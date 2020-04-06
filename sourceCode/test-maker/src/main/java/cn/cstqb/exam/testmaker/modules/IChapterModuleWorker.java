package cn.cstqb.exam.testmaker.modules;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;

public interface IChapterModuleWorker extends IModuleWorker{
	
	boolean chapterExists(Chapter chapter);
	
	void createOrUpdate(Chapter chapter) throws Exception;
	
	void delete(Chapter chapter) throws EntityPersistenceException;
	
	Chapter findChapter(int id);
	
	Chapter findChapter(Chapter chapter);
	
	List<Chapter> findAll();

    /**
     * List all chapters for this syllabus
     * @param syllabus
     * @return
     */
    List<Chapter> findChapters(Syllabus syllabus);

    void createOrUpdate(KnowledgePoint...knowledgePoints) throws EntityPersistenceException;
	
}
