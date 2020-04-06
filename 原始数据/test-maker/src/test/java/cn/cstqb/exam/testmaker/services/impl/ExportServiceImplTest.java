package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IExportService;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.inject.Inject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExportServiceImplTest {

    @Inject
    private IExportService exportService;
    @Inject
    private IProjectService projectService;

    private Project project;

    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    public ExportServiceImplTest() {
        rule.getInjector().injectMembers(this);
    }

    @Before
    public void setUp() throws Exception {
        project = projectService.find(1);
    }

    @Test
    public void testExport() throws Exception {
        int count = exportService.export(project);
        assertTrue(count > 0);
    }
}
