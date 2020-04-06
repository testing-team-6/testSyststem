package cn.cstqb.exam.testmaker.modules;

import org.apache.struts2.interceptor.SessionAware;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 20:49
 */
public interface IModuleWorker extends SessionAware {

    /**
     * Check if the currently logged-in user has privilege to access this worker
     * @return
     */
    boolean isIllegible();

}
