package cn.cstqb.exam.testmaker.junit.rules.datageneration.project;

import cn.cstqb.exam.testmaker.dao.ProjectStatusDao;
import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.AbstractDataGenerationRule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public class StatusGenerationRule extends AbstractDataGenerationRule {
    protected ProjectStatusDao projectStatusDao;

    public StatusGenerationRule() {
        super();
    }

    public void populate() {
        Long count = projectStatusDao.size();
        if (count>dataCount) {
            System.out.printf("There are already %d records available for [%s]! No need to populate.\n",
                    count, ProjectStatus.class.getSimpleName());
            return;
        }
        createSyllabus();
    }

    @Override
    protected void init() {
        projectStatusDao = injector.getInstance(ProjectStatusDao.class);
    }

    protected void createSyllabus() {
        for (int i = 0; i < dataCount; i++) {
            ProjectStatus s = new ProjectStatus(String.format("PROJECT-STATUS-%d (%d)", i, System.currentTimeMillis()));
            projectStatusDao.create(s);
        }
    }

    public ProjectStatusDao getProjectStatusDao() {
        return projectStatusDao;
    }
}
