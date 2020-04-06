package cn.cstqb.exam.testmaker.interceptors;

import org.apache.struts2.interceptor.ActionMappingParametersInteceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/24
 * Time: 17:48
 */
public class URLInterceptor extends ActionMappingParametersInteceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected boolean acceptableName(String name) {
/*        boolean accepted = isWithinLengthLimit(name) && !isExcluded(name) && isAccepted(name);
        if (devMode && accepted) { // notify only when in devMode
            LOG.debug("Parameter [#0] was accepted and will be appended to action!", name);
        }*/
        if (logger.isDebugEnabled()) {
            logger.debug("{} - Checking action mapping name: {}",name);
        }
        return true;
    }
}
