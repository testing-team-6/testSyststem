package cn.cstqb.exam.testmaker.modules.impl;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;
import cn.cstqb.exam.testmaker.modules.AbstractModuleWorker;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.services.IChapterService;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;
import cn.cstqb.exam.testmaker.services.ISyllabusService;
import com.google.common.collect.Lists;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 20:54
 */
public class SyllabusModuleWorkerImpl extends AbstractModuleWorker implements ISyllabusModuleWorker{

    private ISyllabusService syllabusService;
    private IChapterService chapterService;
    private IKnowledgePointService knowledgePointService;

    @Inject
    public SyllabusModuleWorkerImpl(ISyllabusService syllabusService, IChapterService chapterService, IKnowledgePointService knowledgePointService) {
        this.syllabusService = syllabusService;
        this.chapterService = chapterService;
        this.knowledgePointService = knowledgePointService;
    }

    /**
     * Check if the currently logged-in user has privilege to access this worker
     *
     * @return
     */
    @Override
    public boolean isIllegible() {
        //TODO to be implemented
        return false;
    }

    @Override
    public boolean syllabusExists(Syllabus syllabus) {
        return syllabusService.exists(syllabus);
    }

    @Override
    public void createOrUpdate(Syllabus syllabus) throws Exception {
        if(syllabus==null ) throw new EntityPersistenceException(getText("error.entity.invalid", Lists.newArrayList(Syllabus.class.getName())));
        if(!syllabus.validate()) throw new EntityPersistenceException(getText("error.entity.field.missing.required", Lists.newArrayList(syllabus)));
        syllabusService.saveOrUpdate(syllabus);
    }

    @Override
    public void delete(Syllabus syllabus) throws EntityPersistenceException {
        if(syllabus==null || !syllabus.validate() || syllabus.getId()==null) {
            throw new EntityPersistenceException(getText("error.entity.invalid", Lists.newArrayList(Syllabus.class.getName())));
        }
        syllabusService.delete(syllabus);
    }

    @Override
    public Syllabus findSyllabus(int id) {
        if(id<1) {
            logger.error("SyllabusModuleWorkerImpl.findSyllabus: invalid ID provided: {}", id );
            return null;
        }
        return syllabusService.findSyllabus(id);
    }

    @Override
    public Syllabus findSyllabus(Syllabus syllabus) {
        if(syllabus==null) return null;
        return syllabusService.findSyllabus(syllabus);
    }

    @Override
    public List<Syllabus> findAll() {
        return syllabusService.findAll();
    }

    /**
     * @return
     */
    @Override
    public List<Syllabus> findActive() {
        return syllabusService.findActive();
    }

    @Override
    public void createOrUpdate(Chapter... chapters) throws EntityPersistenceException {
        if(chapters.length <1) return;
        for (Chapter chapter : chapters) {
            chapterService.saveOrUpdate(chapter);
        }
    }

    @Override
    public void createOrUpdate(KnowledgePoint... knowledgePoints) throws EntityPersistenceException {
        if(knowledgePoints.length <1) return;
        for (KnowledgePoint knowledgePoint : knowledgePoints) {
            knowledgePointService.saveOrUpdate(knowledgePoint);
        }
    }

    /**
     * List all chapters for this syllabus
     *
     * @param syllabus
     * @return
     */
    @Override
    public List<Chapter> findChapters(Syllabus syllabus) {
        return chapterService.findChapters(syllabus);
    }

    @Override
    public List<Chapter> findChapters() {
        return chapterService.findAll();
    }

    /**
     * Finds all knowledge points associated with this chapter
     *
     * @param chapter The chapter to look for.
     * @return The knowledge points for this chapter. If the chapter is null, all knowledge points will be returned.
     */
    @Override
    public List<KnowledgePoint> findKnowledgePoints(Chapter chapter) {
        if (chapter == null) {
            logger.info("ListKnowledgePointAction.executeImpl: No chapter is provided. Listing all knowledge points." );
            return knowledgePointService.findAll();
        } else {
            logger.info("ListKnowledgePointAction.executeImpl: Listing knowledge points for chapter [{}]", chapter.getTitle());
            return knowledgePointService.findKnowledgePoints(chapter);
        }
    }

    @Override
    public void delete(KnowledgePoint knowledgePoint) {
        if (knowledgePoint==null|| !knowledgePoint.validate() || !knowledgePoint.isValidID()) {
            logger.warn("SyllabusModuleWorkerImpl.delete: Unable to delete null/invalid object: {}", knowledgePoint );
            return;
        }
        KnowledgePoint persisted = knowledgePointService.findKnowledgePoint(knowledgePoint.getId());
        knowledgePointService.delete(persisted);
    }
}
