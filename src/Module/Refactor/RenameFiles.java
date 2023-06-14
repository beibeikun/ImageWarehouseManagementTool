package Module.Refactor;

import java.io.File;
import java.util.Arrays;

import static Module.Refactor.FileChecker.isSystemOrHiddenFile;
import static Module.Refactor.ReadCsvFile.csvToArray;

public class RenameFiles {
    /**
     * 将图片文件按照Excel中的映射关系进行重命名。
     *
     * @param excelPath   Excel文件的路径
     * @param imagePath   图片文件夹的路径
     * @param digitCheck  数字补零检查标志，1表示补零，0表示不补零
     * @param prefixCheck 前缀添加检查标志，1表示添加前缀，0表示不添加前缀
     */
    public void renameFiles(String excelPath, String imagePath, int digitCheck, int prefixCheck) {
        String[][] JB_LOTArray = csvToArray(excelPath);
        String[] JBArray = JB_LOTArray[0];
        String[] LOTArray = JB_LOTArray[1];

        String[] failedFiles = null; // 存放转换失败的文件名
        int failedNum = 0; // 转换失败的文件数量计数器

        File imageFolder = new File(imagePath);
        File[] imageList = imageFolder.listFiles();
        if (imageFolder.exists() && imageFolder.isDirectory()) {
            for (File image : imageList) {
                if (!isSystemOrHiddenFile(image)) {
                    continue;
                }
                else {
                    // 获取文件名
                    String name = image.getName();
                    String frontName = name.substring(0, 10); // 格式为“JBOOOO-OOO”
                    String behindName = name.substring(10); // 格式为" (00).OOO"，注意这里前面有个空格

                    // 根据映射关系进行重命名
                    boolean renamed = false;
                    for (int i = 0; i <= JBArray.length; i++) {
                        if (JBArray[i].equals(frontName)) {
                            String newName = LOTArray[i] + behindName;
                            if (digitCheck == 1) {
                                int num = Integer.parseInt(LOTArray[i]);
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
            }
        }
    }
}
