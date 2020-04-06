package cn.cstqb.exam.testmaker.modules;

import cn.cstqb.exam.testmaker.configuration.AppInjector;
import com.google.inject.Injector;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.dispatcher.SessionMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 20:51
 */
public abstract class AbstractModuleWorker extends ActionSupport implements IModuleWorker {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Injector injector;
    protected SessionMap<String,Object> session;

    protected AbstractModuleWorker() {
        injector = AppInjector.getInstance().getInjector();
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

}
