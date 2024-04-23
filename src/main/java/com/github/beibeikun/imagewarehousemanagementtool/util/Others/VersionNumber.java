package com.github.beibeikun.imagewarehousemanagementtool.util.Others;

/**
 * 版本号工具类，用于获取版本号和 GitHub 仓库地址。
 */
public class VersionNumber
{
    /**
     * 获取版本号。
     *
     * @return 版本号字符串
     */
    public static boolean getReleaseType()
    {
        /*版本类型
         * true-正式版
         * false-测试版
         * */
        return false;
    }

    public static String officialVersionNumber()
    {
        return "1.2";
    } //2024.03.01 //

    public static String betaVersionNumber()
    {
        return "1.3.240422";
    }


    /**
     * 获取 GitHub 仓库地址。
     *
     * @return GitHub 仓库地址字符串
     */
    public static String getGithub()
    {
        return "https://github.com/beibeikun/ImageWarehouseManagementTool";
    }

    public static String getGithubVersionNumberApi()
    {
        return "https://api.github.com/repos/beibeikun/ImageWarehouseManagementTool";
    }

    public static String getGithubLatestRelease()
    {
        return "https://github.com/beibeikun/ImageWarehouseManagementTool/releases/latest";
    }
}
