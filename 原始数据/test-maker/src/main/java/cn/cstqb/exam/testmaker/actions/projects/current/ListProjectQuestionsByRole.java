package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.actions.question.ListQuestionsByRole;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.QuestionRole;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/17
 * Time: 22:48
 */
public class ListProjectQuestionsByRole extends ListQuestionsByRole {
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        userValidator.validateUserName(username);
        project = (Project) session.get(Constants.ATTR_PROJECT);
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("ListProjectQuestionsByAuthor.executeImpl: finding questions for username [#0] in current project #1.", username,project.getName() );
        loadQuestions(QuestionRole.Author,project);
        return null;
    }

    /**
     * Loads QA questions
     * @return
     * @throws Exception
     */
    public String qa() throws Exception {
        validateInput();
        if (hasActionErrors()) {
            return Constants.RESULT_FORM_INVALID;
        }
        loadQuestions(QuestionRole.QA,project);
        return SUCCESS;
    }

    /**
     * Loads questions for reviewers
     * @return
     * @throws Exception
     */
    public String reviewer() throws Exception {
        validateInput();
        if (hasActionErrors()) {
            return Constants.RESULT_FORM_INVALID;
        }
        loadQuestions(QuestionRole.Reviewer,project);
        return SUCCESS;
    }

    /**
     * Loads questions for author role
     * @return
     * @throws Exception
     */
    public String author() throws Exception {
        validateInput();
        if (hasActionErrors()) {
            return Constants.RESULT_FORM_INVALID;
        }
        loadQuestions(QuestionRole.Author,project);
        return SUCCESS;
    }


}
