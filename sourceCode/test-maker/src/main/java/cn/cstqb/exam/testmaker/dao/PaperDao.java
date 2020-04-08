package cn.cstqb.exam.testmaker.dao;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.User;

import java.util.List;


//该接口参照QuestionDao写成
public interface PaperDao extends GenericDao<Paper, Integer>{
    List<Paper> findAll(Project project);

    //根据主持人
    List<Paper> findByUser(Project project , User host);


    List<Paper> findByUser(Project project , String hostname);

    //根据试卷名字
    List<Paper> findByPaperName(Project project , String paperName);


}
