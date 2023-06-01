package UIPAGE;

import Module.File.CopyFiles;
import Module.File.FilenameCheck;
import Module.File.RenameFiles;
import Module.File.Selectfilepath;
import Module.IdentifySystem;
import OLD.algorithm.VersionNumber;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class Mainpage {
    private JPanel panel1;
    private JTabbedPane tabbedPane;
    private JButton CheckButton;
    private JButton RenamestartButton;
    private JCheckBox CheckBox_3lot;
    private JCheckBox CheckBox_addlot;
    private JCheckBox CheckBox_classification;
    private JCheckBox CheckBox_addfromdatabase;
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
    private JButton 连接到相机图片库Button;
    private JButton 连接到手机图片库Button;
    private JLabel databaseaddress;
    private JLabel databaseusername;
    private JLabel cameradatabasepath;
    private JLabel phonedatabasepath;
    private JLabel githuburlLabel;
    private JLabel testmode;
    private JToolBar Namingformat;
    private JLabel mode;
    private static MenuBar menuBar;

    Selectfilepath getfilepath = new Selectfilepath();
    FilenameCheck checkfile = new FilenameCheck();
    CopyFiles copyfiles = new CopyFiles();
    RenameFiles renamefiles = new RenameFiles();

    static VersionNumber versionnumber = new VersionNumber();//获取版本号
    public Mainpage() {
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.setEnabledAt(4, false);
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
        ii = 0;
        File loaddatabasesettings = new File("databasesettings.bbk");
        if (loaddatabasesettings.exists())
        {
            try
            {
                br = new BufferedReader(new FileReader("databasesettings.bbk"));
                String filepath = "";
                while ((line = br.readLine()) != null)
                {
                    if (ii == 0)
                    {
                        filepath = line;
                        databaseaddress.setText(filepath);
                    }
                    else if (ii == 1)
                    {
                        filepath = line;
                        databaseusername.setText(filepath);
                    }
                    else if (ii == 3)
                    {
                        filepath = line;
                        cameradatabasepath.setText(filepath);
                    }
                    else if (ii == 4)
                    {
                        filepath = line;
                        phonedatabasepath.setText(filepath);
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

        versionLabel.setText(versionnumber.VersionNumber());//显示为当前版本号
        githuburlLabel.setText("<html><u>"+versionnumber.Github()+"</u></html>");

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
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        BufferedReader br;
                        String line;
                        int ii = 0;
                        File databasesettings = new File("databasesettings.bbk");
                        if (databasesettings.exists())
                        {
                            try
                            {
                                br = new BufferedReader(new FileReader("databasesettings.bbk"));
                                String filepath = "";
                                while ((line = br.readLine()) != null)
                                {
                                    if (ii == 0)
                                    {
                                        filepath = line;
                                        databaseaddress.setText(filepath);
                                    }
                                    else if (ii == 1)
                                    {
                                        filepath = line;
                                        databaseusername.setText(filepath);
                                    }
                                    ii++;
                                }
                            }
                            catch (IOException ea)
                            {
                                ea.printStackTrace();
                            }
                        }
                        else
                        {
                            System.out.println("不存在");
                        }
                    }
                });
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
        CheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = checkfile.namecheck(renamecsvpath.getText(),firstpath.getText(),lastpath.getText());
                if (i==1)
                {
                    JOptionPane.showMessageDialog(null,"检查通过");
                    RenamestartButton.setEnabled(true);
                }
                else if (i==2)
                {
                    JOptionPane.showMessageDialog(null,"未选取路径","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (i==3)
                {
                    JOptionPane.showMessageDialog(null,"路径名存在中文字符","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (i==41)
                {
                    JOptionPane.showMessageDialog(null,"CSV文件不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (i==42)
                {
                    JOptionPane.showMessageDialog(null,"源文件夹路径不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (i==43)
                {
                    JOptionPane.showMessageDialog(null,"目标文件夹不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (i==5)
                {
                    JOptionPane.showMessageDialog(null,"目标文件夹不为空","文件夹错误",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        githuburlLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                githuburlLabel.setForeground(Color.decode("#bbbbbb").darker());
                githuburlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                githuburlLabel.setForeground(Color.decode("#bbbbbb"));
                githuburlLabel.setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(versionnumber.Github()));
                } catch (Exception ea) {
                    ea.printStackTrace();
                }
            }
        });
        versionLabel.addMouseListener(new MouseAdapter() {
            int clickCount = 0;
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                if (clickCount == 5) {
                    int n = JOptionPane.showConfirmDialog(null,"是否进入开发调试模式","",JOptionPane.YES_NO_OPTION);
                    if (n == 0)
                    {
                        tabbedPane.setEnabledAt(1, true);
                        tabbedPane.setEnabledAt(2, true);
                        tabbedPane.setEnabledAt(3, true);
                        tabbedPane.setEnabledAt(4, true);
                        CheckBox_3lot.setEnabled(true);
                        CheckBox_addlot.setEnabled(true);
                        CheckBox_addfromdatabase.setEnabled(true);
                        CheckBox_classification.setEnabled(true);
                        mode.setText("当前正在开发调试模式");
                    }
                    clickCount = 0; // 重置计数器
                }
            }
        });
        RenamestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyfiles.copyfile(renamecsvpath.getText(),firstpath.getText(),lastpath.getText());
                renamefiles.renamefile(renamecsvpath.getText(),lastpath.getText(),0,0);
                JOptionPane.showMessageDialog(null,"重命名完成");
            }
        });
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("MainpageUI");
        frame.setContentPane(new Mainpage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IdentifySystem systemtype = new IdentifySystem();
        int UIwidth=0,UIheight=0;
        if (systemtype.identifysystem_int()==1) //MAC系统下窗口大小
        {
            UIwidth=600;
            UIheight=450;
        }
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
