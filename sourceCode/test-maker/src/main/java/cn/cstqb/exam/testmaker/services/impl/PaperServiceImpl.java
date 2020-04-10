package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.PaperDao;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.services.IPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkArgument;

public class PaperServiceImpl implements IPaperService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private PaperDao paperDao;

    @Inject
    public PaperServiceImpl(PaperDao paperDao) {
        this.paperDao = paperDao;
    }

    @Override
    public Paper find(int id) {
        checkArgument(id>0,"The project id must be greater than zero. %s", id);
        return paperDao.findById(id);
    }
}
