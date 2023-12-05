package GUI;

import Module.CheckOperations.SystemChecker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

import static Module.Others.SystemPrintOut.systemPrintOut;

public class START_WITH_TERMINAL {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemChecker system = new SystemChecker();//获取系统类型
        String firstpath = null,lastpath = null,renamecsvpath = null,cameradatabasepath = null,phonedatabasepath = null;
        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream("properties" + system.identifySystem_String() + "settings.properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            settingsproperties.load(reader);
            firstpath=settingsproperties.getProperty("firstpath");
            systemPrintOut("源文件夹路径:"+firstpath,1,0);
            lastpath=settingsproperties.getProperty("lastpath");
            systemPrintOut("目标文件夹路径:"+lastpath,1,0);
            renamecsvpath=settingsproperties.getProperty("renamecsvpath");
            systemPrintOut("csv文件路径:"+renamecsvpath,1,0);
            cameradatabasepath=settingsproperties.getProperty("cameradatabasepath");
            systemPrintOut("相机图片库路径:"+cameradatabasepath,1,0);
            phonedatabasepath=settingsproperties.getProperty("phonedatabasepath");
            systemPrintOut("手机图片库路径:"+phonedatabasepath,1,0);
            systemPrintOut("成功读取",1,0);
            // 读取属性值...
        } catch (IOException e) {
            systemPrintOut("读取失败，不存在设置信息",2,0);
            e.printStackTrace();
        }
        menu();
        while (true) {
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("无效的输入，请重新输入");
                continue;
            }

            switch (choice) {
                case 11:
                    input = scanner.nextLine();
                    firstpath = input;
                    System.out.println(ANSI_CYAN+ "源文件夹设置为:"+firstpath+ ANSI_RESET);
                    menu();
                    break;
                case 12:
                    input = scanner.nextLine();
                    lastpath = input;
                    System.out.println(ANSI_CYAN+ "目标文件夹设置为:"+lastpath+ ANSI_RESET);
                    menu();
                    break;
                case 13:
                    input = scanner.nextLine();
                    renamecsvpath = input;
                    System.out.println(ANSI_CYAN+ "csv文件设置为:"+renamecsvpath+ ANSI_RESET);
                    menu();
                    break;
                case 14:
                    input = scanner.nextLine();
                    cameradatabasepath = input;
                    System.out.println(ANSI_CYAN+ "相机图片库设置为:"+cameradatabasepath+ ANSI_RESET);
                    menu();
                    break;
                case 15:
                    input = scanner.nextLine();
                    phonedatabasepath = input;
                    System.out.println(ANSI_CYAN+ "手机图片库设置为:"+phonedatabasepath+ ANSI_RESET);
                    menu();
                    break;
                case 21:
                    System.out.println("21");
                    menu();
                    break;
                case 22:
                    System.out.println("22");
                    menu();
                    break;
                case 23:
                    System.out.println(firstpath);
                    menu();
                    break;
                default:
                    System.out.println("无效的操作，请重新输入");
                    menu();
                    break;
            }
        }
    }
    private static void menu() {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW+ "菜单"+ ANSI_RESET);
        System.out.println(ANSI_YELLOW+ "11.输入源文件夹路径 12.输入目标文件夹路径 13.输入csv文件路径 14.输入相机图片库路径 15.输入手机图片库路径"+ ANSI_RESET);
        System.out.println(ANSI_YELLOW+ "21.检查文件正确性 22.上传至相机图片库 23.上传至手机图片库"+ ANSI_RESET);
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }
}
