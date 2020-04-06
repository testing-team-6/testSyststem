package cn.cstqb.exam.testmaker.actions.questionstatus;

import cn.cstqb.exam.testmaker.entities.QuestionStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/28
 * Time: 12:37
 */
public class ListAllTransitionsAction extends BaseQuestionStatusTransitionAction {
    private Map<Integer, Set<QuestionStatus>> map=new HashMap<>();
    private int maxTransitions;
    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {

    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    protected String executeImpl() throws Exception {
        logger.debug("ListAllTransitionsAction.executeImpl: Querying all transitional statuses");
        this.statuses = statusService.findAllStatuses();



        logger.info("ListAllTransitionsAction.executeImpl: statuses found: " + statuses.size() );

        for (int i = 0; i < statuses.size(); i++) {
            QuestionStatus qs = statuses.get(i);
            logger.debug("ListAllTransitionsAction.executeImpl: Finding transitional status for #0", qs.toString());
            Set<QuestionStatus> transitions = statusService.findTransitionalStates(qs);
            int size = transitions.size();
            if (size> maxTransitions) {
                maxTransitions=size;
            }
            logger.info("ListAllTransitionsAction.executeImpl: transitions found: " + size );
            map.put(i, transitions);
        }
        return null;
    }

    public Map<Integer, Set<QuestionStatus>> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Set<QuestionStatus>> map) {
        this.map = map;
    }

    public int getMaxTransitions() {
        return maxTransitions;
    }

    public void setMaxTransitions(int maxTransitions) {
        this.maxTransitions = maxTransitions;
    }
}
