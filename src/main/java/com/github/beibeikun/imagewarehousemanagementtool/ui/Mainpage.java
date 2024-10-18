package com.github.beibeikun.imagewarehousemanagementtool.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.*;
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
import java.util.*;

import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteFileFromDatabase.deleteFileFromDatabase;
import static com.github.beibeikun.imagewarehousemanagementtool.util.mainpageUtilsWithTasks.*;

public class Mainpage
{
    static JFrame frame = new JFrame("MainpageUI");
    private static MenuBar menuBar;
    mainpageUtils mainpageutil = new mainpageUtils();
    private JButton renameStartButton, selectLastPathButton, selectFirstPathButton, uploadToDatabaseButton, deleteButton, SearchButton, selectRenameCsvButton, connectToDatabaseButton, connectToCameraWarehouseButton, connectToPhoneWarehouseButton, languageButton, extractMainImageFromSourceFolderButton, downloadMainImageFromDatabaseButton, checkMainImageWithCsvButton, changeSuffixButton, sortByFolderButton, changeTargetToSourceButton, selectCheckCsvButton, extractAllImageFromSourceFolderButton, exchangeFirstPathButton, compressButton, selectPdfCsvButton, toPdfButton, selectDeleteCsvButton;
    private JCheckBox digitCheckBox, prefixCheckBox, classificationCheckBox, addFromDatabaseCheckBox, uploadReplaceCheckBox, uploadDeleteCheckBox, suffixCheckBox;
    private JComboBox suffixComboBox, selectdatabase, imgsizecomboBox, uploadSizeComboBox, deleteTypeComboBox, onlyCompressSizeChooseComboBox, uploadDatabaseComboBox, pdfOutTypeComboBox;
    private JLabel versionLabel, sourceFolderPath, targetFolderPath, renameCsvPath, databaseAddressText, databaseUserNameText, cameraWarehouseAddressText, phoneWarehouseAddressText, githuburlLabel, testmode, mode, Suffix, checkCsvPath, pdfCsvPath, enterJBNum, suffixLabel, sizeLabel, addFromDatabaseLabel, tab2SizeLabel, uploadDatabaseLabel, uploadSizeLabel, pdfStaffLabel, pdfOutTypeLabel, deleteCsvPath, deleteJBLabel, deleteTypeLabel, databaseAddressLabel, databaseUserNameLabel, cameraWarehouseAddressLabel, phoneWarehouseAddressLabel;
    private JPanel panel1, Tab1, Tab2, Tab3, Tab4, Tab5, Tab6;
    private JScrollPane printOutScrollPane;
    private JTabbedPane tabbedPane;
    private JTextArea printOutTextArea, consoleTextArea;
    private JTextField searchJBNumTextField, deleteJBNumTextField, pdfStaffTextField;
    private JToolBar JT_firstpath, JT_renameCsvPath, JT_lastpath, JT_otherSettings, JT_checkCsvPath, JT_search, JT_upload, JT_pdfCsvPath, JT_namingFormat, JT_deleteCsvPath, JT_database, JT_cameraWarehouse, JT_phoneWarehouse;
    private JButton organizeAndSortButton;
    private JButton downloadFromDatabaseButton;
    private JComboBox chooseDownloadDatabaseComboBox;
    private JCheckBox useCsvCheckBox;
    private JCheckBox organizeAndSortCheckBox;

    public Mainpage()
    {
        //加载界面
        initUIComponents();
        //加载监听
        addListeners();
        //加载设置
        loadSettings();
    }

    /**
     * 主程序执行入口
     */
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
            int uiWidth, uiHeight;
            if (system.identifySystem_int() == 1) //MAC及linux系统下窗口大小
            {
                uiWidth = 1600;
                uiHeight = 550;
            }
            else //Windows系统下窗口大小
            {
                uiWidth = 1800;
                uiHeight = 700;
            }
            //设置大小
            frame.setSize(uiWidth, uiHeight);
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
        // 根据系统语言配置
        setLanguage(Locale.getDefault().getLanguage());

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

        //切换语言
        languageButton.addActionListener(e ->
        {
            if (languageButton.getText().equals("日")) setLanguage("ja");
            else setLanguage("zh");
        });

        //选择源文件夹
        selectFirstPathButton.addActionListener(e -> mainpageutil.selectPath(sourceFolderPath, "sourceFolderPath", 2));

        //源文件夹路径临时储存
        exchangeFirstPathButton.addActionListener(e -> exchangeFirstPath[0] = mainpageutil.exchangepath(exchangeFirstPath[0], sourceFolderPath, exchangeFirstPathButton));

        //选择目标文件夹
        selectLastPathButton.addActionListener(e -> mainpageutil.selectPath(targetFolderPath, "targetFolderPath", 2));

        //将目标文件夹路径复制到源文件夹路径
        changeTargetToSourceButton.addActionListener(e -> mainpageutil.changeTargetToSourcePath(sourceFolderPath, targetFolderPath));
        /*================================第一页：批量重命名================================*/

        //选择csv文件
        selectRenameCsvButton.addActionListener(e -> mainpageutil.selectPath(renameCsvPath, "renameCsvPath", 1));

        //执行文件重命名
        renameStartButton.addActionListener(e ->
        {
            renameWithTasks(addFromDatabaseCheckBox, selectdatabase, cameraWarehouseAddressText, phoneWarehouseAddressText, classificationCheckBox, suffixCheckBox, suffixComboBox, imgsizecomboBox, sourceFolderPath, targetFolderPath, renameCsvPath);
        });
        /*================================第二页：文件操作================================*/

        //将源文件夹内容按前缀进行分类
        sortByFolderButton.addActionListener(e -> filePrefixMoveWithTasks(sourceFolderPath));

