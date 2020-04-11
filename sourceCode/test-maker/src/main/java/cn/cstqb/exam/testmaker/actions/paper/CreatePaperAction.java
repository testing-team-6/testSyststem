package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.configuration.Constants;
import com.google.common.collect.Lists;

public class CreatPaperAction extends BasePaperAction{
    @Override
    protected String executeImpl() throws Exception {
        if (paper.isValidID()) {
            if (!paperService.exists(paper)) {
                addActionError(getText("error.paper.not.found", Lists.newArrayList(paper.getId())));
                return Constants.RESULT_NOT_FOUND;
            }
        }
        paperService.saveOrUpdate(paper);
        return null;
    }
}