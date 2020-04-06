package cn.cstqb.exam.testmaker.actions.knowledgePoint;

public class SaveOrUpdateKnowledgePointAction extends BaseKnowledgePointAction {

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
        if (knowledgePoint.getChapter()==null) {
            addActionError(getText("error.kp.missing.chapter"));
            return;
        }
        validateParentChapter(knowledgePoint.getChapter());
    }

    @Override
	public String executeImpl() throws Exception {
        worker.createOrUpdate(knowledgePoint);
        return null;
    }
}
