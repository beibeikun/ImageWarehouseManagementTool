package Module.Refactor;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static Module.Refactor.FileChecker.isSystemOrHiddenFile;
import static Module.Refactor.ReadCsvFile.csvToArray;

public class RenameFiles {
    public static void main(String[] args) {
        renameFiles("/Users/bbk/photographs/test2.csv","/Users/bbk/photographs/test3",1,1);
    }

    public static void renameFiles(String excelPath, String imagePath, int digitCheck, int prefixCheck) {
        String[][] JB_LOTArray = csvToArray(excelPath);
        String[] JBArray = JB_LOTArray[0];
        String[] LOTArray = JB_LOTArray[1];

        File imageFolder = new File(imagePath);
        File[] imageList = imageFolder.listFiles();
        int failedNum = 0;

        if (imageFolder.exists() && imageFolder.isDirectory()) {
            for (File image : imageList) {
                if (isSystemOrHiddenFile(image)) {
                    continue;
                } else {
                    // 获取文件名
                    String name = image.getName();

                    // 文件名检查
                    if (name.length() < 10) {
                        // 文件名太短，不符合预期格式
                        System.out.println("Failed to rename: " + name);
                        continue;
                    }

                    String frontName = name.substring(0, 10); // 格式为“JBOOOO-OOO”
                    String behindName = name.substring(10); // 格式为" (00).OOO"，注意这里前面有个空格

                    // 根据映射关系进行重命名
                    boolean renamed = false;
                    for (int i = 0; i < JBArray.length; i++) {
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
                        // 文件名检查，确保索引不越界
                    }
                }
            }
        }
    }
}
