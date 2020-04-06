package cn.cstqb.exam.testmaker.actions.knowledgePoint;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Syllabus;
import cn.cstqb.exam.testmaker.util.ListSort;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/7/20
 * Time: 22:28
 */
public class ListKnowledgePointBySyllabus extends BaseKnowledgePointAction {
    private Syllabus syllabus;
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
    protected void initEntityCount() {
        count=pointService.findKnowledgePoints(syllabus).size();
    }

    @Override
    protected String doExecuteImpl() {
        this.aaData = pointService.findKnowledgePoints(syllabus,this.pageSize, this.pageNumber);
        ListSort.sortList(aaData, "number", null);
        return null;
    }

    public List<KnowledgePoint> getAaData() {
        return aaData;
    }

    public void setAaData(List<KnowledgePoint> aaData) {
        this.aaData = aaData;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }
}
