package Module.Others;

import Module.CheckOperations.SystemChecker;

public class GetSettingsPath {
    public static String settingspath()
    {
        SystemChecker system = new SystemChecker();//获取系统类型
        return "src" + system.identifySystem_String() + "properties" + system.identifySystem_String() + "settings.properties";
    }
}
