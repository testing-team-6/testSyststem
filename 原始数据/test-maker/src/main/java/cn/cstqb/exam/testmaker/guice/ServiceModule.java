package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.actions.UserValidator;
import cn.cstqb.exam.testmaker.services.*;
import cn.cstqb.exam.testmaker.services.impl.*;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/8
 * Time: 22:11
 */
public class ServiceModule extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(IUserService.class).to(UserServiceImpl.class);
        bind(ISyllabusService.class).to(SyllabusServiceImpl.class);
        bind(IChapterService.class).to(ChapterServiceImpl.class);
        bind(IKnowledgePointService.class).to(KnowledgePointServiceImpl.class);
        bind(IQuestionService.class).to(QuestionServiceImpl.class);
        bind(IQuestionStatusService.class).to(QuestionStatusServiceImpl.class);
        bind(IReviewCommentService.class).to(ReviewCommentServiceImpl.class);
        bind(IProjectService.class).to(ProjectServiceImpl.class);
        bind(IProjectStatusService.class).to(ProjectStatusServiceImpl.class);
        bind(IQuestionChoiceService.class).to(QuestionChoiceServiceImpl.class);
        bind(IQuestionLanguageService.class).to(QuestionLanguageServiceImpl.class);
        bind(IQuestionTypeService.class).to(QuestionTypeServiceImpl.class);
        bind(IQuestionLanguageService.class).to(QuestionLanguageServiceImpl.class);
        bind(IExportService.class).to(ExportServiceImpl.class);
        bind(IReportingService.class).to(ReportingServiceImpl.class);
        bind(IDebugInitializationService.class).to(DebugInitializationServiceImpl.class);
        bind(IQuestionChoiceImageService.class).to(QuestionChoiceImageServiceImpl.class);
        bind(IQuestionImageService.class).to(QuestionImageServiceImpl.class);
        bind(UserValidator.class).in(Singleton.class);
        bind(IInitializationService.class).to(InitializationServiceImpl.class);
    }
}
