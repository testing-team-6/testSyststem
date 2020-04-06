package cn.cstqb.exam.testmaker.actions.user;

import cn.cstqb.exam.testmaker.actions.AbstractPaginationAction;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/9
 * Time: 23:37
 */

public class ListUsersPaginatedAction extends AbstractPaginationAction {
    @Inject
    private IUserService userService;
    private List<User> users;

    public ListUsersPaginatedAction() {
        injector.injectMembers(this);
    }

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {
        this.count = userService.count();
    }

    /**
     * subclasses should implement this method to put business logic
     *
     * @return
     */
    @Override
    protected String doExecuteImpl() {
        try {
            this.users = userService.findUsers(this.pageSize, this.pageNumber);
        } catch (Exception e) {
            logger.error("ListUsersPaginatedAction.executeImpl: Failed to find users: {}", e, e.getMessage() );
            addActionError(getText("error.paging.query.failed", Lists.newArrayList(e.getMessage())));
            return ERROR;
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
}
