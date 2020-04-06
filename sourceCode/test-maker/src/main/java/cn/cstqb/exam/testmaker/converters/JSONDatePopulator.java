package cn.cstqb.exam.testmaker.converters;

import cn.cstqb.exam.testmaker.configuration.Constants;
import org.apache.struts2.json.JSONPopulator;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 15:50
 */
public class JSONDatePopulator extends JSONPopulator {
    public JSONDatePopulator() {
        super(Constants.DEFAULT_DATE_FORMAT);
    }
}
