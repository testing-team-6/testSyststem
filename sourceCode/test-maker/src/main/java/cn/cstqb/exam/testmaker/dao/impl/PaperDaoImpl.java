package cn.cstqb.exam.testmaker.dao.impl;

import cn.cstqb.exam.testmaker.dao.PaperDao;
import cn.cstqb.exam.testmaker.entities.*;

import javax.persistence.EntityManager;
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
        return em.createQuery("SELECT q FROM Paper q WHERE q.project.facilitator.username=:hostname AND q.project =:project", Paper.class)
                .setParameter("hostname", hostname)
                .setParameter("project", project)
                .getResultList();
    };

    @Override
    //根据试卷名字
    public List<Paper> findByPaperName(Project project , String paperName){
        EntityManager em = provider.get();
        return em.createQuery("SELECT p FROM Paper p WHERE p.project =:project AND p.name =:paperName ", Paper.class)
                .setParameter("paperName", paperName)
                .setParameter("project", project)
                .getResultList();
    };




}
