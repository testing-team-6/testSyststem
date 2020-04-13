package cn.cstqb.exam.testmaker.actions.projects.current;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.services.IPaperService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/15
 * Time: 23:37
 */
public class ListPapers extends BaseCurrentProjectAction {
    @Inject
    private IPaperService paperService;
    private List<Paper> papers;

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
        validatePaginationParams();
    }

    /**
     * method to load entity count from service layer
     */
    @Override
    protected void initEntityCount() {
        this.count = paperService.findAll(sessionProject).size();
    }


    /**
     * subclasses should implement this method to put business logic
     *
     * @return
     */
    @Override
    protected String doExecuteImpl() {
        logger.debug("ListProjectPapers.doExecuteImpl: Loading papers for project: #0", sessionProject.getName() );
        papers = paperService.findAll(sessionProject, pageSize, pageNumber);
        return null;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
}
