package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IDebugInitializationService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class DebugInitializationServiceImplTest {
    private  IDebugInitializationService service;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule(true);

    @Before
    public void setUp() throws Exception {
        service = rule.getInjector().getInstance(IDebugInitializationService.class);
        service.setCount(10);
    }

    @Test
    public void testInitSyllabus() throws Exception {
        service.initSyllabus();
    }

    @Test
    public void testInitUsers() throws Exception {
        service.initUsers();
    }

    @Test
    public void testInitQuestionAttributes() throws Exception {
        service.initQuestionAttributes();
    }
}