        //执行从数据库中拉取csv列出的文件主图（缩略图）
        downloadMainImageFromDatabaseButton.addActionListener(e -> takeMainFromDatabaseWithTasks(checkCsvPath, cameraWarehouseAddressText, targetFolderPath));

        //执行从源文件夹中拉取文件主图
        extractMainImageFromSourceFolderButton.addActionListener(e -> extractMainImageWithTasks(sourceFolderPath, targetFolderPath, useCsvCheckBox, checkCsvPath));

        //更换后缀名
        changeSuffixButton.addActionListener(e -> changeAllSuffixWithTasks(sourceFolderPath, targetFolderPath, 0));

        //校对源文件夹中的文件与csv的区别
        checkMainImageWithCsvButton.addActionListener(e -> compareAndGenerateCsvWithTasks(sourceFolderPath, checkCsvPath, targetFolderPath));

        selectCheckCsvButton.addActionListener(e -> mainpageutil.selectPath(checkCsvPath, "checkCsvPath", 1));

        extractAllImageFromSourceFolderButton.addActionListener(e -> copyFolderWithListWithTasks(sourceFolderPath, checkCsvPath, targetFolderPath));
        //仅压缩图片
        compressButton.addActionListener(e -> onlyCompressFilesWithTasks(sourceFolderPath, targetFolderPath, ImgSize.getImgSize(onlyCompressSizeChooseComboBox.getSelectedItem().toString())));

        organizeAndSortButton.addActionListener(e -> organizeAndSortWithTasks(sourceFolderPath, targetFolderPath, organizeAndSortCheckBox));
        /*================================第三页：仓库相关================================*/

        //上传到图片数据库
        uploadToDatabaseButton.addActionListener(e -> uploadToWarehouseWithTasks(sourceFolderPath, cameraWarehouseAddressText, phoneWarehouseAddressText, uploadDatabaseComboBox, uploadSizeComboBox, uploadReplaceCheckBox, uploadDeleteCheckBox));
        //从数据库中查询主图
        SearchButton.addActionListener(e ->
        {
            String path = cameraWarehouseAddressText.getText() + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + "JB" + searchJBNumTextField.getText() + ".JPG";
            ImageUtils.openImage(path);
        });
        downloadFromDatabaseButton.addActionListener(e ->
        {
            String warehouseAddressText = "";
            if (chooseDownloadDatabaseComboBox.getSelectedItem().toString().equals("相机库"))
            {
                warehouseAddressText = cameraWarehouseAddressText.getText();
            }
            else
            {
                warehouseAddressText = phoneWarehouseAddressText.getText();
            }
            downloadFromDatabaseWithTasks(warehouseAddressText,"JB" + searchJBNumTextField.getText(),targetFolderPath);
        });
        /*================================第三页：从仓库删除================================*/
        deleteButton.addActionListener(e ->
        {
            try
            {
                deleteFileFromDatabase(cameraWarehouseAddressText.getText(), deleteJBNumTextField.getText());
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }

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
        connectToCameraWarehouseButton.addActionListener(e -> mainpageutil.connectToImgWarehouse(cameraWarehouseAddressText, "cameraWarehouseAddressText"));

        //连接手机图片数据库
        connectToPhoneWarehouseButton.addActionListener(e -> mainpageutil.connectToImgWarehouse(phoneWarehouseAddressText, "phoneWarehouseAddressText"));

        sortByFolderButton.addActionListener(e -> filePrefixMoveWithTasks(sourceFolderPath));

        selectPdfCsvButton.addActionListener(e -> mainpageutil.selectPath(pdfCsvPath, "pdfCsvPath", 1));
        /*================================底部================================*/


        /*--------------------------------按键监听--------------------------------*/
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

    public void setLanguage(String language)
    {
        try
        {
            String local;
            ResourceBundle bundle;
            if (language.equals("zh")) local="CN";
            else local="JP";
            bundle = ResourceBundle.getBundle("Mainpage", new Locale(language,local));

            Field[] fields = Mainpage.class.getDeclaredFields();
            for (Field field : fields)
            {
                if (JTabbedPane.class.isAssignableFrom(field.getType()))
                {
                    field.setAccessible(true);
                    JTabbedPane tabPane = (JTabbedPane) field.get(this);
                    for (int i = 0; i < tabPane.getTabCount(); i++)
                    {
                        String tabTitle = bundle.getString("Tab" + (i + 1));
                        if (tabTitle != null)
                        {
                            tabPane.setTitleAt(i, tabTitle);
                        }
                    }
                }
                else if (JComponent.class.isAssignableFrom(field.getType()))
                {

                    String value;
                    try
                    {
                        value = bundle.getString(field.getName());
                    }
                    catch (MissingResourceException e)
                    {
                        value = ""; // 指定一个默认值
                    }
                    if (! value.isEmpty())
                    {
                        field.setAccessible(true);

                        String finalValue = value;
                        SwingUtilities.invokeLater(() ->
                        {
                            try
                            {
                                JComponent component = (JComponent) field.get(this);
                                if (component instanceof JButton)
                                {
                                    ((JButton) component).setText(finalValue);
                                }
                                else if (component instanceof JToolBar)
                                {
                                    component.setBorder(BorderFactory.createTitledBorder(finalValue));
                                }
                                else if (component instanceof JLabel)
                                {
                                    ((JLabel) component).setText(finalValue);
                                }
                                else if (component instanceof JCheckBox)
                                {
                                    ((JCheckBox) component).setText(finalValue);
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
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    public interface DataCallback
    {
        void onDataEntered(String address, String username, String password);
    }
}