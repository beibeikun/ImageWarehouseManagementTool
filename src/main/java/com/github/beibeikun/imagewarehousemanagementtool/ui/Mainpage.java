package com.github.beibeikun.imagewarehousemanagementtool.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.FilePathChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.*;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.*;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.*;
import com.github.beibeikun.imagewarehousemanagementtool.util.mainpageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.ChangeAllSuffix.changeAllSuffix;
import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.OnlyCompressFiles.onlyCompressFiles;
import static com.github.beibeikun.imagewarehousemanagementtool.util.mainpageUtilsWithTasks.*;

public class Mainpage
{
    static JFrame frame = new JFrame("MainpageUI");
    private static MenuBar menuBar;
    SelectFilePath getfilepath = new SelectFilePath();
    mainpageUtils mainpageutil = new mainpageUtils();
    private	JButton CheckButton,renameStartButton,selectLastPathButton,selectFirstPathButton,uploadToDatabaseButton,deleteButton,SearchButton,selectRenameCsvButton,connectToDatabaseButton,connectToCameraWarehouseButton,connectToPhoneWarehouseButton,consoleButton,extractMainImageFromSourceFolderButton,downloadMainImageFromDatabaseButton,checkMainImageWithCsvButton,changeSuffixButton,sortByFolderButton,changeTargetToSourceButton,selectCheckCsvButton,extractAllImageFromSourceFolderButton,exchangeFirstPathButton,compressButton,selectPdfCsvButton,toPdfButton,selectDeleteCsvButton;
    private	JCheckBox digitCheckBox,prefixCheckBox,classificationCheckBox,addFromDatabaseCheckBox,uploadReplaceCheckBox,uploadDeleteCheckBox,suffixCheckBox;
    private	JComboBox suffixComboBox,selectdatabase,imgsizecomboBox,uploadSizeComboBox,deleteTypeComboBox,onlyCompressSizeChooseComboBox,uploadDatabaseComboBox,pdfOutTypeComboBox;
    private	JLabel versionLabel,sourceFolderPath,targetFolderPath,renameCsvPath,databaseAddressText,databaseUserNameText,cameraWarehouseAddressText,phoneWarehouseAddressText,githuburlLabel,testmode,mode,Suffix,checkCsvPath,pdfCsvPath,enterJBNum,suffixLabel,sizeLabel,addFromDatabaseLabel,tab2SizeLabel,uploadDatabaseLabel,uploadSizeLabel,pdfStaffLabel,pdfOutTypeLabel,deleteCsvPath,deleteJBLabel,deleteTypeLabel,databaseAddressLabel,databaseUserNameLabel,cameraWarehouseAddressLabel,phoneWarehouseAddressLabel;
    private	JPanel panel1,Tab1,Tab2,Tab3,Tab4,Tab5,Tab6;
    private	JScrollPane printOutScrollPane;
    private	JTabbedPane tabbedPane;
    private	JTextArea printOutTextArea,consoleTextArea;
    private	JTextField searchJBNumTextField,deleteJBNumTextField,pdfStaffTextField;
    private	JToolBar JT_firstpath,JT_renameCsvPath,JT_lastpath,JT_otherSettings,JT_checkCsvPath,JT_search,JT_upload,JT_pdfCsvPath,JT_namingFormat,JT_deleteCsvPath,JT_database,JT_cameraWarehouse,JT_phoneWarehouse;

    public Mainpage()
    {
        //加载界面
        initUIComponents();
        //加载监听
        addListeners();
        //加载设置
        loadSettings();
    }

