package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import com.github.beibeikun.imagewarehousemanagementtool.constant.versionNumber;

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
        return true;
    }

    public static String officialVersionNumber()
    {
        return versionNumber.OFFICIAL;
    } //2024.03.01 //

    public static String betaVersionNumber()
    {
        return versionNumber.BETA;
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
