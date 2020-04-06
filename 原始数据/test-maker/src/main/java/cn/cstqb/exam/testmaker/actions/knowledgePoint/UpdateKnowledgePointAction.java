package cn.cstqb.exam.testmaker.actions.knowledgePoint;

public class UpdateKnowledgePointAction extends BaseKnowledgePointAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        worker.createOrUpdate(knowledgePoint);
        return null;
    }
}
