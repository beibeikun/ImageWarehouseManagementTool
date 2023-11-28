package Module.File;
import Module.Others.IdentifySystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.*;

import static Module.File.FileOperations.copyFile;
/*------------------------------------------------------------------
已重构，该代码当前停用
------------------------------------------------------------------*/
/**
 * 文件重命名工具类，根据给定的Excel文件路径和图片路径，将图片文件按照Excel中的映射关系进行重命名。
 */
public class RenameFiles {
    /**
     * 将图片文件按照Excel中的映射关系进行重命名。
     *
     * @param excelPath   Excel文件的路径
     * @param imagePath   图片文件夹的路径
     * @param digitCheck  数字补零检查标志，1表示补零，0表示不补零
     * @param prefixCheck 前缀添加检查标志，1表示添加前缀，0表示不添加前缀
     */
    public void renameFiles(String excelPath, String imagePath, int digitCheck, int prefixCheck, int findMode, String databasePath) {
        IdentifySystem system = new IdentifySystem();
        String[][] fileNameList = new String[2][10000]; // 存放对应的JB号-Lot号
        String[] failedFiles = new String[10000]; // 存放转换失败的文件名
        int failedNum = 0; // 转换失败的文件数量计数器
        int index = 0;
        /* 读取Excel文件，将映射关系存储到fileNameList数组中 */
        try (BufferedReader br = new BufferedReader(new FileReader(excelPath))) {
            String line;
            String cvsSplitBy = ",";

            while ((line = br.readLine()) != null) {
                // 使用逗号作为分隔符
                String[] country = line.split(cvsSplitBy);
                fileNameList[0][index] = country[0];
                fileNameList[1][index] = country[1];
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File imageFolder = new File(imagePath);
        File[] imageList = imageFolder.listFiles();

        System.out.println("\n-------------------------Start renaming-------------------------");
        if (imageFolder.exists() && imageFolder.isDirectory()) {

            for (File image : imageList) {
                if (image.isHidden()) {
                    continue; // 忽略隐藏文件
                }

                // 获取文件名
                String name = image.getName();
                String frontName = name.substring(0, 10); // 格式为“JBOOOO-OOO”
                String behindName = name.substring(10); // 格式为" (00).OOO"，注意这里前面有个空格

                // 根据映射关系进行重命名
                boolean renamed = false;
                for (int i = 0; i < index; i++) {
                    //copyFile(databasePath + system.identifySystem_String() + fileNameList[0][i].substring(0, 6) + system.identifySystem_String() +  fileNameList[0][i] + ".zip",imagePath);
                    if (fileNameList[0][i].equals(frontName)) {
                        String newName = fileNameList[1][i] + behindName;

                        if (digitCheck == 1) {
                            int num = Integer.parseInt(fileNameList[1][i]);
                            if (num < 10) {
                                newName = "00" + newName;
                            } else if (num < 100) {
                                newName = "0" + newName;
                            }
                        }

                        if (prefixCheck == 1) {
                            newName = "Lot" + newName; // 添加前缀
                        }

                        File dest = new File(imagePath + File.separator + newName);
                        if (image.renameTo(dest)) {
                            System.out.println("Renamed: " + name + " --> " + newName);
                            renamed = true;
                            break;
                        }
                    }
                }

                if (!renamed) {
                    System.out.println("Failed to rename: " + name);
                    failedFiles[failedNum] = name;
                    failedNum++;
                }
            }
        } else {
            System.out.println("Invalid image folder path");
        }


        System.out.println("-------------------------Rename completed-------------------------");
    }
}
