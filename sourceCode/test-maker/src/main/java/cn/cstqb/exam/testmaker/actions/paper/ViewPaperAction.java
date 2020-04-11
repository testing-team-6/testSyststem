package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Question;
import com.google.common.collect.Lists;
import jdk.vm.ci.meta.Constant;

import java.util.List;


public class ViewPaperAction extends BasePaperAction {

    private int id;
    private Paper paper;

    @Override
    protected String executeImpl() throws Exception {
        logger.debug("ViewPaperAction.executeImpl: Loading paper details for #0", id );
        paper = paperService.find(id);
        if(paper == null){
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(getText("label.entity.paper"))));
            return Constants.RESULT_NOT_FOUND;
        }
        return null;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }



}