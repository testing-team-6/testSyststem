package cn.cstqb.exam.testmaker.results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/19
 * Time: 11:37
 */
public class CustomJSONResult extends JSONResult {
    private static final Logger logger = LoggerFactory.getLogger(CustomJSONResult.class);
    private int errorCode;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

        if (response.isCommitted()) {
            logger.warn("The response is already committed. Action: {}", actionContext.getActionInvocation().getAction().getClass().getName());
            return;
        }
        try {
            Object rootObject;
            rootObject = readRootObject(invocation);
            writeToResponse(response, createJSONString(request, rootObject), enableGzip(request));
        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
    }

/*    public static void writeJSONToResponse(SerializationParams serializationParams) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(serializationParams.getSerializedJSON()))
            stringBuilder.append(serializationParams.getSerializedJSON());

        if (StringUtils.isNotBlank(serializationParams.getWrapPrefix()))
            stringBuilder.insert(0, serializationParams.getWrapPrefix());
        else if (serializationParams.isWrapWithComments()) {
            stringBuilder.insert(0, "*//* ");
            stringBuilder.append(" *//*");
        } else if (serializationParams.isPrefix())
            stringBuilder.insert(0, "{}&& ");

        if (StringUtils.isNotBlank(serializationParams.getWrapSuffix()))
            stringBuilder.append(serializationParams.getWrapSuffix());

        String json = stringBuilder.toString();

        if (logger.isDebugEnabled()) {
            logger.debug("[JSON]" + json);
        }

        HttpServletResponse response = serializationParams.getResponse();

        // status or error code
        if (serializationParams.getStatusCode() > 0)
            response.setStatus(serializationParams.getStatusCode());
        else if (serializationParams.getErrorCode() > 0)
            response.sendError(serializationParams.getErrorCode());

        // content type
        response.setContentType(serializationParams.getContentType() + ";charset="
                + serializationParams.getEncoding());

        if (serializationParams.isNoCache()) {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
        }

        if (serializationParams.isGzip()) {
            response.addHeader("Content-Encoding", "gzip");
            GZIPOutputStream out = null;
            InputStream in = null;
            try {
                out = new GZIPOutputStream(response.getOutputStream());
                in = new ByteArrayInputStream(json.getBytes(serializationParams.getEncoding()));
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                if (in != null)
                    in.close();
                if (out != null) {
                    out.finish();
                    out.close();
                }
            }

        } else {
            response.setContentLength(json.getBytes(serializationParams.getEncoding()).length);
            PrintWriter out = response.getWriter();
            out.print(json);
        }
    }*/
}
