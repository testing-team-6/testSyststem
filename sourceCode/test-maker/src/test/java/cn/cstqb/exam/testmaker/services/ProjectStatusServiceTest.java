/**
 * 
 */
package cn.cstqb.exam.testmaker.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import cn.cstqb.exam.testmaker.entities.ProjectStatus;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;

/**
 * @author wushuang
 *
 */
public class ProjectStatusServiceTest {

	private IProjectStatusService service;
	
	@ClassRule
	public static DefaultJpaRule rule = new DefaultJpaRule();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = rule.getInjector().getInstance(IProjectStatusService.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link cn.cstqb.exam.testmaker.services.impl.ProjectStatusServiceImpl#saveOrUpdate(cn.cstqb.exam.testmaker.entities.ProjectStatus)}
	 * .
	 */
	@Test
	public void testCreate() throws Exception {
		ProjectStatus newProjectStatus = new ProjectStatus("new project status"+System.currentTimeMillis());
		service.saveOrUpdate(newProjectStatus);
		assertTrue(newProjectStatus.getId() > 0);
	}

	/**
	 * Test method for
	 * {@link cn.cstqb.exam.testmaker.services.impl.ProjectStatusServiceImpl#saveOrUpdate(cn.cstqb.exam.testmaker.entities.ProjectStatus)}
	 * .
	 */
	@Test
	public void testUpdate() throws Exception {
		ProjectStatus updateProjectStatus = new ProjectStatus(
				"update project status");
        updateProjectStatus.setFinish(true);
		service.saveOrUpdate(updateProjectStatus);
		updateProjectStatus.setName("updated");
		assertTrue(updateProjectStatus.getName() == "updated");
	}

	/**
	 * Test method for
	 * {@link cn.cstqb.exam.testmaker.services.impl.ProjectStatusServiceImpl#delete(cn.cstqb.exam.testmaker.entities.ProjectStatus)}
	 * .
	 */
	@Test
	public void testDelete() throws Exception {
		ProjectStatus deleteProjectStatus = new ProjectStatus(
				"delete project status");
		service.saveOrUpdate(deleteProjectStatus);
		service.delete(deleteProjectStatus);
	}

    @Test
    public void testFindStart() throws Exception {
        ProjectStatus status=service.findStartState();
        assertNotNull(status);

    }
}
