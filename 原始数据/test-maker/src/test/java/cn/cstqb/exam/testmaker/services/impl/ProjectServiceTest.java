package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.junit.rules.DefaultJpaRule;
import cn.cstqb.exam.testmaker.services.IProjectService;
import cn.cstqb.exam.testmaker.services.IUserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class ProjectServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static IProjectService projectService;
    private static IUserService userService;
    @ClassRule
    public static DefaultJpaRule rule = new DefaultJpaRule();

    @BeforeClass
    public static void startUp() {
        projectService = rule.getInjector().getInstance(IProjectService.class);
        userService = rule.getInjector().getInstance(IUserService.class);
    }

    @Test
    public void testFindProjects() throws Exception {

    }

    @Test
    public void testFindProjects1() throws Exception {
        User user=userService.findUser("wase");
        logger.info("Loaded user: {}", user);
        List<Project> projects = projectService.findProjects(user, false);
        assertTrue(projects!=null && !projects.isEmpty());
        logger.info("{} projects found for user {}", projects!=null?projects.size():0, user.getUsername());
        for (Project project : projects) {
            logger.info("Project found: {}", project);
        }
    }

    @Test
    public void testFindFirst() throws Exception {

    }
}