package Module;

/*--------------------------------------------
版本信息类，包含了两个方法：
VersionNumber() 方法返回一个表示版本号的字符串,表示构建版本和作者信息。
Github() 方法返回一个字符串，表示项目的 GitHub 地址。
--------------------------------------------*/

public class VersionNumber
{
    public String VersionNumber()
    {
        return "Build:0.1.230601 by beibeikun ";
    }

    public String Github()
    {
        return "https://github.com/beibeikun/IMG_ManagementSystem";
    }
}
