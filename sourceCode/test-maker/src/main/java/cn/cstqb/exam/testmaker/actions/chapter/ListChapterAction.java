package cn.cstqb.exam.testmaker.actions.chapter;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.Syllabus;

import java.util.List;

public class ListChapterAction extends BaseChapterAction{
	private List<Chapter> aaData;
    private int syllabusId;

    @Override
    public void validateInput() {
/*        if (syllabusId<1) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(getText("label.entity.syllabus"))));
            return;
        }
        syllabus = syllabusService.findSyllabus(syllabusId);
*//*        if (syllabus == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(syllabusId,getText("label.entity.syllabus"))));
            return;
        }*//*

        if (syllabus != null && !syllabus.validate()) {
            addActionError(getText("error.entity.validation.incomplete", Lists.newArrayList(getText("label.entity.syllabus"))));
        }*/
    }

    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        if (syllabusId >0) {
            aaData = chapterService.findChapters(syllabusId);
        } else {
            aaData = chapterService.findAll();
        }
        return null;
    }

	public List<Chapter> getAaData() {
		return aaData;
	}

    public void setSyllabusId(int syllabusId) {
        this.syllabusId = syllabusId;
    }
}
