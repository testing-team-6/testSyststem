package cn.cstqb.exam.testmaker.util;

import com.google.common.base.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/7
 * Time: 8:53
 */
public class EntityNameUtils {
    public static final String REGEX_SYMBOLS_BLANK = "[\\p{Punct}\\p{javaWhitespace}]+";
    public static final boolean isNameValid(String name) {
        if(Strings.isNullOrEmpty(name)) return false;
        if(name.length()<5) return false; //minimum 4 characters
        if(name.matches(REGEX_SYMBOLS_BLANK)) return false;
        return true;
    }
}
