package GUI;

import Module.File.*;
import Module.Others.*;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Properties;

import static Module.File.DeleteDirectory.deleteDirectory;
import static Module.File.ExtractMainImage.extractMainImage;
import static Module.File.FileNameCheck.checkFilePath;
import static GUI.ImageUtils.openImage;
import static Module.File.WriteToProperties.writeToProperties;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class Mainpage {
    private JPanel panel1;
    private JTabbedPane tabbedPane;
    private JButton CheckButton;
    private JButton RenamestartButton;
    private JCheckBox CheckBox_digit;
    private JCheckBox CheckBox_prefix;
    private JCheckBox CheckBox_classification;
    private JCheckBox CheckBox_addfromdatabase;
    private JButton SelectButton_lastpath;
    private JButton SelectButton_firstpath;
    private JCheckBox 替换已存在的图片CheckBox;
    private JButton AddtocameradatabaseButton;
    private JButton 开始移除Button;
    private JTextField textField1;
    private JButton SearchButton;
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
    private JButton ConnectButton1;
    private JButton ConnectButton2;
    private JLabel databaseaddress;
    private JLabel databaseusername;
    private JLabel cameradatabasepath;
    private JLabel phonedatabasepath;
    private JLabel githuburlLabel;
    private JLabel testmode;
    private JToolBar Namingformat;
    private JLabel mode;
    private JCheckBox deleteCheckBox;
    private JTextArea textArea1;
    private JScrollPane ScrollPane1;
    private JButton consolebutton;
    private JButton ExtractMainImageButton;
    private JComboBox comboBox1;
    private JTextArea consoleTextArea;
    private static MenuBar menuBar;

    SelectFilePath getfilepath = new SelectFilePath();
    FileNameCheck checkfile = new FileNameCheck();
    FileOperations copyfiles = new FileOperations();
    RenameFiles renamefiles = new RenameFiles();

    static JFrame frame = new JFrame("MainpageUI");
    static VersionNumber versionnumber = new VersionNumber();//获取版本号
    public Mainpage() {
        ScrollPane1.setVisible(false);
        //识别系统语言
        Locale defaultLocale = Locale.getDefault();
        String language = defaultLocale.getLanguage();
        //根据系统语言加载资源文件，目前只有中文
        language = language+".properties";
        Properties properties = new Properties();
        IdentifySystem system = new IdentifySystem();//获取系统类型
        try (FileInputStream fis = new FileInputStream("properties"+system.identifySystem_String()+language);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(reader);
            CheckBox_digit.setText(properties.getProperty("CheckBox_digit"));
            CheckBox_prefix.setText(properties.getProperty("CheckBox_prefix"));
            // 读取属性值...
        } catch (IOException e) {
            e.printStackTrace();
        }


        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream("properties"+system.identifySystem_String()+"settings.properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            settingsproperties.load(reader);
            databaseaddress.setText(settingsproperties.getProperty("databaseaddress"));
            databaseusername.setText(settingsproperties.getProperty("databaseusername"));
            firstpath.setText(settingsproperties.getProperty("firstpath"));
            lastpath.setText(settingsproperties.getProperty("lastpath"));
            renamecsvpath.setText(settingsproperties.getProperty("renamecsvpath"));
            cameradatabasepath.setText(settingsproperties.getProperty("cameradatabasepath"));
            phonedatabasepath.setText(settingsproperties.getProperty("phonedatabasepath"));
            // 读取属性值...
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setOut(new PrintStream(new OutputStream()
        {
            @Override
            public void write(int b)
            {
                textArea1.append(String.valueOf((char) b));
            }
        }));

        versionLabel.setText(versionnumber.getVersionNumber());//显示为当前版本号
        githuburlLabel.setText("<html><u>GitHub Homepage</u></html>");//显示github地址

        connecttodatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnectionForm databaseConnectionForm = new DatabaseConnectionForm(new DataCallback() {
                    public void onDataEntered(String address, String username, String password) {
                        // 在这里处理从第二个窗口返回的数据
                        databaseaddress.setText(address);
                        databaseusername.setText(username);
                    }
                });
                databaseConnectionForm.setVisible(true);
            }

        });
        SelectButton_firstpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sfirstpath = getfilepath.selectFilePath(2);
                firstpath.setText(Sfirstpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "firstpath";
                filenamelist[1][0] = firstpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });
        SelectButton_lastpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Slastpath = getfilepath.selectFilePath(2);
                lastpath.setText(Slastpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "lastpath";
                filenamelist[1][0] = lastpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });
        SelectButton_renamecsvpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Srenamecsvpath = getfilepath.selectFilePath(1);
                renamecsvpath.setText(Srenamecsvpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "renamecsvpath";
                filenamelist[1][0] = renamecsvpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });
        CheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int renamecsvpathcheck = checkFilePath(renamecsvpath.getText(), false);
                int firstpathcheck = checkFilePath(firstpath.getText(), false);
                int lastpathcheck = checkFilePath(lastpath.getText(), true);

                if (renamecsvpathcheck==1 && firstpathcheck==1 && lastpathcheck==1)
                {
                    JOptionPane.showMessageDialog(null,"检查通过");
                    systemPrintOut("Inspection passed",1,1);
                    RenamestartButton.setEnabled(true);
                }
                else if (renamecsvpathcheck==2 || firstpathcheck==2 || lastpathcheck==2)
                {
                    JOptionPane.showMessageDialog(null,"未选取路径","路径错误",JOptionPane.WARNING_MESSAGE);
                    systemPrintOut("No path selected",2,1);
                }
                else if (renamecsvpathcheck==3 || firstpathcheck==3 || lastpathcheck==3)
                {
                    JOptionPane.showMessageDialog(null,"路径名存在中文字符","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (renamecsvpathcheck==4)
                {
                    JOptionPane.showMessageDialog(null,"CSV文件不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (firstpathcheck==4)
                {
                    JOptionPane.showMessageDialog(null,"源文件夹路径不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (lastpathcheck==4)
                {
                    int n = JOptionPane.showConfirmDialog(null, "目标文件夹不存在,是否创建对应文件夹", "标题",JOptionPane.YES_NO_OPTION); //返回值为0或1
                    if (n==0)
                    {
                        String directoryPath = lastpath.getText();
                        File directory = new File(directoryPath);
                        boolean created = directory.mkdirs();
                        JOptionPane.showMessageDialog(null,"文件夹已创建，检查通过");
                        RenamestartButton.setEnabled(true);
                        systemPrintOut("Folder created, inspection passed",1,1);
                    }
                }
                else if (lastpathcheck==5)
                {
                    int n = JOptionPane.showConfirmDialog(null, "目标文件夹非空,是否清空对应文件夹", "标题",JOptionPane.YES_NO_OPTION);
                    if (n==0)
                    {
                        String directoryPath = lastpath.getText();
                        File directory = new File(directoryPath);
                        boolean deleted = deleteDirectory(directory);
                        boolean created = directory.mkdirs();
                        JOptionPane.showMessageDialog(null,"文件夹已清空，检查通过");
                        RenamestartButton.setEnabled(true);
                        systemPrintOut("Folder emptied, inspection passed",1,1);
                    }
                }
            }
        });
        RenamestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                FilePrefixMove filePrefixMove = new FilePrefixMove();
                int digit_check=0,prefix_check=0;
                if (CheckBox_digit.isSelected())
                {
                    digit_check = 1;
                }
                if (CheckBox_prefix.isSelected())
                {
                    prefix_check = 1;
                }
                try {
                    copyfiles.copyFiles(firstpath.getText(),lastpath.getText(),0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                renamefiles.renameFiles(renamecsvpath.getText(),lastpath.getText(),digit_check,prefix_check);
                copyfiles.deleteFiles(lastpath.getText(),"JB");
                if (CheckBox_classification.isSelected())
                {
                    filePrefixMove.filePrefixMove(lastpath.getText()," (");
                }
                Instant instant2 = Instant.now();
                Duration duration = Duration.between(instant1, instant2);
                long diffSeconds = duration.getSeconds();
                JOptionPane.showMessageDialog(null,"任务完成，本次耗时："+diffSeconds+"秒");
                RenamestartButton.setEnabled(false);
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
                    Desktop.getDesktop().browse(new URI(versionnumber.getGithub()));
                } catch (Exception ea) {
                    ea.printStackTrace();
                }
            }
        });
        ConnectButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Scamerapath = getfilepath.selectFilePath(2);
                cameradatabasepath.setText(Scamerapath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0]="cameradatabasepath";
                filenamelist[1][0]=Scamerapath;
                writeToProperties("settings",filenamelist);
            }
        });
        ConnectButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sphonepath = getfilepath.selectFilePath(2);
                phonedatabasepath.setText(Sphonepath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0]="phonedatabasepath";
                filenamelist[1][0]=Sphonepath;
                writeToProperties("settings",filenamelist);
            }
        });
        consolebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (consolebutton.getText().equals(">"))
                {
                    ScrollPane1.setVisible(true);
                    frame.setSize(frame.getWidth()+600, frame.getHeight());
                    consolebutton.setText("<");
                    frame.setLocationRelativeTo(null);
                }
                else
                {
                    ScrollPane1.setVisible(false);
                    frame.setSize(frame.getWidth()-600, frame.getHeight());
                    consolebutton.setText(">");
                    frame.setLocationRelativeTo(null);
                }

            }
        });
        AddtocameradatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "上传任务耗时长，运行期间界面假死为正常现象，请勿终止程序或断开硬盘！", "确认",JOptionPane.YES_NO_OPTION); //返回值为0或1
                if (n==0)
                {
                    int filepathcheck = checkFilePath(firstpath.getText(),false);
                    if (filepathcheck==1)
                    {
                        Instant instant1 = Instant.now();
                        try {
                            String[] prefixes = FileCompression.compressFilesByPrefix(firstpath.getText(), cameradatabasepath.getText());
                            if (deleteCheckBox.isSelected())//完成后删除文件
                            {
                                //TODO 完成后删除源文件夹中的所有内容
                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        Instant instant2 = Instant.now();
                        Duration duration = Duration.between(instant1, instant2);
                        long diffSeconds = duration.getSeconds();
                        JOptionPane.showMessageDialog(null,"任务完成，本次耗时："+diffSeconds+"秒");


                    }
                    else if (filepathcheck == 2)
                    {
                        JOptionPane.showMessageDialog(null,"未选取路径","路径错误",JOptionPane.WARNING_MESSAGE);
                    }
                    else if (filepathcheck == 3)
                    {
                        JOptionPane.showMessageDialog(null,"路径名存在中文字符","路径错误",JOptionPane.WARNING_MESSAGE);
                    }
                    else if (filepathcheck == 4)
                    {
                        JOptionPane.showMessageDialog(null,"源文件夹路径不存在","路径错误",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = cameradatabasepath.getText() +system.identifySystem_String()+"thumbnail"+system.identifySystem_String()+ textField1.getText()+".JPG";
                System.out.println(path);
                openImage(path);
            }
        });
        ExtractMainImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                extractMainImage(firstpath.getText(),lastpath.getText());
                Instant instant2 = Instant.now();
                Duration duration = Duration.between(instant1, instant2);
                long diffSeconds = duration.getSeconds();
                JOptionPane.showMessageDialog(null,"任务完成，本次耗时："+diffSeconds+"秒");

            }
        });
    }
    public interface DataCallback {
        void onDataEntered(String address, String username, String password);
    }
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        frame.setContentPane(new Mainpage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IdentifySystem systemtype = new IdentifySystem();
        int UIwidth=0,UIheight=0;
        if (systemtype.identifySystem_int()==1) //MAC及linux系统下窗口大小
        {
            UIwidth=600;
            UIheight=450;
        }
        else //Windows系统下窗口大小
        {
            UIwidth=800;
            UIheight=600;
        }
        //设置大小
        frame.setSize(UIwidth,UIheight);
        frame.setLocationRelativeTo(null);
        //使窗体显示在屏幕中央
        frame.setVisible(true);
        frame.setTitle("图片文件管理仓库");
    }
}