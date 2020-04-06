package cn.cstqb.exam.testmaker.actions.projects;

import cn.cstqb.exam.testmaker.entities.Project;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/31
 * Time: 8:46
 */
public class ViewProjectAction extends BaseProjectAction {
    private String projectName;
    private Project project;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if(Strings.isNullOrEmpty(projectName)){
            logger.warn("ViewProjectAction.execute: Project name is empty {}", projectName );
            addActionError(getText("error.project.name.required"));
        }

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        project = projectService.find(projectName);
        if (project==null) {
            addActionError(getText("error.project.not.found", Lists.newArrayList(projectName)));
            return ERROR;
        }
        return null;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
