package cn.cstqb.exam.testmaker.actions.chapter;

import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Chapter;

public class DeleteChapterAction extends BaseChapterAction{

    @Override
    protected String executeImpl() throws Exception {
        if (!chapterModuleWorker.chapterExists(chapter)) {
            addActionError(getText("error.entity.not.exist", Lists.newArrayList(chapter)));
            return Constants.RESULT_NOT_FOUND;
        }
        logger.debug("DeleteChapterAction.execute: Loading persisted version for this chapter: {}", chapter);
        Chapter persisted = chapterModuleWorker.findChapter(chapter);
        try {
            chapterModuleWorker.delete(persisted);
        }
        catch (Exception ex) {
            addActionError(getText("error.chapter.delete.inUse"));
            return Constants.RESULT_NOT_MODIFIED;
        }
        return null;
    }

}
