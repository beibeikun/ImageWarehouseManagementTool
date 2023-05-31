package UIPAGE;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class MainpageUI {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton 检查正确性Button;
    private JButton 开始重命名Button;
    private JCheckBox 使用三位lot号CheckBox;
    private JCheckBox 添加Lot前缀CheckBox;
    private JCheckBox 完成后按文件夹分类CheckBox;
    private JCheckBox 尝试从库中提取缺少的拍品CheckBox;
    private JList list1;
    private JList list2;
    private JList list3;
    private JButton 选择Button1;
    private JButton 选择Button2;
    private JButton 选择Button;
    private JCheckBox 替换已存在的图片CheckBox;
    private JButton 开始添加Button;
    private JButton 开始移除Button;
    private JTextField textField1;
    private JButton 查询Button;
    private JCheckBox checkBox1;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame("MainpageUI");
        frame.setContentPane(new MainpageUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,350);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //获取屏幕大小
        frame.setLocation((width - 605) / 2, (height - 710) / 2);
        //使窗体显示在屏幕中央
        frame.setVisible(true);
    }
}
