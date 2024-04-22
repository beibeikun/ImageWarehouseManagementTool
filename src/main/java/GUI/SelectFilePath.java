package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件路径选择工具类，用于打开文件选择对话框并返回所选文件的路径。
 */
public class SelectFilePath
{
    /**
     * 打开文件选择对话框并返回所选文件的路径。
     *
     * @param type 选择对话框的类型，1表示选择Excel文件，2表示选择文件夹
     * @return 所选文件的路径
     */
    public String selectFilePath(int type, String nowpath)
    {
        String filePath = "";
        JFileChooser chooser = new JFileChooser(nowpath);
        if (type == 2)
        {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        if (type == 1)
        {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xlsx", "xls", "csv");
            chooser.setFileFilter(filter);
        }
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            filePath = chooser.getSelectedFile().getAbsolutePath();
            systemPrintOut("Directory selected: " + filePath, 1, 1);
        }
        return filePath;
    }
}
