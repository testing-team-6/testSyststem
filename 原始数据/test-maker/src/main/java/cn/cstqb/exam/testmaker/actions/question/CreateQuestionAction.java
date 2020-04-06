package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.configuration.Constants;

import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/30
 * Time: 3:13
 */
public class CreateQuestionAction extends BaseQuestionAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
    	if (question.isValidID()) {
			if (!questionService.exists(question)) {
				addActionError(getText("error.question.not.found", Lists.newArrayList(question.getId())));
				return Constants.RESULT_NOT_FOUND;
			}
		}
    	questionService.saveOrUpdate(question);
        sendMailToAllRoles(question);
        return null;
    }

}
