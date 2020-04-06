package cn.cstqb.exam.testmaker.actions.knowledgePoint;
import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;

import java.util.List;

public class ListKnowledgePointAction extends BaseKnowledgePointAction{
    private Chapter chapter;
    private List<KnowledgePoint> aaData;

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        validatePaginationParams();
    }

    @Override
    protected String doExecuteImpl() {
        this.aaData = pointService.findKnowledgePoints(chapter,this.pageSize, this.pageNumber);
        return null;
    }

    public List<KnowledgePoint> getAaData() {
        return aaData;
    }

    public void setAaData(List<KnowledgePoint> aaData) {
        this.aaData = aaData;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
