package cn.cstqb.exam.testmaker.converters;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import cn.cstqb.exam.testmaker.configuration.Constants;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/9
 * Time: 9:47
 */
public class DateConverter extends StrutsTypeConverter {
    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
    private static final ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();
    private static SimpleDateFormat dateFormat;

    public DateConverter() {
        dateFormat = new SimpleDateFormat(configContext.getDefaultDateFormat(), Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * Converts one or more String values to the specified class.
     *
     * @param context the action context
     * @param values  the String values to be converted, such as those submitted from an HTML form
     * @param toClass the class to convert to
     * @return the converted object
     */
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String input = values[0];
        Date target=null;
        if (toClass.equals(Date.class)) {
            try {
                target = dateFormat.parse(input);
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("DateConverter.convertFromString: Invalid input date value {}", input );
            }
        }
        return target;
    }

    /**
     * Converts the specified object to a String.
     *
     * @param context the action context
     * @param o       the object to be converted
     * @return the converted String
     */
    @Override
    public String convertToString(Map context, Object o) {
        String output = "";
        if (o instanceof Date) {
            Date date = (Date) o;
            output = dateFormat.format(date);
            logger.info("DateConverter.convertToString: {}",output );
        }
        return output;
    }
}