    public static void main(String[] args)
    {
        if (SystemInfo.isMacFullWindowContentSupported)
        {
            frame.getRootPane().putClientProperty("apple.awt.fullWindowContent", true);
            frame.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        }


        frame.setTitle(null);
        SystemChecker system = new SystemChecker();//获取系统类型
        SwingUtilities.invokeLater(() ->
        {

            FlatDarkLaf.setup();

            try
            {
                StartCheck.startCheck();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            try
            {
                // 创建File对象
                File imageFile = new File(System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String() + "logo.png");

                // 使用ImageIO读取文件并转换为BufferedImage
                BufferedImage image = ImageIO.read(imageFile);
                frame.setIconImage(image);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            frame.setContentPane(new Mainpage().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SystemChecker systemtype = new SystemChecker();
            int UIwidth = 0, UIheight = 0;
            if (systemtype.identifySystem_int() == 1) //MAC及linux系统下窗口大小
            {
                UIwidth = 600;
                UIheight = 450;
            }
            else //Windows系统下窗口大小
            {
                UIwidth = 800;
                UIheight = 600;
            }
            //设置大小
            frame.setSize(UIwidth, UIheight);
            frame.setLocationRelativeTo(null);
            //使窗体显示在屏幕中央
            frame.setVisible(true);
            if (! SystemInfo.isMacFullWindowContentSupported)
            {
                frame.setTitle("Image Warehouse Management Tool");
            }
        });
    }

    /**
     * 初始化界面组件设置
     */
    private void initUIComponents()
    {
        // 设置组件属性，如可见性等
        printOutScrollPane.setVisible(false);
        //识别系统语言
        Locale defaultLocale = Locale.getDefault();
        String language = defaultLocale.getLanguage();
        //根据系统语言加载资源文件，已支持中文/日语
        language = language + ".properties";
        Properties properties = new Properties();
        SystemChecker system = new SystemChecker();//获取系统类型
        try (InputStream inputStream = getClass().getResourceAsStream("/" + language);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        {
            properties.load(reader);
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields)
            {
                if (JTabbedPane.class.isAssignableFrom(field.getType()))
                {
                    field.setAccessible(true);  // 确保可以访问私有字段
                    JTabbedPane tabPane = (JTabbedPane) field.get(this);
                    // 做一些操作，比如设置标题
                    for (int i = 0; i < tabPane.getTabCount(); i++)
                    {
                        // 假设 properties 文件中有类似 Tab0, Tab1 的键
                        String tabTitle = properties.getProperty("Tab" + (i + 1));
                        if (tabTitle != null)
                        {
                            tabPane.setTitleAt(i, tabTitle);
                        }
                    }
                }
                if (JComponent.class.isAssignableFrom(field.getType()))
                {
                    String value = properties.getProperty(field.getName());
                    if (value != null)
                    {
                        SwingUtilities.invokeLater(() ->
                        {
                            try
                            {
                                JComponent component = (JComponent) field.get(this);
                                if (component instanceof JButton)
                                {
                                    ((JButton) component).setText(value);
                                }
                                else if (component instanceof JToolBar)
                                {
                                    component.setBorder(BorderFactory.createTitledBorder(value));
                                }
                                else if (component instanceof JLabel)
                                {
                                    ((JLabel) component).setText(value);
                                }
                                else if (component instanceof JCheckBox)
                                {
                                    ((JCheckBox) component).setText(value);
                                }
                                for (int i = 0; i < tabbedPane.getTabCount(); i++)
                                {
                                    // 假设 properties 文件中有类似 Tab0, Tab1 的键
                                    String tabTitle = properties.getProperty(field.getName() + "Tab" + (i + 1));
                                    if (tabTitle != null)
                                    {
                                        tabbedPane.setTitleAt(i, tabTitle);
                                    }
                                }

                                // 添加其他组件类型的处理逻辑
                            }
                            catch (IllegalAccessException e)
                            {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
            // 读取属性值...
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(new OutputStream()
        {
            @Override
            public void write(int b)
            {
                printOutTextArea.append(String.valueOf((char) b));
                printOutTextArea.setCaretPosition(printOutTextArea.getDocument().getLength());
            }
        }));

        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(GetPropertiesPath.settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            settingsproperties.load(reader);
            databaseAddressText.setText(settingsproperties.getProperty("databaseAddressText"));
            databaseUserNameText.setText(settingsproperties.getProperty("databaseUserNameText"));
            sourceFolderPath.setText(settingsproperties.getProperty("sourceFolderPath"));
            targetFolderPath.setText(settingsproperties.getProperty("targetFolderPath"));
            renameCsvPath.setText(settingsproperties.getProperty("renameCsvPath"));
            checkCsvPath.setText(settingsproperties.getProperty("checkCsvPath"));
            pdfCsvPath.setText(settingsproperties.getProperty("pdfCsvPath"));
            cameraWarehouseAddressText.setText(settingsproperties.getProperty("cameraWarehouseAddressText"));
            phoneWarehouseAddressText.setText(settingsproperties.getProperty("phoneWarehouseAddressText"));

            // 读取属性值...
        }
        catch (IOException e)
        {
            SystemPrintOut.systemPrintOut("No settings file", 2, 0);
            e.printStackTrace();
        }

        String[][] filenamelist = new String[2][10];
        filenamelist[0][0] = "versionNumber";
        if (VersionNumber.getReleaseType())
        {
            versionLabel.setText("Build: V" + VersionNumber.officialVersionNumber() + " by beibeikun");//正式版
            filenamelist[1][0] = VersionNumber.officialVersionNumber();
        }
        else
        {
            versionLabel.setText("Build: Beta V" + VersionNumber.betaVersionNumber() + " by beibeikun");//测试版
            filenamelist[1][0] = VersionNumber.betaVersionNumber();
        }
        //versionLabel.setText(getVersionNumber());//显示为当前版本号
        githuburlLabel.setText("<html><u>GitHub Homepage</u></html>");//显示github地址

        WriteToProperties.writeToProperties("settings", filenamelist);
    }

    /**
     * 添加事件监听器
     */
    private void addListeners()
    {
        SystemChecker system = new SystemChecker();//获取系统类型
        final String[] exchangeFirstPath = {null};
        /*--------------------------------按键监听--------------------------------*/
        /*================================顶部================================*/

        //展开&收起信息面板
        consoleButton.addActionListener(e -> mainpageutil.informationPanelControl(consoleButton, printOutScrollPane, frame));

        //选择源文件夹
        selectFirstPathButton.addActionListener(e -> mainpageutil.selectPath(sourceFolderPath,"sourceFolderPath",2));

        //源文件夹路径临时储存
        exchangeFirstPathButton.addActionListener(e -> exchangeFirstPath[0] =mainpageutil.exchangepath(exchangeFirstPath[0], sourceFolderPath, exchangeFirstPathButton));

        //选择目标文件夹
        selectLastPathButton.addActionListener(e -> mainpageutil.selectPath(targetFolderPath,"targetFolderPath",2));

        //将目标文件夹路径复制到源文件夹路径
        changeTargetToSourceButton.addActionListener(e -> mainpageutil.changeTargetToSourcePath(sourceFolderPath,targetFolderPath));
        /*================================第一页：批量重命名================================*/

        //选择csv文件
        selectRenameCsvButton.addActionListener(e -> mainpageutil.selectPath(renameCsvPath,"renameCsvPath",1));

        //检查源文件夹，目标文件夹以及csv文件正确性
        CheckButton.addActionListener(e ->
        {
            int renamecsvpathcheck = FilePathChecker.checkFilePath(renameCsvPath.getText(), false);
            int firstpathcheck = FilePathChecker.checkFilePath(sourceFolderPath.getText(), false);
            int lastpathcheck = FilePathChecker.checkFilePath(targetFolderPath.getText(), false);
            if (FilePathChecker.checkback(renamecsvpathcheck, firstpathcheck, lastpathcheck, targetFolderPath.getText()))
            {
                renameStartButton.setEnabled(true);
                changeSuffixButton.setEnabled(true);
            }
        });

        //执行文件重命名
        renameStartButton.addActionListener(e ->
        {
            renameWithTasks(addFromDatabaseCheckBox, selectdatabase, cameraWarehouseAddressText, phoneWarehouseAddressText, classificationCheckBox, suffixCheckBox, suffixComboBox, imgsizecomboBox, sourceFolderPath, targetFolderPath, renameCsvPath);
            renameStartButton.setEnabled(false);
            changeSuffixButton.setEnabled(false);
        });
        /*================================第二页：文件操作================================*/

        //将源文件夹内容按前缀进行分类
        sortByFolderButton.addActionListener(e -> filePrefixMoveWithTasks(sourceFolderPath));

        //执行从数据库中拉取csv列出的文件主图（缩略图）
        downloadMainImageFromDatabaseButton.addActionListener(e ->takeMainFromDatabaseWithTasks(checkCsvPath, cameraWarehouseAddressText, targetFolderPath));

        //执行从源文件夹中拉取文件主图
        extractMainImageFromSourceFolderButton.addActionListener(e ->extractMainImageWithTasks(sourceFolderPath, targetFolderPath));

        //更换后缀名
        changeSuffixButton.addActionListener(e ->
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception
                {
                    Instant instant1 = Instant.now();
                    try
                    {
                        changeAllSuffix(sourceFolderPath.getText(), targetFolderPath.getText(), 0);
                    }
                    catch (IOException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                    Instant instant2 = Instant.now();
                    JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
                    return null;
                }
            };
            worker.execute();
        });

        //校对源文件夹中的文件与csv的区别
        checkMainImageWithCsvButton.addActionListener(e ->
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception
                {
                    Instant instant1 = Instant.now();
                    try
                    {
                        FolderCsvComparator.compareAndGenerateCsv(sourceFolderPath.getText(), checkCsvPath.getText(), targetFolderPath.getText());
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }

                    Instant instant2 = Instant.now();
                    JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
                    return null;
                }
            };
            worker.execute();
        });

        selectCheckCsvButton.addActionListener(e -> mainpageutil.selectPath(checkCsvPath,"checkCsvPath",1));
        extractAllImageFromSourceFolderButton.addActionListener(e ->
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception
                {
                    Instant instant1 = Instant.now();
                    List<String> readyToCopyNameList = Arrays.asList(ArrayExtractor.extractRow(ReadCsvFile.csvToArray(checkCsvPath.getText()), 0));
                    try
                    {
                        FolderCopy.copyFolderWithList(sourceFolderPath.getText(), CreateFolder.createFolderWithTime(targetFolderPath.getText()), readyToCopyNameList);
                    }
                    catch (IOException ex)
                    {
                        throw new RuntimeException(ex);
                    }

                    Instant instant2 = Instant.now();
                    JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
                    return null;
                }
            };
            worker.execute();
        });
        compressButton.addActionListener(e ->
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception
                {
                    Instant instant1 = Instant.now();

                    int imgsize = ImgSize.getImgSize(onlyCompressSizeChooseComboBox.getSelectedItem().toString());
                    onlyCompressFiles(sourceFolderPath.getText(), targetFolderPath.getText(), imgsize);

                    Instant instant2 = Instant.now();
                    JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
                    return null;
                }
            };
            worker.execute();
        });
        /*================================第三页：仓库相关================================*/

        //上传到相机图片数据库
        uploadToDatabaseButton.addActionListener(e -> uploadToWarehouseWithTasks(sourceFolderPath, cameraWarehouseAddressText, phoneWarehouseAddressText,uploadDatabaseComboBox,uploadSizeComboBox,uploadReplaceCheckBox,uploadDeleteCheckBox));
        /*================================第三页：从仓库删除================================*/
        //TODO:还没做，这功能有点危险
        /*================================第四页：仓库查询================================*/

        //从数据库中查询主图
        SearchButton.addActionListener(e ->
        {
            String path = cameraWarehouseAddressText.getText() + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + "JB" + searchJBNumTextField.getText() + ".JPG";
            ImageUtils.openImage(path);
        });
        /*================================第五页：仓库配置================================*/

        //连接mysql数据库 TODO:具体功能还没做
        connectToDatabaseButton.addActionListener(e ->
        {
            DatabaseConnectionForm databaseConnectionForm = new DatabaseConnectionForm(new DataCallback()
            {
                public void onDataEntered(String address, String username, String password)
                {
                    // 在这里处理从第二个窗口返回的数据
                    databaseAddressText.setText(address);
                    databaseUserNameText.setText(username);
                }
            });
            databaseConnectionForm.setVisible(true);
        });

        //连接相机图片数据库
        connectToCameraWarehouseButton.addActionListener(e -> mainpageutil.connectToImgWarehouse(cameraWarehouseAddressText,"cameraWarehouseAddressText"));

        //连接手机图片数据库
        connectToPhoneWarehouseButton.addActionListener(e -> mainpageutil.connectToImgWarehouse(phoneWarehouseAddressText,"phoneWarehouseAddressText"));
        sortByFolderButton.addActionListener(e ->
        {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
            {
                @Override
                protected Void doInBackground() throws Exception
                {
                    FilePrefixMove.filePrefixMove(sourceFolderPath.getText(), "-");
                    return null;
                }
            };

            worker.execute();

        });
        selectPdfCsvButton.addActionListener(e -> mainpageutil.selectPath(pdfCsvPath,"pdfCsvPath",1));
        /*================================底部================================*/


        /*--------------------------------按键监听--------------------------------*/

        deleteButton.addActionListener(e ->
        {
            try
            {
                DeleteFileFromDatabase.deleteFileFromDatabase(cameraWarehouseAddressText.getText(), deleteJBNumTextField.getText());
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });
        //生成pdf文件
        toPdfButton.addActionListener(e -> csvToPdfWithTasks(targetFolderPath, pdfOutTypeComboBox, pdfStaffTextField, pdfCsvPath));
    }
    /**
     * 加载设置和配置
     */
    private void loadSettings()
    {
        //显示github信息
        githuburlLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                githuburlLabel.setForeground(Color.decode("#bbbbbb").darker());
                githuburlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                githuburlLabel.setForeground(Color.decode("#bbbbbb"));
                githuburlLabel.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    Desktop.getDesktop().browse(new URI(VersionNumber.getGithub()));
                }
                catch (Exception ea)
                {
                    ea.printStackTrace();
                }
            }
        });
    }
    public interface DataCallback
    {
        void onDataEntered(String address, String username, String password);
    }
}
