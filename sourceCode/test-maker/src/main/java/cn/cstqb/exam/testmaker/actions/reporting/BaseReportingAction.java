package cn.cstqb.exam.testmaker.actions.reporting;

import cn.cstqb.exam.testmaker.actions.projects.current.BaseCurrentProjectAction;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IReportingService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/31
 * Time: 13:01
 */
public abstract class BaseReportingAction extends BaseCurrentProjectAction {
    @Inject protected IReportingService reportingService;
    protected int warningThreshold;
    protected BaseReportingAction() {
        super();
        injector.injectMembers(this);
        this.warningThreshold = configContext.getConfig().getInt("monitoring.expiring.warning-threshold");
    }

    public int getWarningThreshold() {
        return warningThreshold;
    }
}
