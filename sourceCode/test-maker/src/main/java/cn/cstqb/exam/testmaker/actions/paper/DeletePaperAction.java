package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.services.IPaperService;
import javax.inject.Inject;

public class DeletePaperAction extends BaseAction {
    //Ignore
    @Inject
    private IPaperService paperService;
    private String id;


    @Override
    public void validateInput() {

    }

    //TODO
    @Override
    protected String executeImpl() throws Exception {
        Paper paper = new Paper();
        Project project = (Project) session.get(Constants.ATTR_PROJECT);
        if (project == null) {
            addActionError(getText("error.user.auth.notLoggedIn"));
            return Constants.RESULT_USER_NOT_AUTHENTICATED;
        }
        String[] temp = id.split("\\.");
        for (String idString : temp){
            paper = paperService.find(Integer.parseInt(idString));
            paperService.delete(paper);
        }

        return null;
    }




    public String getId() {
        return id;
    }

    public void setId(String ids) {
        this.id = ids;
    }

    public IPaperService getPaperService() {
        return paperService;
    }

    public void setPaperService(IPaperService paperService) {
        this.paperService = paperService;
    }
}
