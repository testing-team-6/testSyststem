package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.services.IPaperService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Lame-Lamb
 * Date: 2020/4/10
 * Time: 9:20
 */
public abstract class BasePaperAction extends BaseAction {
    protected Paper paper;
    @Inject protected IPaperService paperService;

    public BasePaperAction() {
        super();
        injector.injectMembers(this);
    }

    @Override
    public void validateInput() {

    }
}
