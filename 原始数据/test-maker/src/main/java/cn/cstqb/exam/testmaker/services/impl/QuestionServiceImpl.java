package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.QuestionDao;
import cn.cstqb.exam.testmaker.dao.QuestionTypeDao;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IQuestionLanguageService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/29
 * Time: 19:39
 */
public class QuestionServiceImpl implements IQuestionService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private IProjectService projectService;
    @Inject
    private IQuestionLanguageService languageService;
    @Inject
    private QuestionTypeDao typeDao;
    @Inject
    private IUserService userService;

    private QuestionDao dao;

    @Inject
    public QuestionServiceImpl(IProjectService projectService, QuestionDao dao) {
        this.projectService = projectService;
        this.dao = dao;
    }

    /**
     * Create or update a question
     *
     * @param question
     */
    @Override
    public void saveOrUpdate(Question question) {
        if (question == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Question is null. Operation aborted.");
            }
            return;
        }


        Question persisted = dao.findById(question.getId());
        if (persisted == null) {
            checkState(question.validateBasicFields(), "Missing required fields in question: %s", question);
            dao.create(question);
        } else {
            checkState(question.validate(), "Missing required fields in question: %s", question);
            dao.update(question);
        }
    }

    /**
     * delete a question
     *
     * @param question
     */
    @Override
    public void delete(Question question) {
        if (question == null) return;
        dao.delete(question);
    }

    /**
     * find all questions with the given project
     *
     * @param project
     * @return
     */
    @Override
    public List<Question> findAll(Project project) {
        checkArgument(project != null);
        return dao.findAll(project);
    }

    @Override
    public List<Question> findAll(Project project, int pageSize, int pageNumber) {
        checkArgument(project != null);
        checkArgument(pageSize > 1, "Page size must be greater than 1.");
        checkArgument(pageNumber > 0);
        return dao.findResultList("project", project, pageSize, pageNumber);
    }

    /**
     * Find all questions with the given status
     *
     * @param status
     * @return
     */
    @Override
    public List<Question> find(Project project, QuestionStatus status) {
        checkArgument(status != null && status.validate() && status.isValidID());
        logger.debug("QuestionServiceImpl.find: finding all questions with status: {}", status.getName());
        return dao.findByStatus(project, status);
    }

    /**
     * Finds all questions for which the user is the author in the given project
     *
     * @param project The project
     * @param author  The user from the project
     * @return The list of questions the user in the position of author
     */
    @Override
    public List<Question> findByAuthor(Project project, User author) {
        checkArgument(project != null && author != null);
//        checkState(project.getUsers().contains(author), "The user %s is not found in the project [%s]", author.getUsername(), project.getName());
        return dao.findByAuthor(project, author);
    }

    /**
     * @param project
     * @param username
     * @return
     */
    @Override
    public List<Question> findByAuthor(Project project, String username) throws AccountNotFoundException {
        checkArgument(project != null && project.isValidID());
        checkArgument(!Strings.isNullOrEmpty(username));
        if (!userService.isUserNameExists(username)) {
            throw new AccountNotFoundException(String.format("No such user: %s", username));
        }
        return dao.findByAuthor(project, username);
    }

    /**
     * Finds all questions for which the user is the reviewer in the given project
     *
     * @param project  The project
     * @param reviewer The user from the project
     * @return The list of questions the user in the position of reviewer
     */
    @Override
    public List<Question> findByReviewer(Project project, String reviewer) {
        checkArgument(project != null && project.isValidID());
        checkArgument(!Strings.isNullOrEmpty(reviewer));
        return dao.findByReviewer(project, reviewer);
    }

    /**
     * Finds all questions for which the user is the QA in the given project
     *
     * @param project The project
     * @param QA      The user from the project
     * @return The list of questions the user in the position of QA
     */
    @Override
    public List<Question> findByQA(Project project, String QA) {
        checkArgument(project != null && project.isValidID());
        checkArgument(!Strings.isNullOrEmpty(QA));
        return dao.findByQA(project, QA);
    }

    @Override
    public List<Question> findAll(String projectName) {
        checkArgument(!Strings.isNullOrEmpty(projectName));
        Project project = projectService.find(projectName);
        return findAll(project);
    }

    /**
     * find all questions
     *
     * @return
     */
    @Override
    public List<Question> findAll() {
        return dao.findAll();
    }

    /**
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public List<Question> findAll(int pageSize, int pageNumber) {
        return dao.findAll(pageSize, pageNumber);
    }

    /**
     * Gets all question languages
     *
     * @return
     */
    @Override
    public List<QuestionLanguage> findLanguages() {
        return languageService.findAll();
    }

    /**
     * @return
     */
    @Override
    public List<QuestionType> findTypes() {
        return typeDao.findAll();
    }

    /**
     *
     */
    @Override
    public Question findQuestion(int id) {
        return dao.findById(id);
    }

    /**
     *
     */
    @Override
    public boolean exists(Question question) {
        Question persisted = null;
        if (question.getId() != null && question.getId() > 0) {
            persisted = dao.findById(question.getId());
        }
        return persisted != null;
    }

    @Override
    public int size() {
        return dao.size().intValue();
    }

    /**
     * judge if the number of answer for the given question match the question type
     *
     * @param question
     * @return
     */
    @Override
    public boolean ifCorrectAnswersMatchType(Question question) {
        //FIXME: the choices field will be removed from question entity
/*		List<QuestionChoice> questionChoices = (List)question.getChoices();
        int correctNum = 0;
		for (QuestionChoice questionChoice : questionChoices) {
			if (questionChoice.getIsCorrectAnswer()) {
				correctNum++;
			}
		}
		if (correctNum == 0) {
			return true;
		}
		if (!question.isMultipleChoice()) {
			return correctNum==1;
		} else {
			return correctNum>1;
		}*/
        return true;
    }

}
