package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.entities.Question;

public class WriteQuestionAction extends BaseQuestionAction {


    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
//    	if (validateChoiceType(question)) {
//    		addActionError(getText("error.questionChoice.correctAnswer.not.match.questionType"));
//    		return;
//		}
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
		questionService.saveOrUpdate(question);
        return null;
    }

    private boolean validateChoiceType(Question question) {
    	return questionService.ifCorrectAnswersMatchType(question);
    }
}
