package cn.cstqb.exam.testmaker.actions.syllabus;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import com.google.common.collect.Lists;

public class DeleteSyllabusAction extends BaseSyllabusAction{

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
        if (syllabus.getId() == null || syllabus.getId() < 0) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(Syllabus.class.getSimpleName())));
		}
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        if (!syllabusModuleWorker.syllabusExists(syllabus)) {
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(syllabus)));
            return Constants.RESULT_NOT_FOUND;
        }
        logger.debug("DeleteSyllabusAction.execute: Loading persisted version for this syllabus: {}", syllabus );
        Syllabus persisted = syllabusService.findSyllabus(syllabus.getId());
        boolean inUse = syllabusService.isSyllabusInUse(persisted);
        logger.debug("DeleteSyllabusAction.executeImpl: is Syllabus [{}] in use? {}", syllabus, inUse );
        if (inUse) {
            logger.warn("DeleteSyllabusAction.executeImpl: Syllabus [{}] is still in use. Unable to delete!" );
            addActionError(getText("error.syllabus.delete.inUse"));
            return Constants.RESULT_NOT_MODIFIED;
        }
        syllabusModuleWorker.delete(persisted);
        return null;
    }

}
