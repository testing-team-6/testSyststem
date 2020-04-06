package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.dao.*;
import cn.cstqb.exam.testmaker.entities.*;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/31
 * Time: 15:49
 */
public class QuestionPreparationRule extends KnowledgePointDataGenerationRule   {
    protected ProjectDao projectDao;
    protected ProjectStatusDao projectStatusDao;
    protected UserDao userDao;

    protected QuestionLanguageDao questionLanguageDao;
    protected QuestionTypeDao questionTypeDao;
    protected QuestionChoiceDao choiceDao;
    protected QuestionStatusDao questionStatusDao;
    protected QuestionDao questionDao;

    private List<User> users;
    public QuestionPreparationRule() {
        super();
    }

    public KnowledgePointDao getKnowledgePointDao() {
        return super.knowledgePointDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public ProjectStatusDao getProjectStatusDao() {
        return projectStatusDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public QuestionLanguageDao getQuestionLanguageDao() {
        return questionLanguageDao;
    }

    public QuestionTypeDao getQuestionTypeDao() {
        return questionTypeDao;
    }

    public QuestionStatusDao getQuestionStatusDao() {
        return questionStatusDao;
    }

    public QuestionChoiceDao getChoiceDao() {
        return choiceDao;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    protected void init() {
        super.init();
        projectDao = injector.getInstance(ProjectDao.class);
        projectStatusDao = injector.getInstance(ProjectStatusDao.class);
        userDao = injector.getInstance(UserDao.class);

        questionLanguageDao = injector.getInstance(QuestionLanguageDao.class);
        questionTypeDao = injector.getInstance(QuestionTypeDao.class);
        questionStatusDao = injector.getInstance(QuestionStatusDao.class);
        questionDao = injector.getInstance(QuestionDao.class);
    }

    @Override
    public void populate() {
        super.populate();
        if (userDao.size() < dataCount) {
            createUsers();
        }
        users=userDao.findAll();

        if (projectStatusDao.size() < dataCount) {
            createProjectStatus();
        }
        /*
         * Create projects
         */
        if (projectDao.size()<dataCount) {
            createProjects();
        }

        //question languages
        if (questionLanguageDao.size() < dataCount) {
            createLanguages();
        }

        if (questionTypeDao.size() < dataCount) {
            createQuestionTypes();
        }

        if (questionStatusDao.size() < dataCount) {
            createQuestionStatus();
        }


    }

    private void createProjectStatus() {
        for (int i = 0; i < dataCount; i++) {
            ProjectStatus status = new ProjectStatus("Project Status: " + System.currentTimeMillis());
            projectStatusDao.create(status);
        }
    }

    private void createQuestionStatus() {
        for (int i = 0; i < dataCount; i++) {
            QuestionStatus status = new QuestionStatus("Status: " + System.currentTimeMillis());
            questionStatusDao.create(status);
        }
    }

    private void createQuestionTypes() {
        for (int i = 0; i < dataCount; i++) {
            QuestionType type = new QuestionType(String.format("Type-%d-%d",i,System.currentTimeMillis()));
            questionTypeDao.create(type);
        }
    }

    private void createLanguages() {
        for (int i = 0; i < dataCount; i++) {
            Locale[] locales=Locale.getAvailableLocales();
            QuestionLanguage language = new QuestionLanguage(locales[i].getDisplayName(Locale.ENGLISH)+System.currentTimeMillis());
            questionLanguageDao.create(language);
        }
    }

    private void createUsers() {
        for (int i = 0; i < dataCount; i++) {
            User user =new User(String.format("user-%d-%d",i,System.currentTimeMillis()));
            user.setEmail(String.format("%d@test.com", System.currentTimeMillis()));
            user.setPassword("test123");
            userDao.create(user);
        }
    }

    private void createProjects() {
        List<ProjectStatus> projectStatuses = projectStatusDao.findAll();
        for (ProjectStatus projectStatus : projectStatuses) {
            Project project = new Project("DummyProject" + System.currentTimeMillis(),
                    users.get(0), projectStatus);
            projectDao.create(project);
        }
    }
}
