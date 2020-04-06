package cn.cstqb.exam.testmaker.actions.chapter;

import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.modules.impl.ChapterModuleWorkerImpl;

public class SaveOrUpdateChapterAction extends BaseChapterAction{
	
	@Override
	public String executeImpl() throws Exception {
        chapterModuleWorker.createOrUpdate(chapter);
        return null;
    }
}
