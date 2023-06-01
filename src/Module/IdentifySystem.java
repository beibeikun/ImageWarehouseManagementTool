package Module;

/*--------------------------------------------
调用时识别并返回对应系统类型或对应系统类型的分隔符
1-MAC OS-“/”
2-WINDOWS-“\”
3-LINUX-“/”
--------------------------------------------*/
public class IdentifySystem {
    public int identifysystem_int()
    {
        String OSname = System.getProperty("os.name");
        String symbol = null;
        if (OSname.startsWith("Mac OS"))
        {
            return 1;
            // Mac OS
        }
        else if (OSname.startsWith("Windows"))
        {
            return 2;
            // windows
        }
        else
        {
            return 3;
            // unix or linux
        }
    }
    public String identifysystem_String()
    {
        String OSname = System.getProperty("os.name");
        String symbol = null;
        if (OSname.startsWith("Mac OS"))
        {
            return "/";
            // Mac OS
        }
        else if (OSname.startsWith("Windows"))
        {
            return "\\";
            // windows
        }
        else
        {
            return "/";
            // unix or linux
        }
    }
}
