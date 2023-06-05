package Module.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*--------------------------------------------
用于检测一个字符串中是否包含中文字符。它通过使用正则表达式来匹配字符串中的中文字符，并返回是否找到匹配项。

具体来说，代码中的isContainChinese方法接受一个字符串作为参数，并创建一个正则表达式模式[\u4E00-\u9FA5]。
该模式表示Unicode编码范围内的中文字符（从\u4E00到\u9FA5）。然后，使用Matcher对象对输入的字符串进行匹配操作。
如果找到任何一个中文字符，m.find()方法将返回true，否则返回false。
--------------------------------------------*/

public class IsContainChinese
{
    public boolean isContainChinese(String str)
    {

        Pattern p = Pattern.compile("[\u4E00-\u9FA5]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
