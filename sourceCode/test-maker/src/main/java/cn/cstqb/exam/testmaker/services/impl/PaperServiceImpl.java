package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.dao.PaperDao;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IProjectService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class PaperServiceImpl implements IPaperService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private IProjectService projectService;
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
    @Override
    public int size() {
        return paperDao.size().intValue();
    }

    public boolean exists(Paper paper) {
        Paper persisted = null;
        if (paper.getId() != null && paper.getId() > 0) {
            persisted =  paperDao.findById(paper.getId());
        }
        return persisted != null;
    }
    public void delete(Paper paper) {
        if (paper == null) return;
        paperDao.delete(paper);
    }
    public void saveOrUpdate(Paper paper){
        if (paper == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Paper is null. Operation aborted.");
            }
            return;
        }


        Paper persisted = paperDao.findById(paper.getId());
        if (persisted == null) {
            checkState(paper.validateBasicFields(), "Missing required fields in question: %s", paper);
            paperDao.create(paper);
        } else {
            checkState(paper.validate(), "Missing required fields in question: %s", paper);
            paperDao.update(paper);
        }
    }
    @Override
    public List<Paper> findAll(Project project) {
        return paperDao.findAll(project);
    }

    @Override
    public List<Paper> findAll(String projectName) {
        checkArgument(!Strings.isNullOrEmpty(projectName));
        Project project = projectService.find(projectName);
        return findAll(project);
    }

    @Override
    public List<Paper> findAll(Project project, int pageSize, int pageNumber) {
        checkArgument(project != null);
        checkArgument(pageSize > 1, "Page size must be greater than 1.");
        checkArgument(pageNumber > 0);
        return paperDao.findResultList("project", project, pageSize, pageNumber);
    }

}
