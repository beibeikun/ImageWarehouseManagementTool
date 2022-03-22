package algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class isContainChinese
{
    public boolean isContainChinese(String str)
    {

        Pattern p = Pattern.compile("[\u4E00-\u9FA5]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
