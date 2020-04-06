package cn.cstqb.exam.testmaker.modules;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 20:54
 */
public interface ISyllabusModuleWorker extends IModuleWorker {

    boolean syllabusExists(Syllabus syllabus);

    void createOrUpdate(Syllabus syllabus) throws Exception;

    void delete(Syllabus syllabus) throws EntityPersistenceException;

    Syllabus findSyllabus(int id);

    Syllabus findSyllabus(Syllabus syllabus);

    List<Syllabus> findAll();

    /**
     *
     * @return
     */
    List<Syllabus> findActive();

    /**
     * List all chapters for this syllabus
     * @param syllabus
     * @return
     */
    List<Chapter> findChapters(Syllabus syllabus);

    /**
     * Find all chapters in the db
     * @return
     */
    List<Chapter> findChapters();

    void createOrUpdate(Chapter... chapters) throws EntityPersistenceException;

    void createOrUpdate(KnowledgePoint... knowledgePoints) throws EntityPersistenceException;

    /**
     * Finds all knowledge points associated with this chapter
     * @param chapter The chapter to look for.
     * @return The knowledge points for this chapter. If the chapter is null, all knowledge points will be returned.
     */
    List<KnowledgePoint> findKnowledgePoints(Chapter chapter);

    void delete(KnowledgePoint knowledgePoint);
}
