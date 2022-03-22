package GUI;

import javax.swing.*;
import javax.swing.filechooser.*;
import javafx.stage.DirectoryChooser;

public class XuanMulu {
    //打开文件目录对话框
    public String XuanMulu(int type) {
        // TODO Auto-generated method stub
        String filepath = "";
        JFileChooser chooser = new JFileChooser();
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
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            filepath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("--Directory selected-- "+filepath); //此处为测试输出点
        }
        return filepath;
    }
}