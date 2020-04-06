package cn.cstqb.exam.testmaker.actions;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/30
 * Time: 3:14
 */
public abstract class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Injector injector = AppInjector.getInstance().getInjector();
    protected static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.SIMPLIFIED_CHINESE);
    protected ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();
    protected SessionMap<String,Object> session;
    protected HttpServletRequest request;
    @Inject
    protected UserValidator userValidator;
    protected BaseAction() {
        injector.injectMembers(this);
    }

    /**
     * Sets the Map of session attributes in the implementing class.
     *
     * @param session a Map of HTTP session attribute name/value pairs.
     */
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = (SessionMap<String, Object>) session;
    }

    /**
     * Sets the HTTP request object in implementing classes.
     *
     * @param request the HTTP request.
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    public abstract void validateInput();

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    protected abstract String executeImpl() throws Exception;

    /**
     * A default implementation that does nothing an returns "success".
     * <p/>
     * Subclasses should override this method to provide their business logic.
     * <p/>
     * See also {@link com.opensymphony.xwork2.Action#execute()}.
     *
     * @return returns {@link #SUCCESS}
     * @throws Exception can be thrown by subclasses.
     */
    @Override
    public final String execute() throws Exception {
        validateInput();
        if (hasActionErrors()) {
            logger.warn("#0.execute: Input form/parameter is invalid.", getClass().getSimpleName());
            //addActionError(getText("error.form.invalid") );
            return Constants.RESULT_FORM_INVALID;
        }
        try {
            String result=executeImpl();
            if (!Strings.isNullOrEmpty(result)) {
                logger.error("#0.executeImpl: error found in concrete action!", getClass().getName() );
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Throwable rootCause=ExceptionUtils.getRootCause(e);

            String msg=String.format("%s.execute: Exception occurred! Error: %s", getClass().getName(),
                    rootCause!=null?ExceptionUtils.getStackTrace(rootCause): e.getMessage());
            logger.error(msg);
            addActionError(msg);
            return Constants.RESULT_EXCEPTION;
        }

        logger.info("#0.execute: success without errors!",getClass().getName() );
        return SUCCESS;
    }

    /**
     * A default implementation that validates nothing.
     * Subclasses should override this method to provide validations.
     */
    @Override
    public final void validate() {}

    protected User getLoggedInUser() {
        return (User) session.get(Constants.ATTR_USER);
    }
}
