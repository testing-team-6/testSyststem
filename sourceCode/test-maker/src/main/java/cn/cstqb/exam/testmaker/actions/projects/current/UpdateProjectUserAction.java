package cn.cstqb.exam.testmaker.actions.projects.current;

import java.util.List;



import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;

public class UpdateProjectUserAction extends BaseCurrentProjectAction {

	private List<User> users;

	@Override
	public void validateInput() {
		super.validateInput();
//		if (users == null) {
//			addActionError(getText("error.entity.invalid", Lists.newArrayList(users)));
//			return;
//		}
	}

	@Override
	protected String executeImpl() throws Exception {
		Project persisted = projectService.find(sessionProject.getId());
		persisted.setUsers(users);
        projectService.saveOrUpdate(persisted);
        return null;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
