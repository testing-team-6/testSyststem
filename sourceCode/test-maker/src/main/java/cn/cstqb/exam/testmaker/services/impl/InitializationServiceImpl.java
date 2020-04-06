package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.entities.User;
import cn.cstqb.exam.testmaker.services.IInitializationService;
import cn.cstqb.exam.testmaker.services.IUserService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/7/30
 * Time: 23:42
 */
public class InitializationServiceImpl implements IInitializationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private IUserService userService;

    InitializationServiceImpl() {
    }

    @Override
    public void initialize() throws Exception {
        logger.info("InitializationServiceImpl.initialize: populating built-in data..." );
        initBuiltInData();
    }

    private void initBuiltInData() {
        logger.debug("InitializationServiceImpl.initBuiltInData: initializing built-in data...." );

        //initialize default users.
        initDefaultUsers();
    }

    private void initDefaultUsers() {
        logger.debug("InitializationServiceImpl.initDefaultUsers: Creating default system admin..." );
        /*
         * Creates built-in admin user. The built-in admin is defined in application.conf.
         */

        User user=userService.createBuiltInUser();
        logger.info("InitializationServiceImpl.initDefaultUsers: The user [{}] is set as default system administrator.", user.getUsername() );
    }
}
