package cn.cstqb.exam.testmaker.actions.syllabus;

import cn.cstqb.exam.testmaker.actions.BaseAction;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.services.ISyllabusService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public abstract class BaseSyllabusAction extends BaseAction {
    @Inject
    protected ISyllabusModuleWorker syllabusModuleWorker;
    @Inject
    protected ISyllabusService syllabusService;
    protected Syllabus syllabus;

    public BaseSyllabusAction() {
        super();
        injector.injectMembers(this);
	}

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     */
    @Override
    public void validateInput() {
        if (syllabus == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(Syllabus.class.getSimpleName())));
            return;
        }

        if (Strings.isNullOrEmpty(syllabus.getVersion())) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList("version")));
        }

        if (Strings.isNullOrEmpty(syllabus.getLevel())) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList("level")));
            return;
        }

        if (syllabus.getId() != null) {
			if (syllabus.getId() < 0) {
				addActionError(getText("error.entity.invalid", Lists.newArrayList(Syllabus.class.getSimpleName())));
				return;
			}
		}
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }

}
