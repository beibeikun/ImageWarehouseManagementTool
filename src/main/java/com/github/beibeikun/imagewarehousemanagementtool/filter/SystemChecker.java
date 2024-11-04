package com.github.beibeikun.imagewarehousemanagementtool.filter;

/**
 * 系统识别工具类，用于识别当前操作系统类型。
 */
public class SystemChecker
{
    /**
     * 识别当前操作系统类型，并返回相应的整数值。
     *
     * @return 操作系统类型的整数值
     */
    public static int identifySystem_int()
    {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS"))
        {
            return 1; // Mac OS
        }
        else if (osName.startsWith("Windows"))
        {
            return 2; // Windows
        }
        else
        {
            return 3; // Unix or Linux
        }
    }

    /**
     * 识别当前操作系统类型，并返回相应的路径分隔符字符串。
     *
     * @return 路径分隔符字符串
     */
    public static String identifySystem_String()
    {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS"))
        {
            return "/"; // Mac OS
        }
        else if (osName.startsWith("Windows"))
        {
            return "\\"; // Windows
        }
        else
        {
            return "/"; // Unix or Linux
        }
    }
}
