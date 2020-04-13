package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Project;

import java.util.List;

public interface IPaperService {
    /**
     *
     * @param id
     * @return
     */
    Paper find(int id);
    int size();
    boolean exists(Paper paper);
    void delete(Paper paper);
    void saveOrUpdate(Paper paper);
    List<Paper> findAll(Project project);
    List<Paper> findAll(String projectName);
    List<Paper> findAll(Project project, int pageSize, int pageNumber);
}
