package com.github.beibeikun.imagewarehousemanagementtool.util;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.github.beibeikun.imagewarehousemanagementtool.ui.SelectFilePath;
import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.FilePathChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.CompleteNameChangeProcess;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.GetLatestSubfolderPath;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ImgSize;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.WriteToProperties;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetTimeConsuming;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.CompressImgToZipAndUpload.compressImgToZipAndUpload;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Test.CsvReaderToList.readCsvAndProcess;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Test.PdfFormFiller.batchFillPdfForms;

public class mainpageUtils
{
    SelectFilePath getfilepath = new SelectFilePath();
    public String exchangepath(String exchangeFirstPath, JLabel sourceFolderPath, JButton exchangeFirstPathButton)
    {
        if (exchangeFirstPath == null)
        {
            exchangeFirstPath = sourceFolderPath.getText();
            exchangeFirstPathButton.setBackground(Color.GREEN);
            exchangeFirstPathButton.setForeground(Color.BLACK);
        }
        else if (sourceFolderPath.getText().equals(""))
        {
            sourceFolderPath.setText(exchangeFirstPath);
            String[][] filenamelist = new String[2][10];
            filenamelist[0][0] = "sourceFolderPath";
            filenamelist[1][0] = sourceFolderPath.getText();
            WriteToProperties.writeToProperties("settings", filenamelist);
        }
        else
        {
            String a = sourceFolderPath.getText();
            sourceFolderPath.setText(exchangeFirstPath);
            String[][] filenamelist = new String[2][10];
            filenamelist[0][0] = "sourceFolderPath";
            filenamelist[1][0] = sourceFolderPath.getText();
            WriteToProperties.writeToProperties("settings", filenamelist);
            exchangeFirstPath = a;
        }
        return exchangeFirstPath;
    }
    public void informationPanelControl(JButton consoleButton, JScrollPane ScrollPane, JFrame frame)
    {
        if (consoleButton.getText().equals(">"))
        {
            ScrollPane.setVisible(true);
            frame.setSize(frame.getWidth() + 600, frame.getHeight());
            consoleButton.setText("<");
            frame.setLocationRelativeTo(null);
        }
        else
        {
            ScrollPane.setVisible(false);
            frame.setSize(frame.getWidth() - 600, frame.getHeight());
            consoleButton.setText(">");
            frame.setLocationRelativeTo(null);
        }
    }
    public static void csvToPdf(JLabel targetFolderPath, JComboBox pdfComboBox, JTextField pdfStaffTextField, JLabel pdfCsvPath)
    {
        try
        {
            String destinationFolder = targetFolderPath.getText();
            destinationFolder = CreateFolder.createFolderWithTime(destinationFolder);
            String pdfPath;
            if (Objects.equals(pdfComboBox.getSelectedItem(), "打印"))
            {
                pdfPath = "/print.pdf";
            }
            else
            {
                pdfPath = "/out.pdf";
            }
            batchFillPdfForms(pdfPath, destinationFolder, pdfStaffTextField.getText(), readCsvAndProcess(pdfCsvPath.getText()));
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    public void selectPath(JLabel FolderPath, String name, int type)
    {
        String path = getfilepath.selectFilePath(type, FolderPath.getText());
        FolderPath.setText(path);
        String[][] filenamelist = new String[2][10];
        filenamelist[0][0] = name;
        filenamelist[1][0] = FolderPath.getText();
        WriteToProperties.writeToProperties("settings", filenamelist);
    }
    public void changeTargetToSourcePath(JLabel sourceFolderPath, JLabel targetFolderPath)
    {
        sourceFolderPath.setText(GetLatestSubfolderPath.getLatestSubfolder(targetFolderPath.getText()));
        String[][] filenamelist = new String[2][10];
        filenamelist[0][0] = "sourceFolderPath";
        filenamelist[1][0] = sourceFolderPath.getText();
        WriteToProperties.writeToProperties("settings", filenamelist);
    }
    public void connectToImgWarehouse(JLabel cameradatabasepath,String warehouse)
    {
        String path = getfilepath.selectFilePath(2, cameradatabasepath.getText());
        cameradatabasepath.setText(path);
        String[][] filenamelist = new String[2][10];
        filenamelist[0][0] = warehouse;
        filenamelist[1][0] = path;
        WriteToProperties.writeToProperties("settings", filenamelist);
    }

    /**
     * 实现主程序执行重命名的功能
     * @param CheckBox_addfromdatabase 数据库拉取判定
     * @param selectdatabase 数据库类型
     * @param cameradatabasepath 相机数据库
     * @param phonedatabasepath 手机数据库
     * @param CheckBox_classification
     * @param suffixCheckBox
     * @param comboBox1
     * @param imgsizecomboBox
     * @param sourceFolderPath
     * @param targetFolderPath
     * @param renameCsvPath
     */
    public static void rename(JCheckBox CheckBox_addfromdatabase, JComboBox selectdatabase, JLabel cameradatabasepath, JLabel phonedatabasepath, JCheckBox CheckBox_classification, JCheckBox suffixCheckBox, JComboBox comboBox1, JComboBox imgsizecomboBox, JLabel sourceFolderPath, JLabel targetFolderPath, JLabel renameCsvPath)
    {
        int check_usedatabase = 0, check_whichdatabase = 0;
        //判断是否从数据库拉取图片
        if (CheckBox_addfromdatabase.isSelected())
        {
            check_usedatabase = 1;
        }
        //判断数据库类型
        if (selectdatabase.getSelectedItem().equals("phone"))
        {
            check_whichdatabase = 1;
        }
        String databasepath = null;
        //根据数据库类型获取数据库路径
        if (check_whichdatabase == 0)
        {
            databasepath = cameradatabasepath.getText();
        }
        else
        {
            databasepath = phonedatabasepath.getText();
        }
        boolean prefix = CheckBox_classification.isSelected();
        String suffix = "";
        int suffixtype = 0;
        //判断是否需要更换后缀
        if (suffixCheckBox.isSelected())
        {
            suffix = (String) comboBox1.getSelectedItem();
        }
        //判断更换后缀类型
        if (suffix.equals("_x"))
        {
            suffixtype = 1;
        }
        CompleteNameChangeProcess completeNameChangeProcess = new CompleteNameChangeProcess();
        //获取压缩尺寸
        int imgsize = ImgSize.getImgSize(imgsizecomboBox.getSelectedItem().toString());
        try
        {
            SystemPrintOut.systemPrintOut("Start to rename", 1, 0);
            completeNameChangeProcess.completeNameChangeProcess(databasepath, sourceFolderPath.getText(), targetFolderPath.getText(), renameCsvPath.getText(), check_usedatabase, imgsize, false, prefix, suffixtype);
        }
        catch (IOException | ImageProcessingException | MetadataException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param sourceFolderPath 原文件夹路径
     * @param cameradatabasepath 相机数据库路径
     * @param phonedatabasepath 手机数据库路径
     * @param uploadDatabaseComboBox 选择数据库
     * @param uploadSizeComboBox 压缩尺寸
     * @param uploadReplaceCheckBox 覆盖上传
     * @param uploadDeleteCheckBox 完成后删除
     */
    public static void uploadToWarehouse(JLabel sourceFolderPath, JLabel cameradatabasepath, JLabel phonedatabasepath, JComboBox uploadDatabaseComboBox, JComboBox uploadSizeComboBox, JCheckBox uploadReplaceCheckBox, JCheckBox uploadDeleteCheckBox)
    {
        String databaseAddress;
        int uploadMode = 0;
        //选择数据库
        if (uploadDatabaseComboBox.getSelectedItem().toString().equals("相机库"))
        {
            databaseAddress = cameradatabasepath.getText();
            uploadMode = 1;
        }
        else
        {
            databaseAddress = phonedatabasepath.getText();
        }
        //获取压缩尺寸
        int imgsize = ImgSize.getImgSize(uploadSizeComboBox.getSelectedItem().toString());
        if (imgsize == 1)
        {
            if (uploadDatabaseComboBox.getSelectedItem().toString().equals("相机库"))
            {
                imgsize = 5000;
            }
            else
            {
                imgsize = 4000;
            }
        }
        Instant instant1 = Instant.now();
        boolean coverageDeterminer = false;
        boolean deleteQualifier = false;
        //替换判定
        if (uploadReplaceCheckBox.isSelected())
            coverageDeterminer = true;
        //删除判定
        if (uploadDeleteCheckBox.isSelected())
            deleteQualifier = true;
        try
        {
            compressImgToZipAndUpload(sourceFolderPath.getText(), databaseAddress, uploadMode, imgsize, coverageDeterminer, deleteQualifier);

        }
        catch (IOException | ImageProcessingException | MetadataException ex)
        {
            throw new RuntimeException(ex);
        }
        Instant instant2 = Instant.now();
        JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
    }
}
