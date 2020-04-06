package cn.cstqb.exam.testmaker.filters;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.User;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/24
 * Time: 15:25
 */
@WebFilter(filterName = "loginFilter")
public class AuthenticationFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String contextPath;

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = ((HttpServletRequest) req);
        String requestURL =request.getServletPath();
        if (logger.isDebugEnabled()) {
            logger.debug("{} - Requesting URL: {}", this.getClass().getSimpleName(), requestURL);
        }
        boolean shouldProceed = needToHandle(requestURL);
        if (!shouldProceed) {
            if (logger.isDebugEnabled()) {
                logger.debug("No need to check further. Passing on...");
            }
            chain.doFilter(req, resp);
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Protected area accessed. Inspecting if the user is authenticated or not.");
        }

        //check if user is authenticated
        if (!isAuthenticated(request)) {
            //get login url from request URL
            StringBuilder result = new StringBuilder(contextPath);
            if (requestURL.startsWith("/admin/")||requestURL.equals("/admin")) {
                result.append("/admin/");
            } else {
                result.append("/");
            }
            if (logger.isInfoEnabled()) {
                logger.info("Redirecting to login URLs: {}", result);
            }
            ((HttpServletResponse) resp).sendRedirect(result.toString());
            return;
        }

        //now the user is already authenticated
        User user = getUser(request);
        if (logger.isDebugEnabled()) {
            logger.debug("Authenticated user: {}", user.getUsername());
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        contextPath=config.getServletContext().getContextPath();
        if (logger.isDebugEnabled()) {
            logger.debug("{} Context Path: {}", this.getClass().getSimpleName(), contextPath);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        User user = getUser(request);
        boolean isAuthenticated = user != null;
        if (logger.isDebugEnabled()) {
            logger.debug("isAuthenticated? {} {}", isAuthenticated, isAuthenticated?user.getUsername():"");
        }
        return isAuthenticated;
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.ATTR_USER);
        if (logger.isDebugEnabled()) {
            logger.debug("Loaded user:{}", user != null ? user.getUsername() : "");
        }
        return user;
    }

    protected void checkUser(HttpServletRequest request) {
        String requestURL = request.getServletPath();
        User user = getUser(request);

        //if the user is not admin and accessing admin console. return index page
        if (requestURL.startsWith("/admin/") && !user.isAdmin()) {
        }

    }

    private boolean needToHandle(String requestURL) {
        //index page
        if (requestURL.equals("/")||requestURL.equals("/admin/")) return false;

        if(requestURL.matches("/.*(login|index).action$")) return false;
        return true;
    }
}
