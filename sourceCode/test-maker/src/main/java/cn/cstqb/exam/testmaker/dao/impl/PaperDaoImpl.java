package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.PaperDao;
import cn.cstqb.exam.testmaker.entities.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PaperDaoImpl extends GenericJpaDaoImpl<Paper, Integer> implements PaperDao {
    @Override
    public List<Paper> findAll(Project project){
        return findResultList("project", project);
    };

    @Override
    //根据主持人
    public List<Paper> findByUser(Project project , User host){
        return findByUser(project,host.getUsername());
    };

    @Override
    public List<Paper> findByUser(Project project , String hostname){
        EntityManager em = provider.get();
        List<Paper> papers = em.createQuery("SELECT q FROM Paper q WHERE q.project.facilitator=:hostname AND q.project =:project", Paper.class)
                .setParameter("hostname", hostname)
                .setParameter("project", project)
                .getResultList();
        return findQuestions(papers);
    };

    @Override
    //根据试卷名字
    public List<Paper> findByPaperName(Project project , String paperName){
        EntityManager em = provider.get();
        List<Paper> papers =  em.createQuery("SELECT p FROM Paper p WHERE p.project =:project AND p.name =:paperName ", Paper.class)
                .setParameter("paperName", paperName)
                .setParameter("project", project)
                .getResultList();
        return findQuestions(papers);
    };

    //试卷链表加上对应的题目链表
    private List<Paper> findQuestions(List<Paper> papers){
        for(Paper paper:papers){
            int paper_id = paper.getId();
            List<Question> questions = findByPaperId(paper_id);
            paper.setQuestions(questions);
        }
        return papers;
    };


    //根据试卷id获取所有题目
    private List<Question> findByPaperId(int paper_id){
        EntityManager em = provider.get();
        List<Combine> combines = em.createQuery("SELECT q FROM Combine q WHERE q.paper.id = :paper_id", Combine.class)
                .setParameter("paper_id", paper_id)
                .getResultList();
        List<Question> questions = new ArrayList<>();
        for(Combine c:combines){
            Question question = c.getQuestion();
            questions.add(question);
        }
        return questions;
    }


}
