package cn.cstqb.exam.testmaker.interceptors;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/21
 * Time: 19:36
 */
public class AuthenticationInterceptor implements Interceptor {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Called to let an interceptor clean up any resources it has allocated.
     */
    @Override
    public void destroy() {

    }

    /**
     * Called after an interceptor is created, but before any requests are processed using
     * {@link #intercept(com.opensymphony.xwork2.ActionInvocation) intercept} , giving
     * the Interceptor a chance to initialize any needed resources.
     */
    @Override
    public void init() {}

    /**
     * Allows the Interceptor to do some processing on the request before and/or after the rest of the processing of the
     * request by the {@link com.opensymphony.xwork2.ActionInvocation} or to short-circuit the processing and just return a String return code.
     *
     * @param invocation the action invocation
     * @return the return code, either returned from {@link com.opensymphony.xwork2.ActionInvocation#invoke()}, or from the interceptor itself.
     * @throws Exception any system-level error, as defined in {@link com.opensymphony.xwork2.Action#execute()}.
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String requestURL = ServletActionContext.getRequest().getServletPath();
        logger.debug("{} Requested URL: {}", this.getClass().getSimpleName(), requestURL);

        if(!needToHandle(requestURL)) return invocation.invoke();
        logger.debug("Protected area accessed. Inspecting if the user is authenticated or not.");

        //check if user is authenticated
        if(!isAuthenticated(invocation)) {
            //get login url from request URL
            String result = null;
            if (requestURL.matches("/web/admin/?")||
                    requestURL.matches("/web/admin/.+\\.action")) {
                result = Constants.RESULT_LOGIN_ADMIN;
            }else if(requestURL.matches("/web/client/?") || requestURL.matches("/web/client/.+\\.action")) {
                result = Constants.RESULT_LOGIN;
            }
            logger.info("Returning login result: {}", result);
            return result!=null?result:Constants.RESULT_USER_NOT_AUTHENTICATED;
        }

        //now the user is already authenticated

        return invocation.invoke();
    }

    protected boolean needToHandle(String requestURL) {
        //index page
        if (requestURL.equals("/web/client/")||requestURL.equals("/web/admin/")) return false;

        return !requestURL.matches("/.*(login|index|authenticate|login-admin|logout|debug.*).action$");
    }

    private boolean isAuthenticated(ActionInvocation invocation) throws Exception {
        Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();

        //return false if the session attributes map is empty because no user is saved in the session
        if(sessionAttributes.isEmpty()) return false;

        User user = getUser(invocation);
        boolean isAuthenticated = user != null;
        logger.debug("isAuthenticated? {} {}", isAuthenticated, isAuthenticated?user.getUsername():"");
        return isAuthenticated;
    }

    private User getUser(ActionInvocation invocation) {
        Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();
        User user = (User) sessionAttributes.get(Constants.ATTR_USER);
        //protecting admin area
        logger.debug("Loaded user:{}", user != null?user.getUsername():"");
        return user;
    }
}
