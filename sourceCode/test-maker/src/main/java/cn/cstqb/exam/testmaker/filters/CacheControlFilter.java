package cn.cstqb.exam.testmaker.filters;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/14
 * Time: 23:06
 */
public class CacheControlFilter implements Filter {
    private ApplicationConfigContext configContext;
    private long ageInSeconds;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        if (configContext.getConfig().getBoolean("application.dev-mode")) {
            response.setDateHeader("Expires", System.currentTimeMillis() +ageInSeconds);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        ageInSeconds = 604800000L;
        configContext = ApplicationConfigContext.getInstance();
    }

}
