package cn.cstqb.exam.testmaker.interceptors;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.WildcardUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.FilterDispatcher;
import org.apache.struts2.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 16:04
 */
public class CustomJSONInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 4950170304212158803L;
    private static final Logger logger = LoggerFactory.getLogger(CustomJSONInterceptor.class);
    private boolean enableGZIP = false;
    private boolean wrapWithComments;
    private boolean prefix;
    private String defaultEncoding = "ISO-8859-1";
    private boolean ignoreHierarchy = true;
    private String root;
    private List<Pattern> excludeProperties;
    private List<Pattern> includeProperties;
    private JSONPopulator populator;
    private JSONCleaner dataCleaner = null;
    private boolean debug = false;
    private boolean noCache = false;
    private boolean excludeNullProperties;
    private String callbackParameter;
    private String contentType;
    private ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();
    private String defaultDateFormat;

    public CustomJSONInterceptor() {
        defaultDateFormat = configContext.getDefaultDateFormat();
        populator = new JSONPopulator(defaultDateFormat);
    }

    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        if (response.isCommitted()) {
            return invocation.invoke();
        }

        String contentType = request.getHeader("content-type");
        if (contentType != null) {
            int iSemicolonIdx;
            if ((iSemicolonIdx = contentType.indexOf(";")) != -1)
                contentType = contentType.substring(0, iSemicolonIdx);
        }

        Object rootObject = null;
        if (this.root != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(this.root);

            if (rootObject == null) {
                throw new RuntimeException("Invalid root expression: '" + this.root + "'.");
            }
        }

        if ((contentType != null) && contentType.equalsIgnoreCase("application/json")) {
            // load JSON object
            Object obj = JSONUtil.deserialize(request.getReader());

            if (obj instanceof Map) {
                Map json = (Map) obj;

                // clean up the values
                if (dataCleaner != null)
                    dataCleaner.clean("", json);

                if (rootObject == null) // model overrides action
                    rootObject = invocation.getStack().peek();

                // populate fields
                populator.populateObject(rootObject, json);
            } else {
                logger.error("Unable to deserialize JSON object from request");
                throw new JSONException("Unable to deserialize JSON object from request");
            }


            invocation.addPreResultListener(new PreResultListener() {
                public void beforeResult(ActionInvocation invocation, String resultCode) {
                    Map resultsMap = invocation.getProxy().getConfig().getResults();
                    ResultConfig finalResultConfig = (ResultConfig) resultsMap.get(resultCode);

                    if (finalResultConfig == null) {
                        return;
                    }
                    // do something interesting with the 'to-be' executed result
                    ActionContext actionContext = invocation.getInvocationContext();
                    HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
                    HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
                    boolean writeGzip = enableGZIP && JSONUtil.isGzipInRequest(request);

                    Object rootObject;
                    rootObject = findRootObject(invocation);
                    Map<String, String> params = finalResultConfig.getParams();

                    String errorCodeStr = params.get("errorCode");
                    int errorCode = errorCodeStr != null ? Integer.parseInt(errorCodeStr) : -1;
                    try {
                        String json = createJSONString(request, rootObject);
                        logger.debug("CustomJSONInterceptor.beforeResult: json string{}", json);
                        if (!response.isCommitted()) {
                            writeToResponse(response, json, writeGzip, errorCode, errorCode);
                        } else {
                            logger.warn("CustomJSONInterceptor.beforeResult: response committed before write result. status: {}", response.getStatus() );
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }

            });
        }
        return invocation.invoke();
    }

    protected String createJSONString(HttpServletRequest request, Object rootObject) throws JSONException {
        String json = JSONUtil.serialize(rootObject, excludeProperties, includeProperties, ignoreHierarchy,
                JSONWriter.ENUM_AS_BEAN_DEFAULT, excludeNullProperties, defaultDateFormat);
        json = addCallbackIfApplicable(request, json);
        return json;
    }

    protected Object findRootObject(ActionInvocation invocation) {
        Object rootObject;
        if (this.root != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(root);
        } else {
            rootObject = invocation.getStack().peek(); // model overrides action
        }
        return rootObject;
    }

    protected String addCallbackIfApplicable(HttpServletRequest request, String json) {
        if ((callbackParameter != null) && (callbackParameter.length() > 0)) {
            String callbackName = request.getParameter(callbackParameter);
            if ((callbackName != null) && (callbackName.length() > 0))
                json = callbackName + "(" + json + ")";
        }
        return json;
    }

    protected void writeToResponse(HttpServletResponse response, String json, boolean gzip, int statusCode, int errorCode) throws IOException {
        JSONUtil.writeJSONToResponse(new SerializationParams(response, this.defaultEncoding, wrapWithComments,
                json, false, gzip, noCache, statusCode, errorCode, prefix, contentType));
    }

    /**
     * Wrap generated JSON with comments. Only used if SMD is enabled.
     *
     * @param wrapWithComments
     */
    public void setWrapWithComments(boolean wrapWithComments) {
        this.wrapWithComments = wrapWithComments;
    }

    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        this.defaultEncoding = val;
    }

    /**
     * Ignore properties defined on base classes of the root object.
     *
     * @param ignoreHierarchy
     */
    public void setIgnoreHierarchy(boolean ignoreHierarchy) {
        this.ignoreHierarchy = ignoreHierarchy;
    }

    /**
     * Sets the root object to be deserialized, defaults to the Action
     *
     * @param root OGNL expression of root object to be serialized
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * Sets the JSONPopulator to be used
     *
     * @param populator JSONPopulator
     */
    public void setJSONPopulator(JSONPopulator populator) {
        this.populator = populator;
    }

    /**
     * Sets the JSONCleaner to be used
     *
     * @param dataCleaner JSONCleaner
     */
    public void setJSONCleaner(JSONCleaner dataCleaner) {
        this.dataCleaner = dataCleaner;
    }

    /**
     * @return true if debugging is turned on
     */
    public boolean getDebug() {
        Boolean devModeOverride = FilterDispatcher.getDevModeOverride();
        return devModeOverride != null ? devModeOverride.booleanValue() : this.debug;
    }

    /**
     * Turns debugging on or off
     *
     * @param debug true or false
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(
            String mode) {
        setDebug("true".equalsIgnoreCase(mode));
    }

    /**
     * Sets a comma-delimited list of regular expressions to match properties
     * that should be excluded from the JSON output.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setExcludeProperties(String commaDelim) {
        Set<String> excludePatterns = JSONUtil.asSet(commaDelim);
        if (excludePatterns != null) {
            this.excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
            for (String pattern : excludePatterns) {
                this.excludeProperties.add(Pattern.compile(pattern));
            }
        }
    }

    /**
     * Sets a comma-delimited list of wildcard expressions to match
     * properties that should be excluded from the JSON output.
     *
     * @param commaDelim A comma-delimited list of wildcard expressions
     */
    public void setExcludeWildcards(String commaDelim) {
        Set<String> excludePatterns = JSONUtil.asSet(commaDelim);
        if (excludePatterns != null) {
            this.excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
            for (String pattern : excludePatterns) {
                this.excludeProperties.add(WildcardUtil.compileWildcardPattern(pattern));
            }
        }
    }

    /**
     * Sets a comma-delimited list of regular expressions to match properties
     * that should be included from the JSON output.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setIncludeProperties(String commaDelim) {
        includeProperties = JSONUtil.processIncludePatterns(JSONUtil.asSet(commaDelim), JSONUtil.REGEXP_PATTERN);
    }

    /**
     * Sets a comma-delimited list of wildcard expressions to match
     * properties that should be included from the JSON output.  The
     * standard boilerplate (id, error, debug) are automatically included,
     * as appropriate, so you only need to provide patterns for the
     * contents of "result".
     *
     * @param commaDelim A comma-delimited list of wildcard expressions
     */
    public void setIncludeWildcards(String commaDelim) {
        includeProperties = JSONUtil.processIncludePatterns(JSONUtil.asSet(commaDelim), JSONUtil.WILDCARD_PATTERN);
        if (includeProperties != null) {
            includeProperties.add(Pattern.compile("id"));
            includeProperties.add(Pattern.compile("result"));
            includeProperties.add(Pattern.compile("error"));
            includeProperties.add(WildcardUtil.compileWildcardPattern("error.code"));
        }
    }

    /**
     * Returns the appropriate set of includes, based on debug setting.
     * Derived classes can override if there are additional, custom
     * debug-only parameters.
     */
    protected List getIncludeProperties() {
        if (includeProperties != null && getDebug()) {
            List<Pattern> list = new ArrayList<Pattern>(includeProperties);
            list.add(Pattern.compile("debug"));
            list.add(WildcardUtil.compileWildcardPattern("error.*"));
            return list;
        } else {
            return includeProperties;
        }
    }

    public boolean isEnableGZIP() {
        return enableGZIP;
    }

    /**
     * Setting this property to "true" will compress the output.
     *
     * @param enableGZIP Enable compressed output
     */
    public void setEnableGZIP(boolean enableGZIP) {
        this.enableGZIP = enableGZIP;
    }

    public boolean isNoCache() {
        return noCache;
    }

    /**
     * Add headers to response to prevent the browser from caching the response
     *
     * @param noCache
     */
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    public boolean isExcludeNullProperties() {
        return excludeNullProperties;
    }

    /**
     * Do not serialize properties with a null value
     *
     * @param excludeNullProperties
     */
    public void setExcludeNullProperties(boolean excludeNullProperties) {
        this.excludeNullProperties = excludeNullProperties;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    /**
     * Add "{} && " to generated JSON
     *
     * @param prefix
     */
    public void setPrefix(boolean prefix) {
        this.prefix = prefix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
