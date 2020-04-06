package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.projects.BaseProjectAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;

import com.google.common.collect.Lists;

/**
 * Created by wushuang on 2015/3/13.
 */
public class UpdateProjectAction extends BaseProjectAction{

    private Project project;

    @Override
    public void validateInput() {
//        validateProject(project);
    	if (project == null) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(Project.class.getSimpleName())));
			return;
		}
        if (!project.isValidID()) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(Project.class.getSimpleName())));
            return;
        }
        if (!projectService.exists(project)) {
            addActionError(getText("error.entity.notExisted", Lists.newArrayList(project.getId())));
            return;
        }
    }

    @Override
    protected String executeImpl() throws Exception {
        Project persisted = projectService.find(project.getId());
        projectService.saveOrUpdate(project);
        if (!persisted.getFacilitator().getId().equals(project.getFacilitator().getId())) {
            sendMailToFacilitator(project, project.getFacilitator());
        }
        //update session
        session.put(Constants.ATTR_PROJECT, project);
        return null;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject(Project project) {
        return project;
    }
}
