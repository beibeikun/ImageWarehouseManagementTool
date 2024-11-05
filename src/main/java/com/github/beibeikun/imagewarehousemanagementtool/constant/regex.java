package com.github.beibeikun.imagewarehousemanagementtool.constant;

public class regex
{
    //这个正则表达式的作用是匹配符合以下格式的文件名：
    //前缀：必须以大写字母开头，并且只能包含大写字母和数字
    //编号：破折号后跟一串数字
    //可选的序号部分：可以有一个括号包裹的序号（数字），前面有一个空格
    //扩展名：文件扩展名可以是任意的合法字符，但不能包含点号 .
    public static String REGEX_STANDARD_FILE_NAME = "^[A-Z][A-Z0-9]*-\\d+( \\(\\d+\\))?\\.[^.]+$";
}
