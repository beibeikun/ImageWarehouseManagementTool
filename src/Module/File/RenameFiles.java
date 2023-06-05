package Module.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件重命名工具类，用于根据给定的Excel文件路径和图片路径，将图片文件按照Excel中的映射关系进行重命名。
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
    public void renameFiles(String excelPath, String imagePath, int digitCheck, int prefixCheck) {
        String[][] fileNameList = new String[2][10000]; // 存放对应的JB号-Lot号
        String[] failedFiles = new String[10000]; // 存放转换失败的文件名
        int failedNum = 0; // 转换失败的文件数量计数器

        /*-------读取csv文件储存到fileNameList-------*/
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        int ii = 0;
        try {
            br = new BufferedReader(new FileReader(excelPath));
            while ((line = br.readLine()) != null) {

                // 使用逗号作为分隔符
                String[] country = line.split(cvsSplitBy);
                fileNameList[0][ii] = country[0];
                fileNameList[1][ii] = country[1];
                ii++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*-------读取csv文件储存到fileNameList-------*/

        File imageFolder = new File(imagePath);
        File[] imageList = imageFolder.listFiles();
        String newName;
        String oldName;
        File dest = null;
        System.out.println("\n\n" + "-------------------------Start to rename-------------------------");
        if (imageFolder.exists() && imageFolder.isDirectory()) {
            for (int i = 0; i < imageList.length; i++) {
                // 获取文件名存入name中
                String name = imageList[i].getName();
                String frontName = name.substring(0, 10); // 格式为“JBOOOO-OOO”
                String behindName = name.substring(10); // 格式为" (00).OOO"，注意这里前面有个空格
                int x = 0;
                while (x < ii) {
                    dest = null;
                    if (fileNameList[0][x].equals(frontName)) {
                        if (digitCheck == 1) {
                            int num = Integer.parseInt(fileNameList[1][x]);
                            if (num / 10 == 0) {
                                newName = "00" + fileNameList[1][x] + behindName;
                            } else if (num / 10 < 10) {
                                newName = "0" + fileNameList[1][x] + behindName;
                            } else {
                                newName = fileNameList[1][x] + behindName;
                            }
                        } else {
                            newName = fileNameList[1][x] + behindName;
                        }

                        if (prefixCheck == 1) { // 判断是否添加前缀
                            newName = "Lot" + newName;
                        }
                        dest = new File(imagePath + "/" + newName);
                        oldName = imageList[i].getName();
                        imageList[i].renameTo(dest);
                        System.out.println("succeeded: " + oldName + " --> " + newName);
                        break;
                    }
                    x++;
                }
                if (dest == null) {
                    System.out.println("failed: " + name);
                    failedFiles[failedNum] = name;
                    failedNum++;
                }
            }
        }
        System.out.println("-------------------------Rename succeeded-------------------------");
        /*
        int x = 0;
        while (x < ii) {
            if (fileNameList[1][x].equals("")) {
                System.out.println("No found: " + fileNameList[0][x]);
            }
            x++;
        }
        */
    }
}
