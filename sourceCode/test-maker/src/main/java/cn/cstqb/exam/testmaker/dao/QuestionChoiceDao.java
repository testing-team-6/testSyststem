package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.QuestionChoice;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:48
 */
public interface QuestionChoiceDao extends GenericDao<QuestionChoice, Integer> {

    /**
     * Finds all choices for the given question id
     * @param questionId
     * @return
     */
    List<QuestionChoice> findQuestionChoices(int questionId);
}
