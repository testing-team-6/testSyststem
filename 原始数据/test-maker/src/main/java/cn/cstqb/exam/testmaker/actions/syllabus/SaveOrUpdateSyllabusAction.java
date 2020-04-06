package cn.cstqb.exam.testmaker.actions.syllabus;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.struts2.ServletActionContext;

public class SaveOrUpdateSyllabusAction extends BaseSyllabusAction {

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        syllabusModuleWorker.createOrUpdate(syllabus);
        return null;
    }

}
