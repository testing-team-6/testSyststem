package cn.cstqb.exam.testmaker.listeners; /**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/13
 * Time: 9:34
 */

import cn.cstqb.exam.testmaker.Release;
import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.configuration.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener()
public class ApplicationInitListener implements ServletContextListener,
        HttpSessionAttributeListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ApplicationConfigContext configContext;

    // Public constructor is required by servlet spec
    public ApplicationInitListener() {
        configContext = ApplicationConfigContext.getInstance();
    }
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("ApplicationInitListener.contextInitialized." );
        boolean devMode = configContext.getConfig().getBoolean("application.dev-mode");
        logger.info("Is system in dev mode? {}", devMode);
        sce.getServletContext().setAttribute("devMode",devMode);

        /*
         * Saving context path as an attribute
         */
        String contextPath=sce.getServletContext().getContextPath();
        sce.getServletContext().setAttribute("ctx",contextPath);
        sce.getServletContext().setAttribute(Constants.ATTR_RELEASE, new Release());

        /*
         * Initialize file upload base dir
         */
        String uploadBaseDir = configContext.getUploadBaseDir();
        Path baseDir = Paths.get(sce.getServletContext().getRealPath(uploadBaseDir));
        try {
            logger.info("ApplicationInitListener.contextInitialized: Creating file upload base directory: {}",baseDir );
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("ApplicationInitListener.contextInitialized: Failed to create file upload base dir: {}", baseDir );
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ApplicationInitListener.contextDestroyed..." );
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info("deregistering jdbc driver: {}", driver);
            } catch (SQLException e) {
               logger.error("Error deregistering driver {}", e);
            }

        }
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
//        se.getSession().invalidate();
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
