package com.github.beibeikun.imagewarehousemanagementtool.util;

import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.ExtractMainImage;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FilePrefixMove;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.TakeMainFromDatabase;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetTimeConsuming;
import com.github.beibeikun.imagewarehousemanagementtool.util.Test.AsyncTaskExecutor;

import javax.swing.*;
import java.time.Instant;
import java.util.concurrent.Callable;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.ChangeAllSuffix.changeAllSuffix;
import static com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess.OnlyCompressFiles.onlyCompressFiles;
import static com.github.beibeikun.imagewarehousemanagementtool.util.mainpageUtils.rename;
import static com.github.beibeikun.imagewarehousemanagementtool.util.mainpageUtils.uploadToWarehouse;

public class mainpageUtilsWithTasks
{
    static mainpageUtils mainpageutil = new mainpageUtils();

    /**
     *使用异步进行重命名任务
     */
    public static void renameWithTasks(JCheckBox CheckBox_addfromdatabase, JComboBox selectdatabase, JLabel cameradatabasepath, JLabel phonedatabasepath, JCheckBox CheckBox_classification, JCheckBox suffixCheckBox, JComboBox comboBox1, JComboBox imgsizecomboBox, JLabel sourceFolderPath, JLabel targetFolderPath, JLabel renameCsvPath)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            rename(CheckBox_addfromdatabase, selectdatabase, cameradatabasepath, phonedatabasepath, CheckBox_classification, suffixCheckBox, comboBox1, imgsizecomboBox, sourceFolderPath, targetFolderPath, renameCsvPath);
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
        Instant instant2 = Instant.now();
    }
    public static void uploadToWarehouseWithTasks(JLabel sourceFolderPath, JLabel cameradatabasepath, JLabel phonedatabasepath, JComboBox uploadDatabaseComboBox, JComboBox uploadSizeComboBox, JCheckBox uploadReplaceCheckBox, JCheckBox uploadDeleteCheckBox)
    {
        Callable<Void> task = () -> {
            uploadToWarehouse(sourceFolderPath,cameradatabasepath,phonedatabasepath,uploadDatabaseComboBox,uploadSizeComboBox,uploadReplaceCheckBox,uploadDeleteCheckBox);
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
        Instant instant2 = Instant.now();
    }
    public static void csvToPdfWithTasks(JLabel targetFolderPath, JComboBox pdfComboBox, JTextField pdfStaffTextField, JLabel pdfCsvPath)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            mainpageutil.csvToPdf(targetFolderPath, pdfComboBox, pdfStaffTextField, pdfCsvPath);
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
        Instant instant2 = Instant.now();
    }
    public static void filePrefixMoveWithTasks(JLabel sourceFolderPath)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            FilePrefixMove.filePrefixMove(sourceFolderPath.getText(), "-");
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
    }
    public static void takeMainFromDatabaseWithTasks(JLabel checkCsvPath, JLabel cameraWarehouseAddressText, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            TakeMainFromDatabase.takeMainFromDatabase(checkCsvPath.getText(), cameraWarehouseAddressText.getText(), targetFolderPath.getText());
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
    }
    public static void extractMainImageWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            ExtractMainImage.extractMainImage(sourceFolderPath.getText(), targetFolderPath.getText());
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
    }
    public static void changeAllSuffixWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, int mode)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            changeAllSuffix(sourceFolderPath.getText(), targetFolderPath.getText(), mode);
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
    }
    public static void onlyCompressFilesWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, int imgsize)
    {
        Callable<Void> task = () -> {
            Instant instant1 = Instant.now();
            onlyCompressFiles(sourceFolderPath.getText(), targetFolderPath.getText(), imgsize);
            Instant instant2 = Instant.now();
            JOptionPane.showMessageDialog(null, "任务完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(instant1, instant2) + "秒");
            return null;
        };
        AsyncTaskExecutor.executeInBackground(
                task
        );
    }
}
