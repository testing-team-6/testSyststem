package cn.cstqb.exam.testmaker.actions.auth;

import cn.cstqb.exam.testmaker.actions.BaseAction;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/21
 * Time: 0:01
 */
public class LogoutAction extends BaseAction {

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.info("Performing logout....");
        session.clear();
        session.invalidate();
        return null;
    }
}
