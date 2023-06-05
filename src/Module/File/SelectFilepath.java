package Module.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*--------------------------------------------
实现了打开文件目录对话框并根据用户选择返回相应的路径值的功能。

代码中包含一个方法Selectfilepath，它接受一个整数类型的参数type，用于指定打开文件目录对话框的类型。

在方法内部，首先创建一个JFileChooser对象chooser，它是Swing库中提供的一个文件选择对话框组件。

根据传入的type参数，设置文件选择对话框的模式。如果type为2，表示选择文件夹模式，通过setFileSelectionMode()方法将文件选择模式设置为仅选择文件夹。如果type为1，表示选择文件模式，通过FileNameExtensionFilter设置文件过滤器，仅显示指定的文件类型（Excel文件和CSV文件）。

接下来，调用showOpenDialog()方法显示文件选择对话框，并将返回值存储在returnVal变量中，表示用户的选择。

如果用户选择了文件或文件夹并点击了"确认"按钮（JFileChooser.APPROVE_OPTION），则通过getSelectedFile().getAbsolutePath()方法获取选择的文件或文件夹的绝对路径，并将其存储在filepath变量中。

最后，将filepath返回作为方法的结果。

通过调用这个方法并传入相应的type参数，可以打开文件目录对话框并获取用户选择的文件或文件夹的路径。
--------------------------------------------*/

public class SelectFilepath
{
    //打开文件目录对话框
    public String Selectfilepath(int type)
    {
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
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            filepath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("--Directory selected-- " + filepath); //此处为测试输出点
        }
        return filepath;
    }
}
