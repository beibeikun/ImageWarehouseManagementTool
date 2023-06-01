package UIPAGE;

import Module.File.Selectfilepath;
import OLD.algorithm.VersionNumber;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Mainpage {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton CheckButton;
    private JButton RenamestartButton;
    private JCheckBox 使用三位lot号CheckBox;
    private JCheckBox 添加Lot前缀CheckBox;
    private JCheckBox 完成后按文件夹分类CheckBox;
    private JCheckBox 尝试从库中提取缺少的拍品CheckBox;
    private JButton SelectButton_lastpath;
    private JButton SelectButton_firstpath;
    private JCheckBox 替换已存在的图片CheckBox;
    private JButton 开始添加Button;
    private JButton 开始移除Button;
    private JTextField textField1;
    private JButton 查询Button;
    private JLabel versionLabel;
    private JLabel firstpath;
    private JLabel lastpath;
    private JLabel renamecsvpath;
    private JToolBar JT_firstpath;
    private JToolBar JT_renamecsvpath;
    private JToolBar JT_lastpath;
    private JToolBar Othersettings;
    private JButton SelectButton_renamecsvpath;
    private JButton connecttodatabase;
    private JButton 连接到图片库Button;
    private static MenuBar menuBar;

    Selectfilepath getfilepath = new Selectfilepath();

    static VersionNumber versionnumber = new VersionNumber();//获取版本号
    public Mainpage() {
        BufferedReader br;
        String line;
        int ii = 0;
        File loadsettings = new File("historysettings.bbk");
        if (loadsettings.exists())
        {
            try
            {
                br = new BufferedReader(new FileReader("historysettings.bbk"));
                String filepath = "";
                while ((line = br.readLine()) != null)
                {
                    if (ii == 0)
                    {
                        filepath = line;
                        firstpath.setText(filepath);
                        System.out.println("--Directory selected-- " + filepath); //此处为测试输出点
                    }
                    else if (ii == 1)
                    {
                        filepath = line;
                        lastpath.setText(filepath);
                        System.out.println("--Directory selected-- " + filepath); //此处为测试输出点
                    }
                    else if (ii == 2)
                    {
                        filepath = line;
                        renamecsvpath.setText(filepath);
                        System.out.println("--Directory selected-- " + filepath); //此处为测试输出点
                    }

                    ii++;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("存在");
        }
        else
        {
            System.out.println("不存在");
        }

        versionLabel.setText(versionnumber.VersionNumber()+"   "+versionnumber.Github());//显示为当前版本号
        CheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        connecttodatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Connecttodatabase");
                frame.setContentPane(new Connecttodatabase().panel1);
                int UIwidth=300,UIheight=200;
                //设置大小
                frame.setSize(UIwidth,UIheight);
                int Monitor_width = Toolkit.getDefaultToolkit().getScreenSize().width;
                int Monitor_height = Toolkit.getDefaultToolkit().getScreenSize().height;
                //获取屏幕大小
                frame.setLocation((Monitor_width - UIwidth) / 2, (Monitor_height - UIheight) / 2);
                //使窗体显示在屏幕中央
                frame.setVisible(true);
                frame.setTitle("连接到数据库");
                frame.setResizable(false); //禁止改变大小
            }
        });
        SelectButton_firstpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sfirstpath = getfilepath.Selectfilepath(2);
                firstpath.setText(Sfirstpath);
            }
        });
        SelectButton_lastpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Slastpath = getfilepath.Selectfilepath(2);
                lastpath.setText(Slastpath);
            }
        });
        SelectButton_renamecsvpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Srenamecsvpath = getfilepath.Selectfilepath(1);
                renamecsvpath.setText(Srenamecsvpath);
            }
        });
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("MainpageUI");
        frame.setContentPane(new Mainpage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int UIwidth=600,UIheight=500;
        //设置大小
        frame.setSize(UIwidth,UIheight);
        int Monitor_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int Monitor_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //获取屏幕大小
        frame.setLocation((Monitor_width - UIwidth) / 2, (Monitor_height - UIheight) / 2);
        //使窗体显示在屏幕中央
        frame.setVisible(true);
        frame.setTitle("图片文件管理仓库");
    }

}
