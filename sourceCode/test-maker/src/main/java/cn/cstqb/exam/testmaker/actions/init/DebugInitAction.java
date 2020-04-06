package cn.cstqb.exam.testmaker.actions.init;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.services.IDebugInitializationService;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IUserService;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/31
 * Time: 14:32
 */
public class DebugInitAction extends BaseAction {
    @Inject
    private IDebugInitializationService initService;
    @Inject
    private IUserService userService;
    @Inject private IProjectService projectService;
    @Inject
    private ISyllabusModuleWorker syllabusWorker;

    private User user;
    private Project project;
    private int dataSize=10;
    public DebugInitAction() {
        injector.injectMembers(this);
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        initService.setCount(dataSize);
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        initUserAndProject();
        return null;
    }

    public String initUserAndProject() throws Exception {
        logger.debug("Initializing user and project information for debugging....");
        initService.setCount(dataSize);
        user = userService.findFirstUser();
        logger.info("Debug user to be used: #0", Objects.toString(user));

        //init syllabus
        initService.initSyllabus();

        initProject();

        initService.initUsers();
        initService.initQuestionAttributes();
        return SUCCESS;
    }
    public String loadData() {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading user and project information for debugging....");
        }
        user = getLoggedInUser();
        project = (Project) session.get(Constants.ATTR_PROJECT);
        return SUCCESS;
    }

    private void initProject() {
        project = projectService.findFirst();
        if (logger.isInfoEnabled()) {
            logger.info("Debug project to be used: {}:{}", project.getId().toString(), project.getName());
        }
        Syllabus syllabus=syllabusWorker.findSyllabus(1);
        if (project.getSyllabus() ==null) {
            project.setSyllabus(syllabus);
        }
        project.addUser(user);
        projectService.saveOrUpdate(project);
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }
}
