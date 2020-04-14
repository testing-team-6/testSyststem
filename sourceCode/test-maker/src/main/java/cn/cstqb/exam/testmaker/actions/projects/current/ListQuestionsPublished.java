package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class ListQuestionsPublished extends BaseCurrentProjectAction {
    private List<Question> questions;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        super.validateInput();
        validatePaginationParams();
    }

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {
        this.count = questionService.findAll(sessionProject).size();
    }


    /**
     * subclasses should implement this method to put business logic
     *
     * @return
     */
    @Override
    protected String doExecuteImpl() {
        logger.debug("ListProjectQuestions.doExecuteImpl: Loading questions for project: #0", sessionProject.getName() );
        List<Question> allQuestions = questionService.findAll(sessionProject, pageSize, pageNumber);
        questions = new ArrayList<>();
        /* modified by jxy */
        for (Question question : allQuestions){
            if (question.getStatus().isFinish()){
                questions.add(question);
            }
        }
        return null;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
