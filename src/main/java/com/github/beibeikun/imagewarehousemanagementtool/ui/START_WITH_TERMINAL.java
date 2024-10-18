/*--------------------------------------------------------------------------------


缺少维护，不保证完全可用


--------------------------------------------------------------------------------*/
package com.github.beibeikun.imagewarehousemanagementtool.ui;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.CompleteNameChangeProcess;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.WriteToProperties;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.ExtractMainImage;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.TakeMainFromDatabase;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetPropertiesPath;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.CompressImgToZipAndUpload.compressImgToZipAndUpload;
import static com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.CompressImagesInBatches.compressImagesInBatches;

public class START_WITH_TERMINAL
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws ImageProcessingException, IOException, MetadataException
    {
        Scanner scanner = new Scanner(System.in);
        SystemChecker system = new SystemChecker();//获取系统类型
        String firstpath = null, lastpath = null, renamecsvpath = null, cameradatabasepath = null, phonedatabasepath = null;
        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(GetPropertiesPath.settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            settingsproperties.load(reader);
            firstpath = settingsproperties.getProperty("firstpath");
            SystemPrintOut.systemPrintOut("源文件夹路径:" + firstpath, 1, 0);
            lastpath = settingsproperties.getProperty("lastpath");
            SystemPrintOut.systemPrintOut("目标文件夹路径:" + lastpath, 1, 0);
            renamecsvpath = settingsproperties.getProperty("renamecsvpath");
            SystemPrintOut.systemPrintOut("csv文件路径:" + renamecsvpath, 1, 0);
            cameradatabasepath = settingsproperties.getProperty("cameradatabasepath");
            SystemPrintOut.systemPrintOut("相机图片库路径:" + cameradatabasepath, 1, 0);
            phonedatabasepath = settingsproperties.getProperty("phonedatabasepath");
            SystemPrintOut.systemPrintOut("手机图片库路径:" + phonedatabasepath, 1, 0);
            SystemPrintOut.systemPrintOut("成功读取", 1, 0);
            // 读取属性值...
        }
        catch (IOException e)
        {
            SystemPrintOut.systemPrintOut("读取失败，不存在设置信息", 2, 0);
            e.printStackTrace();
        }
        System.out.println(ANSI_RED + "注意！终端版不具备检查功能！请谨慎操作！" + ANSI_RESET);
        System.out.println(ANSI_RED + "注意！终端版不具备检查功能！请谨慎操作！" + ANSI_RESET);
        System.out.println(ANSI_RED + "注意！终端版不具备检查功能！请谨慎操作！" + ANSI_RESET);
        menu();
        while (true)
        {
            String input = scanner.nextLine();
            int choice;
            try
            {
                choice = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("无效的输入，请重新输入");
                continue;
            }
            String[][] filenamelist = new String[2][10];
            int zipornot;
            String[] prefixes;

            switch (choice)
            {
                //输入源文件夹路径
                case 11:
                    input = scanner.nextLine();
                    firstpath = input;

                    filenamelist[0][0] = "firstpath";
                    filenamelist[1][0] = firstpath;
                    WriteToProperties.writeToProperties("settings", filenamelist);
                    System.out.println(ANSI_CYAN + "源文件夹设置为:" + firstpath + ANSI_RESET);
                    menu();
                    break;
                //输入目标文件夹路径
                case 12:
                    input = scanner.nextLine();
                    lastpath = input;

                    filenamelist[0][0] = "lastpath";
                    filenamelist[1][0] = lastpath;
                    WriteToProperties.writeToProperties("settings", filenamelist);
                    System.out.println(ANSI_CYAN + "目标文件夹设置为:" + lastpath + ANSI_RESET);
                    menu();
                    break;
                case 13:
                    input = scanner.nextLine();
                    renamecsvpath = input;

                    filenamelist[0][0] = "renamecsvpath";
                    filenamelist[1][0] = renamecsvpath;
                    WriteToProperties.writeToProperties("settings", filenamelist);
                    System.out.println(ANSI_CYAN + "csv文件设置为:" + renamecsvpath + ANSI_RESET);
                    menu();
                    break;
                case 14:
                    input = scanner.nextLine();
                    cameradatabasepath = input;

                    filenamelist[0][0] = "cameradatabasepath";
                    filenamelist[1][0] = cameradatabasepath;
                    WriteToProperties.writeToProperties("settings", filenamelist);
                    System.out.println(ANSI_CYAN + "相机图片库设置为:" + cameradatabasepath + ANSI_RESET);
                    menu();
                    break;
                case 15:
                    input = scanner.nextLine();
                    phonedatabasepath = input;

                    filenamelist[0][0] = "phonedatabasepath";
                    filenamelist[1][0] = phonedatabasepath;
                    WriteToProperties.writeToProperties("settings", filenamelist);
                    System.out.println(ANSI_CYAN + "手机图片库设置为:" + phonedatabasepath + ANSI_RESET);
                    menu();
                    break;
                case 21:
                    System.out.println(ANSI_BLUE + "请输入图片库类型，0为不使用图片库，1为相机图片库，2为手机图片库" + ANSI_RESET);
                    input = scanner.nextLine();
                    int choose = Integer.parseInt(input);
                    String databasepath = null;
                    int database = 0;
                    if (choose == 1)
                    {
                        databasepath = cameradatabasepath;
                    }
                    else if (choose == 2)
                    {
                        databasepath = phonedatabasepath;
                    }

                    System.out.println(ANSI_BLUE + "请输入图片压缩尺寸，0为不压缩" + ANSI_RESET);
                    input = scanner.nextLine();
                    int imgsize = Integer.parseInt(input);
                    System.out.println(ANSI_BLUE + "是否需要按文件夹分类？ 1.是 2.否" + ANSI_RESET);
                    input = scanner.nextLine();
                    boolean prefix;
                    prefix = Integer.parseInt(input) == 1;
                    CompleteNameChangeProcess completeNameChangeProcess = new CompleteNameChangeProcess();
                    completeNameChangeProcess.completeNameChangeProcess(databasepath, firstpath, lastpath, renamecsvpath, database, imgsize, true, prefix, 0);
                    menu();
                    break;
                case 22:
                    TakeMainFromDatabase.takeMainFromDatabase(renamecsvpath, cameradatabasepath, lastpath);
                    menu();
                    break;
                case 23:

                    menu();
                    break;
                case 31:
                    System.out.println(ANSI_BLUE + "是否压缩图片？ 1.是 2.否" + ANSI_RESET);
                    input = scanner.nextLine();
                    zipornot = Integer.parseInt(input);
                    if (zipornot == 1)
                    {
                        compressImagesInBatches(firstpath, 5000, true);
                    }
                    compressImgToZipAndUpload(firstpath, cameradatabasepath, 1, 0, false, false);
                    menu();
                    break;
                case 32:
                    System.out.println(ANSI_BLUE + "是否压缩图片？ 1.是 2.否" + ANSI_RESET);
                    input = scanner.nextLine();
                    zipornot = Integer.parseInt(input);
                    if (zipornot == 1)
                    {
                        compressImagesInBatches(firstpath, 5000, true);
                    }
                    compressImgToZipAndUpload(firstpath, phonedatabasepath, 0, 0, false, false);
                    menu();
                    break;
                case 4:
                    stopProgram();
                    break;
                default:
                    System.out.println("无效的操作，请重新输入");
                    menu();
                    break;
            }
        }
    }

    private static void menu()
    {
        System.out.println(ANSI_YELLOW + "————————————————————————————————————————————————————————————————————————————————————————————————————");
        System.out.println("| 菜单");
        System.out.println("| 11.输入源文件夹路径 12.输入目标文件夹路径 13.输入csv文件路径 14.输入相机图片库路径 15.输入手机图片库路径");
        System.out.println("| 21.执行重命名 22.从相机图片库中根据csv提取主图 23.从源文件夹中中提取主图");
        System.out.println("| 31.上传至相机图片库 32.上传至手机图片库");
        System.out.println("| 4.结束程序");
        System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————" + ANSI_RESET);
    }

    private static void stopProgram()
    {
        System.exit(0);
    }
}
