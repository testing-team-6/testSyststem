package cn.cstqb.exam.testmaker;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/16
 * Time: 15:41
 */
public class IndexHelper {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext context;

    private final ApplicationConfigContext configContext= ApplicationConfigContext.getInstance();

    public IndexHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
        this.request = request;
        this.response = response;
        this.context = context;
    }

    public String getContextPath() {
        return context.getContextPath();
    }

    public boolean isDevMode() {
        return configContext.getConfig().getBoolean("application.dev-mode");
    }
}
