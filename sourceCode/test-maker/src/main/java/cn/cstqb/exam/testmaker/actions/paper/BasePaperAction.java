package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionService;

import javax.inject.Inject;

public abstract class BasePaperAction extends BaseAction {
    protected Paper paper;
    @Inject protected IPaperService paperService;
    @Inject protected IQuestionService questionService;
    @Inject protected IQuestionChoiceService questionChoiceService;
    public BasePaperAction() {
        super();
        injector.injectMembers(this);
    }

    @Override
    public void validateInput() {

    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
