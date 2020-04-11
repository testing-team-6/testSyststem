package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Paper;

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
}
