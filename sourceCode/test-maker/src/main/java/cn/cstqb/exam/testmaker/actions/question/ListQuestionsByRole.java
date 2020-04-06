package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionRole;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.struts2.json.annotations.JSON;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/17
 * Time: 22:48
 */
public class ListQuestionsByRole extends BaseQuestionAction {
    protected String username;
    protected Project project;
    private String role;
    private QuestionRole questionRole;
    protected List<Question> questions;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        userValidator.validateExistingUser(username);
        if (Strings.isNullOrEmpty(role)) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.question.role"))));
            return;
        }
        try {
            questionRole = QuestionRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            addActionError(getText("error.question.role.invalidName", Lists.newArrayList(role)));
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
        logger.debug("ListQuestionsByRole.executeImpl: finding questions for username [#0] in current project #1.", username,project.getName() );
        loadQuestions(questionRole,project);
        return null;
    }

    protected void loadQuestions(QuestionRole role, Project project) throws Exception {
        switch (role) {
            case Author:
                this.questions = questionService.findByAuthor(project, username);
                break;
            case Reviewer:
                this.questions = questionService.findByReviewer(project, username);
                break;
            case QA:
                this.questions = questionService.findByQA(project, username);
                break;
        }
    }

    @JSON(serialize = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @JSON(serialize = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @JSON(serialize = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
