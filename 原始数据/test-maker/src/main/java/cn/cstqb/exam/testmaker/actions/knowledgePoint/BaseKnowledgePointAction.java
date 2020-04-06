package cn.cstqb.exam.testmaker.actions.knowledgePoint;

import cn.cstqb.exam.testmaker.actions.AbstractPaginationAction;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.modules.IKnowledgePointModuleWorker;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.services.IKnowledgePointService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public abstract class BaseKnowledgePointAction extends AbstractPaginationAction {
	protected KnowledgePoint knowledgePoint;
    @Inject
    protected ISyllabusModuleWorker worker;
    @Inject
    protected IKnowledgePointService pointService;
    @Inject
    protected IKnowledgePointModuleWorker pointModuleWorker;

	public BaseKnowledgePointAction() {
       injector.injectMembers(this);
	}

	public KnowledgePoint getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {
        count=pointService.size().intValue();
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (knowledgePoint==null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.knowledgePoint"))));
            return;
        }

        if (!knowledgePoint.validate()) {
            addActionError(getText("error.entity.validation.incomplete"));
            return;
        }

        if (knowledgePoint.getId() != null) {
			if (knowledgePoint.getId() < 0) {
				addActionError(getText("error.entity.invalid"));
				return;
			}
		}
    }

    protected void validateParentChapter(Chapter chapter) {
        if (chapter.getId()==null || chapter.getId()<1 || !chapter.validate()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.chapter"))));
            return;
        }
    }

    /**
     * subclasses should implement this method to put business logic
     *
     * @return
     */
    @Override
    protected String doExecuteImpl() {
        return null;
    }
}
