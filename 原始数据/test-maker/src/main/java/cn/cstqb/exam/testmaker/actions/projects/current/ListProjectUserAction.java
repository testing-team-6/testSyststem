package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.projects.BaseProjectAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;

public class ListProjectUserAction extends BaseProjectAction {
    private String projectName;
	private List<User> users;

	@Override
	public void validateInput() {
		if (Strings.isNullOrEmpty(projectName)) {
			addActionError(getText("error.project.name.required"));
			return;
		}
	}

	@Override
	protected String executeImpl() throws Exception {
		Project persisted = projectService.find(projectName);
        if (persisted==null) {
			addActionError(getText("error.project.not.found", Lists.newArrayList(projectName)));
            return Constants.RESULT_NOT_FOUND;
        }
        users = projectService.findProjectUsers(projectName, true);
        return null;
	}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
