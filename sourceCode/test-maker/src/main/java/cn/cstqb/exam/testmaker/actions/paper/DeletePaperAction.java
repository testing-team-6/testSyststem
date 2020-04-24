package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IProjectService;

import javax.inject.Inject;

public class DeletePaperAction extends BaseAction {
    //Ignore
    @Inject
    private IPaperService paperService;
    @Inject
    private IProjectService projectService;
    private String id;
    private String projectName;
    private Project project;
    public DeletePaperAction() {
        super();
        injector.injectMembers(this);
    }

    @Override
    public void validateInput() {
        if (projectName != null) {
            project = projectService.find(projectName);
        }

    }

    //TODO
    @Override
    protected String executeImpl() throws Exception {
        Paper paper = new Paper();
        if (id != null) {

            String[] temp = id.split("\\.");
            for (String idString : temp){
                paper = paperService.find(Integer.parseInt(idString));
                paperService.delete(paper);
            }
            return null;
        }
        return null;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setId(String id) {
        this.id = id;
    }
}
