package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IQuestionService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class BasePaperActionTest {
    @Inject protected IPaperService paperService;
    @Inject protected IQuestionService questionService;
    @Inject private Paper paper;
    @Inject private Paper paperTemp;
    private BasePaperAction action = new BasePaperAction() {
        @Override
        protected String executeImpl() throws Exception {
            return "yes";
        }
    };
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();
    public BasePaperActionTest(){

    }
    @Before
    public void setUp() throws Exception {
        rule.getInjector().injectMembers(this);
        action.execute();
        action.setPaper(paper);
    }
    @Test
    public void getPaper() {
        assertEquals(paper,action.getPaper());
    }

    @Test
    public void setPaper() {
        action.setPaper(paperTemp);
        assertEquals(paperTemp,action.getPaper());
    }
}