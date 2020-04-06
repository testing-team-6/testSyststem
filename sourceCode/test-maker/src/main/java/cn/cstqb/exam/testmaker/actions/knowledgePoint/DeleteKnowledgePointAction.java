package cn.cstqb.exam.testmaker.actions.knowledgePoint;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.modules.impl.KnowledgePointModuleWorker;

import java.util.List;

public class DeleteKnowledgePointAction extends BaseKnowledgePointAction {

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        super.validateInput();
        validateParentChapter(knowledgePoint.getChapter());
    }

    @Override
    protected String executeImpl() throws Exception {
        logger.debug("DeleteKnowledgePointAction.executeImpl: Deleting... {}", knowledgePoint );

        /*
         * A knowledge point cannot be deleted if it's still in use.
         */
        List<Question> questionsInUse = pointService.findUsingQuestions(knowledgePoint);
        if (questionsInUse != null && !questionsInUse.isEmpty()) {
            StringBuilder questionIds = new StringBuilder();
            for (Question question : questionsInUse) {
                questionIds.append(question.getId()).append(", ");
            }
            logger.warn("DeleteKnowledgePointAction.executeImpl: The following questions are still using the knowledge point: [{}]", questionIds);

            addActionError(getText("error.knowledgePoint.delete.inUse", new String[]{questionIds.toString().trim()}));
            return Constants.RESULT_NOT_MODIFIED;
        }

        logger.debug("DeleteChapterAction.execute: Loading persisted version for this knowledge point: {}", knowledgePoint);
        KnowledgePoint persisted = pointService.findKnowledgePoint(knowledgePoint.getId());
        pointService.delete(persisted);
        return null;
    }
}
