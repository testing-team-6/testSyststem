package cn.cstqb.exam.testmaker;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.digester.Rule;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.inject.Inject;
import com.opensymphony.xwork2.interceptor.annotations.Before;

import cn.cstqb.exam.testmaker.actions.chapter.SaveOrUpdateChapterAction;
import cn.cstqb.exam.testmaker.dao.ProjectDao;
import cn.cstqb.exam.testmaker.dao.QuestionDao;
import cn.cstqb.exam.testmaker.dao.UserDao;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.datageneration.DefaultGenerationRule;

/**
 * 
 * @author wushuang
 *
 */
public class InitialTest {
	private UserDao userDao;
	private ProjectDao projectDao;
	private QuestionDao questionDao;
	
	@ClassRule
	public static DefaultGenerationRule rule = new DefaultGenerationRule();
	
	@Before
	public void setUp() {
		userDao = rule.getInjector().getInstance(UserDao.class);
		projectDao = rule.getInjector().getInstance(ProjectDao.class);
		questionDao = rule.getInjector().getInstance(QuestionDao.class);
	}
	
	@Test
	public void create() {
		//create 3 users
		for (int i = 0; i < 3; i++) {
			User user = new User(String.format("user-%d-%d", i, System.currentTimeMillis()));
			user.setEmail(String.format("test-%d-%d@test.com", i, System.currentTimeMillis()));
			user.setPassword("test123");
			userDao.create(user);
		}
		//create a project
		Project project = new Project();
		project.setFacilitator(userDao.findById(userDao.getMaxID()));
	}
	
	
	
}
