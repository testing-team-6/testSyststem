package cn.cstqb.exam.testmaker.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import cn.cstqb.exam.testmaker.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.cstqb.exam.testmaker.dao.QuestionChoiceDao;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.guice.DaoModule;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;

public class QuestionChoiceServiceImpl implements IQuestionChoiceService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private QuestionChoiceDao questionChoiceDao;
    private QuestionDao questionDao;

    @Inject
    public QuestionChoiceServiceImpl(QuestionChoiceDao questionChoiceDao, QuestionDao questionDao) {
        this.questionChoiceDao = questionChoiceDao;
        this.questionDao = questionDao;
    }

    /**
	 * Create or update a question choice
	 * @param questionChoice
	 */
	@Override
	public void createOrUpdate(QuestionChoice questionChoice) {
		Preconditions.checkArgument(questionChoice != null,
				"Invalid question choice.");
		Preconditions.checkArgument(questionChoice.getIsCorrectAnswer() != null
				&& !Strings.isNullOrEmpty(questionChoice.getContent()),
				"The content of question choice cannot be null or empty");
		QuestionChoice persisted = questionChoiceDao.findById(questionChoice
				.getId());
		if (persisted == null) {
			questionChoiceDao.create(questionChoice);
		} else {
			questionChoiceDao.update(questionChoice);
		}
	}

	/**
	 * delete a question choice
	 * @param questionChoice
	 */
	@Override
	public void delete(QuestionChoice questionChoice) {
		Preconditions.checkArgument(questionChoice != null,
				"Invalid question choice.");
		questionChoiceDao.delete(questionChoice);
	}

	/**
	 * find the question to which question choice belong
	 * @param questionChoice
	 * @return
	 */
	@Override
	public Question findQuestion(QuestionChoice questionChoice) {
		return questionChoice.getQuestion();
	}

	/**
	 * find the question choice with the provided id
	 * @param id
	 * @return
	 */
	@Override
	public QuestionChoice findQuestionChoice(int id) {
		return questionChoiceDao.findById(id);
	}

	@Override
	public List<QuestionChoice> findQuestionChoices(QuestionChoice questionChoice) {
		return questionChoiceDao.findResultList("question", questionChoice.getQuestion());
	}

	@Override
	public List<QuestionChoice> findQuestionChoices(Question question) {
		return questionChoiceDao.findResultList("question", question);
	}

    /**
     * Find question choices by question id
     *
     * @param questionId ID of the question to be searched
     * @return all choices for the question
     */
    @Override
    public List<QuestionChoice> findQuestionChoices(int questionId) {
        Preconditions.checkArgument(questionId>0);
        Question question = questionDao.findById(questionId);
        if (question == null) {
            logger.error("QuestionChoiceServiceImpl.findQuestionChoices: No question found for ID: {}", questionId );
            return null;
        }
        return findQuestionChoices(question);
    }

	@Override
	public QuestionChoice findLastQuestionChoice() {
		return questionChoiceDao.last();
	}

	@Override
	public boolean haveSameLabel(QuestionChoice questionChoice) {
        Preconditions.checkNotNull(questionChoice);
        Preconditions.checkArgument(questionChoice.validate(), "Question choice invalid： %s", questionChoice);
        List<QuestionChoice> questionChoices = findQuestionChoices(questionChoice);

        for (QuestionChoice choice : questionChoices) {
            if ((choice.getChoiceLabel() == questionChoice.getChoiceLabel()// same label
                    && (questionChoice.getId() == null || questionChoice.getId() < 1)) ||
                    (choice.getChoiceLabel() == questionChoice.getChoiceLabel() && !questionChoice.getId().equals(choice.getId()))){
                logger.error("QuestionChoiceServiceImpl.haveSameLabel: There's already one choice with same choice label: {}", choice);
                return true;
            }
        }
        return false;
    }


	@Override
	public boolean correctAnswerMatchType(QuestionChoice questionChoice) {
        Preconditions.checkNotNull(questionChoice);
         if (questionChoice.getQuestion().isMultipleChoice()) return true;

        List<QuestionChoice> questionChoices = findQuestionChoices(questionChoice);
        if (questionChoice.isValidID()){
            for (QuestionChoice choice : questionChoices) {
               if (choice.getIsCorrectAnswer() && questionChoice.getId().equals(choice.getId())){
                   return true;
               }
            }
        }
        for (QuestionChoice choice : questionChoices) {
            if (choice.getIsCorrectAnswer() && questionChoice.getIsCorrectAnswer()){
                return false;
            }
        }

        return true;
    }

	public boolean correctAnswerMatchType(Question question) {
		int count = 0;
		Preconditions.checkNotNull(question);
		if (question.isMultipleChoice()) return true;

		List<QuestionChoice> questionChoices = findQuestionChoices(question);

		for (QuestionChoice choice : questionChoices) {
			if (choice.getIsCorrectAnswer()){
				count++;
				if(count > 1) return false;
			}
		}
		return true;
	}

	@Override
	public boolean exists(QuestionChoice questionChoice) {
		QuestionChoice persisted = null;
		if (questionChoice.getId() != null && questionChoice.getId() > 0) {
			persisted = questionChoiceDao.findById(questionChoice.getId());
		}
		return persisted != null;
	}

}
