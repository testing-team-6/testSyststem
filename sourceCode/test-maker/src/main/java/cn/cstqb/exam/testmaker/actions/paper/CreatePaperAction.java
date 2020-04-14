package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreatePaperAction extends BaseAction {
    //Ignore
    @Inject
    private IPaperService paperService;
    @Inject
    private IQuestionService questionService;

    private Paper newPaper;
    private String ids;


    @Override
    public void validateInput() {

    }

    //TODO
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("SaveProjectQuestion.executeImpl: Saving question..." );
        logger.info(newPaper.getName());
        Paper paper = new Paper();
        paper.setName(newPaper.getName());
        Project project = (Project) session.get(Constants.ATTR_PROJECT);
        if (project == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return Constants.RESULT_USER_NOT_AUTHENTICATED;
        }
        String[] temp = ids.split("\\.");
        for (String idString : temp){
            Question question = questionService.findQuestion(Integer.parseInt(idString));
            paper.addQuestion(question);
        }
        paper.setProject(project);
        paperService.saveOrUpdate(paper);//save paper

        return null;
    }

    public void setPaper(Paper newPaper) {
        this.newPaper = newPaper;
    }



    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public IQuestionService getQuestionService() {
        return questionService;
    }

    public void setQuestionService(IQuestionService questionService) {
        this.questionService = questionService;
    }
}
