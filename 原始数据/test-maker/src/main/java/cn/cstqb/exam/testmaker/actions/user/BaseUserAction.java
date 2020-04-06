package cn.cstqb.exam.testmaker.actions.user;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.services.IUserService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/9
 * Time: 11:45
 */
public abstract class BaseUserAction extends BaseAction {
    @Inject protected IUserService userService;

    protected BaseUserAction() {
        injector.injectMembers(this);
    }


}
