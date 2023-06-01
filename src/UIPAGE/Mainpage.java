package UIPAGE;

import algorithm.VersionNumber;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JCheckBox checkBox1;
    private JComboBox comboBox_System;
    private JLabel versionLabel;
    private JLabel firstpath;
    private JLabel lastpath;
    private JLabel renamecsvpath;
    private JLabel JLabel_selectsystem;
    private JToolBar JT_firstpath;
    private JToolBar JT_renamecsvpath;
    private JToolBar JT_lastpath;
    private JToolBar Othersettings;
    private JButton SelectButton_renamecsvpath;

    static VersionNumber versionnumber = new VersionNumber();//获取版本号
    public Mainpage() {
        versionLabel.setText(versionnumber.VersionNumber());//显示为当前版本号
        CheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        String OSname = System.getProperty("os.name");
        System.out.println(OSname);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("配置");
        menu.add("配置数据库");
        menu.add("配置图片库");
        menuBar.add(menu);
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
        frame.setMenuBar(menuBar);
    }

}
