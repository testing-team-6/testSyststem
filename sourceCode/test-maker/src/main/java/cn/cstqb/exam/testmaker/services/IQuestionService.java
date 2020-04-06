package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.*;

import javax.security.auth.login.AccountNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface IQuestionService {
	/**
	 * Create or update a question
	 * @param question
	 */
	void saveOrUpdate(Question question);
	/**
	 * delete a question
	 * @param question
	 */
	void delete(Question question);

	/**
	 * find all questions with the given project
	 * @param project
	 * @return
	 */
	List<Question> findAll(Project project);

    /**
     * Finds all questions in the project
     * @param projectName
     * @return
     */
    List<Question> findAll(String projectName);

    /**
     * Gets all question languages
     * @return
     */
    List<QuestionLanguage> findLanguages();

    /**
     *
     * @return
     */
    List<QuestionType> findTypes();

    /**
     *
     * @param project
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<Question> findAll(Project project, int pageSize, int pageNumber);

    /**
     * Find all questions with the given status
     * @param project
     * @param status
     * @return
     */
    List<Question> find(Project project, QuestionStatus status);

    /**
     * Finds all questions for which the user is the author in the given project
     * @param project The project
     * @param author The user from the project
     * @return The list of questions the user in the position of author
     */
	List<Question> findByAuthor(Project project, User author);

    /**
     *
     * @param project
     * @param username
     * @return
     */
	List<Question> findByAuthor(Project project, String username) throws UserPrincipalNotFoundException, AccountNotFoundException;

	/**
     * Finds all questions for which the user is the reviewer in the given project
     * @param project The project
     * @param reviewer The user from the project
     * @return The list of questions the user in the position of reviewer
     */
	List<Question> findByReviewer(Project project, String reviewer);

	/**
     * Finds all questions for which the user is the QA in the given project
     * @param project The project
     * @param QA The user from the project
     * @return The list of questions the user in the position of QA
     */
	List<Question> findByQA(Project project, String QA);


	/**
	 * find all questions
	 * @return
	 */
	List<Question> findAll();

	/**
	 *
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	List<Question> findAll(int pageSize, int pageNumber);

	/**
	 *
	 * @param id
	 * @return
	 */
	Question findQuestion(int id);

	/**
	 *
	 * @param question
	 * @return
	 */
	boolean exists(Question question);

	/**
	 * judge if the number of answer for the given question match the question type
	 * @param question
	 * @return
	 */
	boolean ifCorrectAnswersMatchType(Question question);

    int size();
}
