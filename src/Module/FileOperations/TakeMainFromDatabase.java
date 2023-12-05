package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 从数据库中获取文件的类
 */
public class TakeMainFromDatabase {

    /**
     * 从数据库中获取文件，并在特定条件下将文件复制到指定文件夹中
     *
     * @param csvpath      包含映射关系的 CSV 文件路径
     * @param databasepath 数据库文件的根路径
     * @param folderpath   目标文件夹路径
     */
    public static void takeMainFromDatabase(String csvpath, String databasepath, String folderpath)
    {
        systemPrintOut("Start to take main img from database",3,0);
        SystemChecker system = new SystemChecker();
        String[] fileNameList = new String[10000]; // 存放对应的JB号-Lot号
        int index = 0;
        /* 读取Excel文件，将映射关系存储到fileNameList数组中 */
        try (BufferedReader br = new BufferedReader(new FileReader(csvpath))) {
            String line;
            String cvsSplitBy = ",";

            while ((line = br.readLine()) != null) {
                // 使用逗号作为分隔符
                String[] country = line.split(cvsSplitBy);
                fileNameList[index] = country[0];
                index++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        for (int x = 1; x < index; x++) {
            File fileCheck = new File(databasepath + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + fileNameList[x] + ".JPG");
            if (fileCheck.exists()) {
                copyFile(databasepath + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + fileNameList[x] + ".JPG", folderpath);
                systemPrintOut("Copy:"+fileNameList[x],1,0);
            }
        }
        systemPrintOut(null,0,0);
    }
}
