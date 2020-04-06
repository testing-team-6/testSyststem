package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.entities.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:54
 */
public class QuestionStatusPreparationRule extends QuestionPreparationRule {
    @Override
    public void populate() {
        super.populate();
        if (questionDao.size() < dataCount) {
            createQuestions();
        }
    }

    private void createQuestions() {
        Integer firstProject=projectDao.getMinID();
        for (int i = 0; i < dataCount; i++) {
            Integer nextProjectId=i+firstProject;
            Project project = projectDao.findById(nextProjectId);
            Objects.requireNonNull(project);
            Integer nextUserId = i + userDao.getMinID();
            User user = userDao.findById(nextUserId);

            Integer nextTypeId = i + questionTypeDao.getMinID();
            QuestionType type = questionTypeDao.findById(nextTypeId);

            Integer nexLangId = i + questionLanguageDao.getMinID();
            QuestionLanguage language = questionLanguageDao.findById(nexLangId);

            Integer nextStatusId = i + questionStatusDao.getMinID();
            QuestionStatus status = questionStatusDao.findById(nextStatusId);

            String stem = "This is the question stem @" + System.currentTimeMillis();
            Question question = new Question(stem, 3, language, type,
                    (short)(3 + i), project, user);
            question.setKnowledgePoint(knowledgePointDao.findById((Integer)(knowledgePointDao.getMaxID() - i)));
            question.setQualityAdmin(user);
            question.setStatus(status);
            question.setAuthoringStartDate(new Date());
            question.setAuthoringFinishDate(new Date());
            Set<User> reviewers = new HashSet<>();
            reviewers.addAll(userDao.findAll());
            question.setReviewers(reviewers);
            questionDao.create(question);
        }
    }
}
