package com.github.beibeikun.imagewarehousemanagementtool.util.task;

import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ReadCsvFile;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.common.AsyncTaskExecutor;
import com.github.beibeikun.imagewarehousemanagementtool.util.common.GetTimeConsuming;

import javax.swing.*;
import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static com.github.beibeikun.imagewarehousemanagementtool.util.process.ChangeAllSuffix.changeAllSuffix;
import static com.github.beibeikun.imagewarehousemanagementtool.util.process.OnlyCompressFiles.onlyCompressFiles;
import static com.github.beibeikun.imagewarehousemanagementtool.util.data.ArrayExtractor.extractRow;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FolderCsvComparator.compareAndGenerateCsv;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteDirectory.deleteDirectory;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.ExtractMainImage.extractMainImage;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FilePrefixMove.filePrefixMove;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy.copyFolderWithList;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.OrganizeFiles.moveNumberForward;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.OrganizeFiles.organizeFileNumbers;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.TakeMainFromDatabase.takeMainFromDatabase;
import static com.github.beibeikun.imagewarehousemanagementtool.util.file.DownloadFromDatabase.downloadFromDatabase;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.mainpageUtils.*;

public class mainpageUtilsWithTasks
{
    private static void executeTaskWithTiming(Callable<Void> task, String taskName, boolean printout) {
        AsyncTaskExecutor.executeInBackground(() -> {
            if (printout == true)
            {
                Instant start = Instant.now();
                task.call();
                Instant end = Instant.now();
                JOptionPane.showMessageDialog(null, taskName + " 完成，本次耗时：" + GetTimeConsuming.getTimeConsuming(start, end) + "秒");
            }
            else
            {
                task.call();
            }
            return null;
        });
    }

    public static void renameWithTasks(JCheckBox CheckBox_addfromdatabase, JComboBox selectdatabase, JLabel cameradatabasepath, JLabel phonedatabasepath, JCheckBox CheckBox_classification, JCheckBox suffixCheckBox, JComboBox comboBox1, JCheckBox mainpageOrganizeAndSortCheckBox, JComboBox imgsizecomboBox, JLabel targetFolderPath, JLabel renameCsvPath)
    {
        Callable<Void> task = () -> {
            rename(CheckBox_addfromdatabase, selectdatabase, cameradatabasepath, phonedatabasepath, CheckBox_classification, suffixCheckBox, comboBox1, mainpageOrganizeAndSortCheckBox, imgsizecomboBox, targetFolderPath, renameCsvPath);
            return null;
        };
        executeTaskWithTiming(task, "重命名任务",true);
    }

    public static void uploadToWarehouseWithTasks(JLabel sourceFolderPath, JLabel cameradatabasepath, JLabel phonedatabasepath, JComboBox uploadDatabaseComboBox, JComboBox uploadSizeComboBox, JCheckBox uploadReplaceCheckBox, JCheckBox uploadDeleteCheckBox)
    {
        Callable<Void> task = () -> {
            uploadToWarehouse(sourceFolderPath,cameradatabasepath,phonedatabasepath,uploadDatabaseComboBox,uploadSizeComboBox,uploadReplaceCheckBox,uploadDeleteCheckBox);
            return null;
        };
        executeTaskWithTiming(task, "上传备份任务",false);
    }
    public static void csvToPdfWithTasks(JLabel targetFolderPath, JComboBox pdfComboBox, JTextField pdfStaffTextField, JLabel pdfCsvPath)
    {
        Callable<Void> task = () -> {
            csvToPdf(targetFolderPath, pdfComboBox, pdfStaffTextField, pdfCsvPath);
            return null;
        };
        executeTaskWithTiming(task, "生成pdf任务",true);
    }
    public static void takeMainFromDatabaseWithTasks(JLabel checkCsvPath, JLabel cameraWarehouseAddressText, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            takeMainFromDatabase(checkCsvPath.getText(), cameraWarehouseAddressText.getText(), targetFolderPath.getText());
            return null;
        };
        executeTaskWithTiming(task, "从数据库提取主图任务",true);
    }
    public static void extractMainImageWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, JCheckBox useCsvCheck, JLabel checkCsvPath)
    {
        Callable<Void> task = () -> {
            boolean x = false;
            if (useCsvCheck.isSelected())
            {
                x = true;
            }
            extractMainImage(sourceFolderPath.getText(), targetFolderPath.getText(), x, checkCsvPath.getText());
            return null;
        };
        executeTaskWithTiming(task, "提取主图任务",true);
    }
    public static void changeAllSuffixWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, int mode)
    {
        Callable<Void> task = () -> {
            changeAllSuffix(sourceFolderPath.getText(), targetFolderPath.getText(), mode);
            return null;
        };
        executeTaskWithTiming(task, "更换后缀名任务",true);
    }
    public static void onlyCompressFilesWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, int imgsize)
    {
        Callable<Void> task = () -> {
            onlyCompressFiles(sourceFolderPath.getText(), targetFolderPath.getText(), imgsize);
            return null;
        };
        executeTaskWithTiming(task, "压缩任务",true);
    }
    public static void compareAndGenerateCsvWithTasks(JLabel sourceFolderPath, JLabel checkCsvPath, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            compareAndGenerateCsv(sourceFolderPath.getText(), checkCsvPath.getText(), targetFolderPath.getText());
            return null;
        };
        executeTaskWithTiming(task, "csv校对任务",true);
    }

    public static void organizeAndSortWithTasks(JLabel sourceFolderPath, JLabel targetFolderPath, JCheckBox organizeAndSortCheckBox)
    {
        Callable<Void> task = () ->
        {
            if (organizeAndSortCheckBox.isSelected()) {
                String middleFolderPath = targetFolderPath.getText()+"middleFolderPath";
                organizeFileNumbers(sourceFolderPath.getText(), middleFolderPath, true);
                moveNumberForward(middleFolderPath,targetFolderPath.getText(),true);
                deleteDirectory(new File(middleFolderPath));
            }
            else {
                String targetFolderPathInSort = CreateFolder.createFolderWithTime(targetFolderPath.getText());
                organizeFileNumbers(sourceFolderPath.getText(), targetFolderPathInSort, true);
            }
            return null;
        };
        executeTaskWithTiming(task, "整理排序文件任务", true);
    }
    public static void copyFolderWithListWithTasks(JLabel sourceFolderPath, JLabel checkCsvPath, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            List<String> readyToCopyNameList = Arrays.asList(extractRow(ReadCsvFile.csvToArray(checkCsvPath.getText()), 0));
            copyFolderWithList(sourceFolderPath.getText(), CreateFolder.createFolderWithTime(targetFolderPath.getText()), readyToCopyNameList);
            return null;
        };
        executeTaskWithTiming(task, "从源文件夹提取图片任务",true);
    }
    public static void filePrefixMoveWithTasks(JLabel sourceFolderPath)
    {
        Callable<Void> task = () -> {
            filePrefixMove(sourceFolderPath.getText(), "-");
            return null;
        };
        executeTaskWithTiming(task, "按文件夹分类任务",true);
    }
    public static void downloadFromDatabaseWithTasks(String warehouseAddressText, String fileName, JLabel targetFolderPath)
    {
        Callable<Void> task = () -> {
            downloadFromDatabase(warehouseAddressText, fileName, targetFolderPath.getText());
            return null;
        };
        executeTaskWithTiming(task, "从数据库提取任务",true);
    }
}
