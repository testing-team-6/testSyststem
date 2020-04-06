package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.dao.*;
import cn.cstqb.exam.testmaker.dao.impl.*;
import com.google.inject.AbstractModule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:05
 */
public class DaoModule extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(SyllabusDao.class).to(SyllabusDaoImpl.class);
        bind(ChapterDao.class).to(ChapterDaoImpl.class);
        bind(KnowledgePointDao.class).to(KnowledgePointDaoImpl.class);

        /*
         * Question related Dao
         */
        bind(QuestionLanguageDao.class).to(QuestionLanguageDaoImpl.class);
        bind(QuestionStatusDao.class).to(QuestionStatusDaoImpl.class);
        bind(QuestionTypeDao.class).to(QuestionTypeDaoImpl.class);
        bind(QuestionDao.class).to(QuestionDaoImpl.class);
        bind(QuestionChoiceDao.class).to(QuestionChoiceDaoImpl.class);
        bind(QuestionStatusTransitionDao.class).to(QuestionStatusTransitionDaoImpl.class);
        bind(ReviewCommentDao.class).to(ReviewCommentDaoImpl.class);
        bind(ReviewAssessmentDao.class).to(ReviewAssessmentDaoImpl.class);
        bind(QuestionImageDao.class).to(QuestionImageDaoImpl.class);
        bind(QuestionChoiceImageDao.class).to(QuestionChoiceImageDaoImpl.class);
        bind(QuestionReportingDao.class).to(QuestionReportingDaoImpl.class);

        /*
         * Project related Dao
         */
        bind(ProjectDao.class).to(ProjectDaoImpl.class);
        bind(ProjectStatusDao.class).to(ProjectStatusDaoImpl.class);


        /*
         * User and access control Dao
         */
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(RoleDao.class).to(RoleDaoImpl.class);
    }
}
