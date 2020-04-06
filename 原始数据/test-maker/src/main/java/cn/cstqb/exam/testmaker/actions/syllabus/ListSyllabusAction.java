package cn.cstqb.exam.testmaker.actions.syllabus;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

/**
 * Created by wushuang on 2015/2/4.
 */
public class ListSyllabusAction extends BaseSyllabusAction{
    private List<Syllabus> aaData;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
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
    @Override
    protected String executeImpl() throws Exception {
        aaData = syllabusModuleWorker.findAll();
        logger.info("ListSyllabusAction.execute: Found aaData #0", aaData.size()+"");
        if (aaData == null) {
            return Constants.RESULT_NOT_FOUND;
        }
        return null;
    }

    /**
     * List active syllabuses
     * @return
     */
    public String active() {
        aaData = syllabusModuleWorker.findActive();
        logger.info("ListSyllabusAction.listActive: Found aaData #0", aaData.size()+"");
        if (aaData == null) {
            return Constants.RESULT_NOT_FOUND;
        }
        return SUCCESS;
    }

    public List<Syllabus> getAaData() {
        return aaData;
    }

    public void setAaData(List<Syllabus> aaData) {
        this.aaData = aaData;
    }
}
