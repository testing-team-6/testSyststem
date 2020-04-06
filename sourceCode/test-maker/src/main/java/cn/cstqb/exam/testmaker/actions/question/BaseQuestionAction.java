package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.IQuestionStatusService;
import cn.cstqb.exam.testmaker.services.IReviewCommentService;
import cn.cstqb.exam.testmaker.util.ServletUtils;
import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.inject.Inject;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/30
 * Time: 3:15
 */
public abstract class BaseQuestionAction extends BaseAction {
    protected Question question;
    protected int numOfCorrectAnswer;
    @Inject
    protected IQuestionService questionService;
    @Inject
    protected IQuestionStatusService statusService;
    @Inject
    protected IReviewCommentService commentService;
    @Inject
    protected IQuestionChoiceService choiceService;
    @Inject
    protected MailNotificationFactory mailFactory;
    @Inject
    protected MailMessenger messenger;

    public BaseQuestionAction() {
        super();
        injector.injectMembers(this);
    }

    @Override
    public void validateInput() {
        if (question == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question"))));
            return;
        }

        /*
         * This part checks existing entity
         */
        if (question.getId() != null) {
            if (question.getId() < 1) {
                addActionError(getText("error.entity.invalid", Lists.newArrayList(Question.class.getSimpleName())));
                return;
            }
            if (!question.validate()) {
                addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("message.question.requiredFields"))));
                return;
            }
        }

        if (!question.validateBasicFields()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.question"), getText("message.question.requiredFields.basic"))));
            return;
        }

        /**
         * author, reviewer and quality admin should be distinct
         */
        for (User reviewer : question.getReviewers()) {
            if (question.getAuthor().equals(reviewer)) {
                addActionError(getText("error.question.distinct.role"));
                return;
            }
        }
        if (question.getAuthor().equals(question.getQualityAdmin())) {
            addActionError(getText("error.question.distinct.role"));
            return;
        }

        /**
         * date judgment
         */
        if (question.getAuthoringStartDate() == null || question.getAuthoringFinishDate() == null || question.getReviewingStartDate() == null || question.getReviewingFinishDate() == null) {
            addActionError(getText("error.question.missing.date.info"));
            return;
        }
        if (question.getAuthoringFinishDate().before(question.getAuthoringStartDate())) {
            addActionError(getText("error.question.authoringFinishDate.earlierThanStart", Lists.newArrayList(dateFormat.format(question.getAuthoringStartDate()), dateFormat.format(question.getAuthoringFinishDate()))));
            return;
        }
        if (question.getReviewingFinishDate().before(question.getReviewingStartDate())) {
            addActionError(getText("error.question.reviewingFinishDate.earlierThanStart", Lists.newArrayList(dateFormat.format(question.getReviewingStartDate()), dateFormat.format(question.getReviewingFinishDate()))));
            return;
        }
        if (question.getReviewingStartDate().before(question.getAuthoringStartDate())) {
            addActionError(getText("error.question.reviewingStartDate.earlierThan.authoringStartDate"));
            return;
        }
        if (question.getReviewingFinishDate().before(question.getAuthoringFinishDate())) {
            addActionError(getText("error.question.reviewingFinishDate.earlierThan.authoringFinishDate"));
            return;
        }
        if (question.getAuthoringStartDate().before(((Project) session.get(Constants.ATTR_PROJECT)).getStartDate()) || question.getReviewingStartDate().before(((Project) session.get(Constants.ATTR_PROJECT)).getStartDate())) {
            addActionError(getText("error.question.startDate.earlierThan.projectStartDate"));
            return;
        }
        if (question.getAuthoringFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate()) || question.getReviewingFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate())) {
            addActionError(getText("error.question.finishDate.laterThan.projectFinishDate"));
            return;
        }

        if (question.getId() != null && choiceService.findQuestionChoices(question.getId()) != null) {
            if (!choiceService.correctAnswerMatchType(question)) {
                addActionError(getText("error.questionChoice.correctAnswer.not.match.questionType"));
                return;
            }
        }

//		if (choiceService.findQuestionChoices(question.getId()) != null) {
//			numOfCorrectAnswer = 0;
//			for (QuestionChoice questionChoice: choiceService.findQuestionChoices(question.getId())) {
//				if (questionChoice.getIsCorrectAnswer()) {
//					numOfCorrectAnswer++;
//					if (numOfCorrectAnswer > 1 && question.isMultipleChoice()) {
//						addActionError(getText("error.questionChoice.correctAnswer.not.match.questionType"));
//					}
//				}
//			}
//		}
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    protected void sendMailToQuestionUser(Question question, QuestionRole role) throws AddressException, TemplateException, EmailException, IOException {
        mailFactory.setContextPath(ServletUtils.getBaseUrl(request));
        List<QuestionChoice> choices = choiceService.findQuestionChoices(question);
        HtmlEmail email = mailFactory.buildQuestionNotification(question, choices, role);
        if (email != null) {
            messenger.send(new SendMailTask(email));
        }
    }

    protected void sendMailToAllRoles(Question question) throws AddressException, TemplateException, IOException, EmailException {
        for (QuestionRole role : QuestionRole.values()) {
            sendMailToQuestionUser(question, role);
        }
    }
}
