package UIPAGE;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connecttodatabase {
    JPanel panel1;
    private JTextField address_textField;
    private JTextField username_textField;
    private JPasswordField password_textField;
    private JButton ConnectButton;
    private JLabel databaseaddress;
    private JLabel username;
    private JLabel password;
    private JLabel connect;


    public Connecttodatabase()
    {

        ConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://"+address_textField.getText(), username_textField.getText(), password_textField.getText());
                    connect.setText("连接成功");
                    File savepath = new File("databasesettings.bbk");
                    try
                    {
                        //写入的txt文档的路径
                        PrintWriter pw = new PrintWriter(savepath);
                        //写入的内容
                        String c =address_textField.getText() + "\r\n" + username_textField.getText() + "\r\n" + password_textField.getText();
                        pw.write(c);
                        pw.flush();
                        pw.close();
                    }
                    catch (Exception ea)
                    {
                        ea.printStackTrace();
                    }
                    // 连接成功后的操作
                } catch (SQLException ea) {
                    ea.printStackTrace();
                    connect.setText("连接失败");
                    // 连接失败时的处理
                }
            }
        });
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
