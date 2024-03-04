package Module.Others;

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
    public static String getVersionNumber()
    {
        return "Build: Beta1.2.240302 by beibeikun";
    } //2024.03.01
    public static String finalVersionNumber() {return "V1.2";}

    /**
     * 获取 GitHub 仓库地址。
     *
     * @return GitHub 仓库地址字符串
     */
    public static String getGithub() {return "https://github.com/beibeikun/ImageWarehouseManagementTool";}
    public static String getGithubVersionNumberApi() {return "https://api.github.com/repos/beibeikun/ImageWarehouseManagementTool";}
    public static String getGithubLatestRelease() {return  "https://github.com/beibeikun/ImageWarehouseManagementTool/releases/latest";}
}
