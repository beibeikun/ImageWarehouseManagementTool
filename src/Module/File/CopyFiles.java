package Module.File;

import Module.IdentifySystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件拷贝和删除操作类。
 */
public class CopyFiles {
    /**
     * 批量拷贝文件。
     *
     * @param excelPath   源文件夹路径
     * @param imgPath     图片文件夹路径
     * @param copyPath    拷贝文件夹路径
     */
    public void copyFiles(String excelPath, String imgPath, String copyPath) {
        IdentifySystem systemType = new IdentifySystem();
        File file = new File(imgPath);
        File[] imgList = file.listFiles();
        if (file.exists() && file.isDirectory()) {
            System.out.println("\n\n" + "-------------------------Start to copy-------------------------");
            for (int i = 0; i < imgList.length; i++) {
                copy(imgPath + systemType.identifySystem_String() + imgList[i].getName(), copyPath);
                System.out.println("succeeded: " + imgList[i].getName());
            }
            System.out.println("-------------------------Copy succeeded-------------------------");
        }
    }
    /**
     * 将源文件拷贝到目标文件夹。
     *
     * @param srcPathStr  源文件路径
     * @param desPathStr  目标文件夹路径
     */
    static void copy(String srcPathStr, String desPathStr) {
        IdentifySystem systemType = new IdentifySystem();

        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf(systemType.identifySystem_String()) + 1); // 目标文件地址
        desPathStr = desPathStr + File.separator + newFileName; // 源文件地址
        try {
            FileInputStream fis = new FileInputStream(srcPathStr); // 创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); // 创建输出流对象
            byte datas[] = new byte[1024 * 8]; // 创建搬运工具
            int len = 0; // 创建长度
            while ((len = fis.read(datas)) != -1) { // 循环读取数据
                fos.write(datas, 0, len);
            }
            fos.close(); // 释放资源
            fis.close(); // 释放资源
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹中以"JB"开头的文件。
     *
     * @param imgPath  图片文件夹路径
     */
    public void deleteFiles(String imgPath) {
        File file = new File(imgPath);
        File[] imgList = file.listFiles();
        System.out.println("\n\n" + "-------------------------Start to delete failed files-------------------------");
        if (file.exists() && file.isDirectory()) {
            for (int i = 0; i < imgList.length; i++) {
                if (imgList[i].getName().startsWith("JB")) {
                    String name = imgList[i].getName();
                    imgList[i].delete();
                    System.out.println("succeeded: " + name);
                }
            }
        }
        System.out.println("-------------------------Delete failed files succeeded-------------------------");
    }
}
