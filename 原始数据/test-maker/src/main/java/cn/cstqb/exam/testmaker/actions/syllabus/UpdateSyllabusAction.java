package cn.cstqb.exam.testmaker.actions.syllabus;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import com.google.common.collect.Lists;

public class UpdateSyllabusAction extends BaseSyllabusAction{

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("UpdateSyllabusAction.execute: syllabus {}",syllabus );
        if (!syllabusModuleWorker.syllabusExists(syllabus)) {
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(syllabus)));
            return Constants.RESULT_NOT_FOUND;
        }

        logger.debug("UpdateSyllabusAction.execute: Loading persisted version for this syllabus: {}", syllabus );
        Syllabus persisted = syllabusModuleWorker.findSyllabus(syllabus);

        /*
         * If the form syllabus is the same as the persisted, return not modified
         */
        if (persisted.equals(syllabus)) {
            logger.warn("UpdateSyllabusAction.execute: No need to update {}", syllabus );
            addActionError(getText("error.entity.notModified", Lists.newArrayList(persisted)));
            return Constants.RESULT_NOT_MODIFIED;
        }

        syllabusModuleWorker.createOrUpdate(syllabus);
        return null;
    }

}
