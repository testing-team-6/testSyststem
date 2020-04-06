/**
 * 
 */
package cn.cstqb.exam.testmaker.modules;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;

/**
 * @author wushuang
 *
 */
public interface IKnowledgePointModuleWorker {
	
	boolean knowledgePointExists(KnowledgePoint point);
	
	void createOrUpdate(KnowledgePoint point) throws EntityPersistenceException;
	
	void delete(KnowledgePoint point) throws EntityPersistenceException;
	
	KnowledgePoint findKnowledgePoint(int id);
	
	KnowledgePoint findKnowledgePoint(KnowledgePoint point);
	
	List<KnowledgePoint> findAll();
	
}
