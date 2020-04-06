package cn.cstqb.exam.testmaker.util;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/14
 * Time: 22:48
 */
public class DateTimeUtils {
    private static final ApplicationConfigContext config = ApplicationConfigContext.getInstance();
    private static final SimpleDateFormat formatter = new SimpleDateFormat(config.getDefaultDateFormat());

    /**
     * Format the given date with the system default date time format
     * @param date
     * @return Formatted date string
     */
    public static final String format(Date date) {
        if(date==null) return null;
        return formatter.format(date);
    }
}
