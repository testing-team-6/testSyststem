package cn.cstqb.exam.testmaker.actions.chapter;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Chapter;
import com.google.common.collect.Lists;

public class UpdateChapterAction extends BaseChapterAction{

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        if (!chapterModuleWorker.chapterExists(chapter)) {
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(chapter)));
            return Constants.RESULT_NOT_FOUND;
        }

        logger.debug("UpdateChapterAction.execute: loading persisted version for this chapter: {}", chapter);
        Chapter persisted = chapterModuleWorker.findChapter(chapter);

        if (persisted.equals(chapter)) {
            logger.warn("UpdateChapterAction.execute: no need to update {}", chapter);
            addActionError(getText("error.entity.notModified", Lists.newArrayList(persisted)));
            return Constants.RESULT_NOT_MODIFIED;
        }
        chapterModuleWorker.createOrUpdate(chapter);
        return null;
    }
}
