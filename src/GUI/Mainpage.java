package GUI;

import Module.CheckOperations.SystemChecker;
import Module.FileOperations.FileCompression;
import Module.Others.VersionNumber;
import Module.Refactor.CompleteNameChangeProcess;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Locale;
import java.util.Properties;

import static GUI.ImageUtils.openImage;
import static Module.CheckOperations.FilePathChecker.checkFilePath;
import static Module.CheckOperations.FilePathChecker.checkback;
import static Module.DataOperations.FolderCsvComparator.compareAndGenerateCsv;
import static Module.DataOperations.WriteToProperties.writeToProperties;
import static Module.FileOperations.ExtractMainImage.extractMainImage;
import static Module.FileOperations.TakeMainFromDatabase.takeMainFromDatabase;
import static Module.Others.GetSettingsPath.settingspath;
import static Module.Others.GetTimeConsuming.getTimeConsuming;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class Mainpage {
    static JFrame frame = new JFrame("MainpageUI");
    static VersionNumber versionnumber = new VersionNumber();//获取版本号
    private static MenuBar menuBar;
    SelectFilePath getfilepath = new SelectFilePath();
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
    private JButton AddtophonedatabaseButton;
    private JComboBox selectdatabase;
    private JButton takemainfromdatabaseButton;
    private JButton checkMainIMGButton;
    private JComboBox imgsizecomboBox;
    private JTextArea consoleTextArea;

    public Mainpage() {
        ScrollPane1.setVisible(false);
        //识别系统语言
        Locale defaultLocale = Locale.getDefault();
        String language = defaultLocale.getLanguage();
        //根据系统语言加载资源文件，目前只有中文
        language = language + ".properties";
        Properties properties = new Properties();
        SystemChecker system = new SystemChecker();//获取系统类型
        try (FileInputStream fis = new FileInputStream("src" + system.identifySystem_String() + "properties" + system.identifySystem_String() + language);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(reader);
            CheckBox_digit.setText(properties.getProperty("CheckBox_digit"));
            CheckBox_prefix.setText(properties.getProperty("CheckBox_prefix"));
            // 读取属性值...
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea1.append(String.valueOf((char) b));
            }
        }));

        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            settingsproperties.load(reader);
            databaseaddress.setText(settingsproperties.getProperty("databaseaddress"));
            databaseusername.setText(settingsproperties.getProperty("databaseusername"));
            firstpath.setText(settingsproperties.getProperty("firstpath"));
            lastpath.setText(settingsproperties.getProperty("lastpath"));
            renamecsvpath.setText(settingsproperties.getProperty("renamecsvpath"));
            cameradatabasepath.setText(settingsproperties.getProperty("cameradatabasepath"));
            phonedatabasepath.setText(settingsproperties.getProperty("phonedatabasepath"));
            systemPrintOut("Read settings file",1,0);
            // 读取属性值...
        } catch (IOException e) {
            systemPrintOut("No settings file",2,0);
            e.printStackTrace();
        }


        versionLabel.setText(versionnumber.getVersionNumber());//显示为当前版本号
        githuburlLabel.setText("<html><u>GitHub Homepage</u></html>");//显示github地址

        /*--------------------------------按键监听--------------------------------*/
        /*================================顶部================================*/

        //展开&收起信息面板
        consolebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (consolebutton.getText().equals(">")) {
                    ScrollPane1.setVisible(true);
                    frame.setSize(frame.getWidth() + 600, frame.getHeight());
                    consolebutton.setText("<");
                    frame.setLocationRelativeTo(null);
                } else {
                    ScrollPane1.setVisible(false);
                    frame.setSize(frame.getWidth() - 600, frame.getHeight());
                    consolebutton.setText(">");
                    frame.setLocationRelativeTo(null);
                }

            }
        });

        //选择源文件夹
        SelectButton_firstpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sfirstpath = getfilepath.selectFilePath(2, firstpath.getText());
                firstpath.setText(Sfirstpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "firstpath";
                filenamelist[1][0] = firstpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });
        /*================================第一页：批量重命名================================*/

        //选择目标文件夹
        SelectButton_lastpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Slastpath = getfilepath.selectFilePath(2, lastpath.getText());
                lastpath.setText(Slastpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "lastpath";
                filenamelist[1][0] = lastpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });

        //选择csv文件
        SelectButton_renamecsvpath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Srenamecsvpath = getfilepath.selectFilePath(1, renamecsvpath.getText());
                renamecsvpath.setText(Srenamecsvpath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "renamecsvpath";
                filenamelist[1][0] = renamecsvpath.getText();
                writeToProperties("settings", filenamelist);
            }
        });

        //检查源文件夹，目标文件夹以及csv文件正确性
        CheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int renamecsvpathcheck = checkFilePath(renamecsvpath.getText(), false);
                int firstpathcheck = checkFilePath(firstpath.getText(), false);
                int lastpathcheck = checkFilePath(lastpath.getText(), true);
                if (checkback(renamecsvpathcheck,firstpathcheck,lastpathcheck,lastpath.getText()))
                {
                    RenamestartButton.setEnabled(true);
                    takemainfromdatabaseButton.setEnabled(true);
                    ExtractMainImageButton.setEnabled(true);
                }
            }
        });

        //执行文件重命名
        RenamestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                int check_usedatabase = 0, check_whichdatabase = 0;
                if (CheckBox_addfromdatabase.isSelected()) {
                    check_usedatabase = 1;
                }
                if (selectdatabase.getSelectedItem().equals("phone")) {
                    check_whichdatabase = 1;
                }
                String databasepath = null;
                if (check_whichdatabase == 0) {
                    databasepath = cameradatabasepath.getText();
                } else {
                    databasepath = phonedatabasepath.getText();
                }
                CompleteNameChangeProcess completeNameChangeProcess = new CompleteNameChangeProcess();
                try {
                    int imgsize;
                    if (imgsizecomboBox.getSelectedItem().equals("原尺寸")) {
                        imgsize = 0;
                    } else {
                        imgsize = Integer.parseInt(imgsizecomboBox.getSelectedItem().toString().substring(0, 4));
                    }
                    systemPrintOut("Start to rename",1,0);
                    completeNameChangeProcess.completeNameChangeProcess(databasepath, firstpath.getText(), lastpath.getText(), renamecsvpath.getText(), check_usedatabase, imgsize);
                } catch (IOException | ImageProcessingException | MetadataException ex) {
                    throw new RuntimeException(ex);
                }


                Instant instant2 = Instant.now();
                JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1, instant2) + "秒");
                RenamestartButton.setEnabled(false);
                takemainfromdatabaseButton.setEnabled(false);
                ExtractMainImageButton.setEnabled(false);
            }
        });

        //执行从数据库中拉取csv列出的文件主图（缩略图）
        takemainfromdatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                takeMainFromDatabase(renamecsvpath.getText(),cameradatabasepath.getText(),lastpath.getText());
                Instant instant2 = Instant.now();
                JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1,instant2) + "秒");
                RenamestartButton.setEnabled(false);
                takemainfromdatabaseButton.setEnabled(false);
                ExtractMainImageButton.setEnabled(false);
            }
        });

        //执行从源文件夹中拉取文件主图
        ExtractMainImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                extractMainImage(firstpath.getText(), lastpath.getText());
                Instant instant2 = Instant.now();
                JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1,instant2) + "秒");
                RenamestartButton.setEnabled(false);
                takemainfromdatabaseButton.setEnabled(false);
                ExtractMainImageButton.setEnabled(false);

            }
        });

        //校对源文件夹中的文件与csv的区别
        checkMainIMGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instant instant1 = Instant.now();
                try {
                    compareAndGenerateCsv(firstpath.getText(), renamecsvpath.getText(), lastpath.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Instant instant2 = Instant.now();
                JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1, instant2) + "秒");
            }
        });
        /*================================第二页：上传至仓库================================*/

        //上传到相机图片数据库
        AddtocameradatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "上传任务耗时长，运行期间界面假死为正常现象，请勿终止程序或断开硬盘！", "确认", JOptionPane.YES_NO_OPTION); //返回值为0或1
                if (n == 0) {
                    int filepathcheck = checkFilePath(firstpath.getText(), false);
                    if (filepathcheck == 1) {
                        Instant instant1 = Instant.now();
                        try {
                            String[] prefixes = FileCompression.compressFilesByPrefix(firstpath.getText(), cameradatabasepath.getText(), 1);
                            if (deleteCheckBox.isSelected())//完成后删除文件
                            {
                                //TODO 完成后删除源文件夹中的所有内容
                            }

                        } catch (IOException | ImageProcessingException | MetadataException ex) {
                            throw new RuntimeException(ex);
                        }
                        Instant instant2 = Instant.now();
                        JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1,instant2) + "秒");


                    } else if (filepathcheck == 2) {
                        JOptionPane.showMessageDialog(null, "未选取路径", "路径错误", JOptionPane.WARNING_MESSAGE);
                    } else if (filepathcheck == 3) {
                        JOptionPane.showMessageDialog(null, "路径名存在中文字符", "路径错误", JOptionPane.WARNING_MESSAGE);
                    } else if (filepathcheck == 4) {
                        JOptionPane.showMessageDialog(null, "源文件夹路径不存在", "路径错误", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //上传到手机图片数据库
        AddtophonedatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "上传任务耗时长，运行期间界面假死为正常现象，请勿终止程序或断开硬盘！", "确认", JOptionPane.YES_NO_OPTION); //返回值为0或1
                if (n == 0) {
                    int filepathcheck = checkFilePath(firstpath.getText(), false);
                    if (filepathcheck == 1) {
                        Instant instant1 = Instant.now();
                        try {
                            String[] prefixes = FileCompression.compressFilesByPrefix(firstpath.getText(), phonedatabasepath.getText(), 2);
                            if (deleteCheckBox.isSelected())//完成后删除文件
                            {
                                //TODO 完成后删除源文件夹中的所有内容
                            }

                        } catch (IOException | ImageProcessingException | MetadataException ex) {
                            throw new RuntimeException(ex);
                        }
                        Instant instant2 = Instant.now();
                        JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + getTimeConsuming(instant1,instant2) + "秒");


                    } else if (filepathcheck == 2) {
                        JOptionPane.showMessageDialog(null, "未选取路径", "路径错误", JOptionPane.WARNING_MESSAGE);
                    } else if (filepathcheck == 3) {
                        JOptionPane.showMessageDialog(null, "路径名存在中文字符", "路径错误", JOptionPane.WARNING_MESSAGE);
                    } else if (filepathcheck == 4) {
                        JOptionPane.showMessageDialog(null, "源文件夹路径不存在", "路径错误", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        /*================================第三页：从仓库删除================================*/
        //TODO:还没做，这功能有点危险
        /*================================第四页：仓库查询================================*/

        //从数据库中查询主图
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = cameradatabasepath.getText() + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + "JB" + textField1.getText() + ".JPG";
                openImage(path);
            }
        });
        /*================================第五页：仓库配置================================*/

        //连接mysql数据库 TODO:具体功能还没做
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

        //连接相机图片数据库
        ConnectButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Scamerapath = getfilepath.selectFilePath(2, cameradatabasepath.getText());
                cameradatabasepath.setText(Scamerapath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "cameradatabasepath";
                filenamelist[1][0] = Scamerapath;
                writeToProperties("settings", filenamelist);
            }
        });

        //连接手机图片数据库
        ConnectButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sphonepath = getfilepath.selectFilePath(2, phonedatabasepath.getText());
                phonedatabasepath.setText(Sphonepath);
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "phonedatabasepath";
                filenamelist[1][0] = Sphonepath;
                writeToProperties("settings", filenamelist);
            }
        });
        /*================================底部================================*/

        //显示github信息
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
        /*--------------------------------按键监听--------------------------------*/

    }

    public static void main(String[] args) {
        try {
            // 创建File对象
            File imageFile = new File("logo.ico");

            // 使用ImageIO读取文件并转换为BufferedImage
            BufferedImage image = ImageIO.read(imageFile);
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FlatDarkLaf.setup();
        frame.setContentPane(new Mainpage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SystemChecker systemtype = new SystemChecker();
        int UIwidth = 0, UIheight = 0;
        if (systemtype.identifySystem_int() == 1) //MAC及linux系统下窗口大小
        {
            UIwidth = 600;
            UIheight = 450;
        } else //Windows系统下窗口大小
        {
            UIwidth = 800;
            UIheight = 600;
        }
        //设置大小
        frame.setSize(UIwidth, UIheight);
        frame.setLocationRelativeTo(null);
        //使窗体显示在屏幕中央
        frame.setVisible(true);
        frame.setTitle("IWMT");
    }

    public interface DataCallback {
        void onDataEntered(String address, String username, String password);
    }
}
