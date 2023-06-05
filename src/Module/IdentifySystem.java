package Module;

/**
 * 系统识别工具类，用于识别当前操作系统类型。
 */
public class IdentifySystem {
    /**
     * 识别当前操作系统类型，并返回相应的整数值。
     *
     * @return 操作系统类型的整数值
     */
    public int identifySystem_int() {
        String OSname = System.getProperty("os.name");
        if (OSname.startsWith("Mac OS")) {
            return 1; // Mac OS
        } else if (OSname.startsWith("Windows")) {
            return 2; // Windows
        } else {
            return 3; // Unix or Linux
        }
    }

    /**
     * 识别当前操作系统类型，并返回相应的路径分隔符字符串。
     *
     * @return 路径分隔符字符串
     */
    public String identifySystem_String() {
        String OSname = System.getProperty("os.name");
        if (OSname.startsWith("Mac OS")) {
            return "/"; // Mac OS
        } else if (OSname.startsWith("Windows")) {
            return "\\"; // Windows
        } else {
            return "/"; // Unix or Linux
        }
    }
}
