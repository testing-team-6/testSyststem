package sandbox;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/9
 * Time: 9:59
 */
public class DateFormatTest {
    public static void main(String[] args) {
        Date date = new Date();
        int style = DateFormat.LONG;
        DateFormat df = DateFormat.getDateInstance(style, Locale.SIMPLIFIED_CHINESE);
        System.out.printf("Formatted date: %s\n", df.format(date));
    }
}
