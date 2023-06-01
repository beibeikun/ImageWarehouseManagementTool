package UIPAGE;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Connecttodatabase {
    JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton 连接Button;


    public Connecttodatabase()
    {

    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("Connecttodatabase");
        frame.setContentPane(new Connecttodatabase().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int UIwidth=300,UIheight=200;
        //设置大小
        frame.setSize(UIwidth,UIheight);
        int Monitor_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int Monitor_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //获取屏幕大小
        frame.setLocation((Monitor_width - UIwidth) / 2, (Monitor_height - UIheight) / 2);
        //使窗体显示在屏幕中央
        frame.setVisible(true);
        frame.setTitle("连接到数据库");
        frame.setResizable(false); //禁止改变大小
    }
}
