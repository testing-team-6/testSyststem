package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.modules.IChapterModuleWorker;
import cn.cstqb.exam.testmaker.modules.IKnowledgePointModuleWorker;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.modules.impl.ChapterModuleWorkerImpl;
import cn.cstqb.exam.testmaker.modules.impl.KnowledgePointModuleWorker;
import cn.cstqb.exam.testmaker.modules.impl.SyllabusModuleWorkerImpl;

import com.google.inject.AbstractModule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 21:08
 */
public class WorkerModule extends AbstractModule {
    /**
     * Configures a {@link javax.xml.bind.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(ISyllabusModuleWorker.class).to(SyllabusModuleWorkerImpl.class);
        bind(IChapterModuleWorker.class).to(ChapterModuleWorkerImpl.class);
        bind(IKnowledgePointModuleWorker.class).to(KnowledgePointModuleWorker.class);
    }
}
