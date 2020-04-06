package cn.cstqb.exam.testmaker.actions.chapter;

import cn.cstqb.exam.testmaker.services.IChapterService;
import cn.cstqb.exam.testmaker.services.ISyllabusService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.modules.IChapterModuleWorker;
import com.google.inject.Inject;


public abstract class BaseChapterAction extends BaseAction {
	@Inject
    protected IChapterModuleWorker chapterModuleWorker;
    @Inject
    protected IChapterService chapterService;
    @Inject
    protected ISyllabusService syllabusService;
	protected Chapter chapter;

	public BaseChapterAction() {
		super();
	}

	@Override
	public void validateInput() {
		if (chapter == null) {
			addActionError(getText("error.entity.invalid", Lists.newArrayList(Chapter.class.getSimpleName())));
			return;
		}

		if (Strings.isNullOrEmpty(chapter.getTitle())) {
			addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.field.title"))));
		}

        if (chapter.getSyllabus()==null) {
            addActionError(getText("error.chapter.missing.syllabus"));
            return;
        }

        if (chapter.getId() != null) {
			if (chapter.getId() < 0) {
				addActionError(getText("error.entity.invalid", Lists.newArrayList(Chapter.class.getSimpleName())));
				return;
			}
		}
    }

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

}
