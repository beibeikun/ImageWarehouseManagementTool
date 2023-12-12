package Module.CheckOperations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于判断字符串是否包含中文字符的工具类。
 */
public class ChineseCharactersChecker {
    /**
     * 判断字符串是否包含中文字符。
     *
     * @param str 待判断的字符串
     * @return 如果字符串包含中文字符，则返回true；否则返回false
     */
    public boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[一-龥]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
