package Module;

/*--------------------------------------------
用于识别当前系统类型并返回对应的系统类型或分隔符。

代码中包含两个方法：identifysystem_int和identifysystem_String。

identifysystem_int方法返回一个整数值，用于表示当前系统类型。根据不同的操作系统，返回以下数值：

如果是Mac OS，返回1。
如果是Windows，返回2。
如果是Unix或Linux，返回3。
identifysystem_String方法返回一个字符串，表示当前系统的分隔符。根据不同的操作系统，返回以下字符串：

如果是Mac OS，返回"/"。
如果是Windows，返回"\"。
如果是Unix或Linux，返回"/"。
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
