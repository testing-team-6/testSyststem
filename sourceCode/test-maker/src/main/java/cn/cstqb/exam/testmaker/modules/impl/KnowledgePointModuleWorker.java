package cn.cstqb.exam.testmaker.modules.impl;

import java.util.List;

import com.google.inject.Inject;

import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;
import cn.cstqb.exam.testmaker.modules.AbstractModuleWorker;
import cn.cstqb.exam.testmaker.modules.IKnowledgePointModuleWorker;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;

public class KnowledgePointModuleWorker extends AbstractModuleWorker implements IKnowledgePointModuleWorker {
	private IKnowledgePointService knowledgePointService;
	
	@Inject
	public KnowledgePointModuleWorker(IKnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}
	
	@Override
	public boolean isIllegible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean knowledgePointExists(KnowledgePoint point) {
		//return knowledgePointService.exists(point);
		return false;
	}

	@Override
	public void createOrUpdate(KnowledgePoint point)
			throws EntityPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(KnowledgePoint point) throws EntityPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public KnowledgePoint findKnowledgePoint(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KnowledgePoint findKnowledgePoint(KnowledgePoint point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgePoint> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
