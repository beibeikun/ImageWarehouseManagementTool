package UIPAGE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionForm extends JFrame {
    private JTextField addressField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JToggleButton showPasswordToggle;

    public DatabaseConnectionForm(Mainpage.DataCallback callback) {
        setTitle("数据库连接");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置关闭操作为仅关闭当前窗口

        // 创建面板，使用GridBagLayout布局管理器
        JPanel panel = new JPanel(new GridBagLayout());

        // 创建GridBagConstraints对象来指定组件的位置和大小
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 列索引
        gbc.gridy = 0; // 行索引
        gbc.insets = new Insets(5, 5, 5, 5); // 组件之间的间距

        // 创建标签和文本框
        JLabel addressLabel = new JLabel("地址:");
        addressField = new JTextField(20);

        // 添加地址标签和文本框到面板
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);

        // 添加用户名标签和文本框到面板
        gbc.gridx = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);

        // 添加密码标签和文本框到面板
        gbc.gridx = 0;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("确认");
        BufferedReader br;
        String line;
        int ii = 0;
        File loaddatabasesettings = new File("databasesettings.bbk");
        if (loaddatabasesettings.exists())
        {
            try
            {
                br = new BufferedReader(new FileReader("databasesettings.bbk"));
                String filepath = "";
                while ((line = br.readLine()) != null)
                {
                    if (ii == 0)
                    {
                        filepath = line;
                        addressField.setText(filepath);
                    }
                    else if (ii == 1)
                    {
                        filepath = line;
                        usernameField.setText(filepath);
                    }
                    else if (ii == 2)
                    {
                        filepath = line;
                        passwordField.setText(filepath);
                    }
                    ii++;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("存在");
        }
        else
        {
            System.out.println("不存在");
        }
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 在这里执行数据库连接操作
                // 这里只是一个示例，你可以根据需要进行自定义

                System.out.println("地址: " + address);
                System.out.println("用户名: " + username);
                System.out.println("密码: " + password);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://"+address, username, password);
                    File savepath = new File("databasesettings.bbk");
                    try
                    {
                        //写入的txt文档的路径
                        PrintWriter pw = new PrintWriter(savepath);
                        //写入的内容
                        String c =address + "\r\n" + username + "\r\n" + password;
                        pw.write(c);
                        pw.flush();
                        pw.close();
                        // 调用主窗口的回调方法，将数据传递回去
                        callback.onDataEntered(address, username, password);
                        JOptionPane.showMessageDialog(null,"数据库连接测试通过");
                        dispose(); // 关闭当前窗口
                    }
                    catch (Exception ea)
                    {
                        ea.printStackTrace();
                    }
                    // 连接成功后的操作
                } catch (SQLException ea) {
                    ea.printStackTrace();
                    JOptionPane.showMessageDialog(null,"数据库连接测试失败","连接错误",JOptionPane.WARNING_MESSAGE);
                    // 连接失败时的处理
                }

            }
        });

        // 添加确认按钮到面板
        panel.add(confirmButton, gbc);

        // 将面板添加到窗体
        add(panel);

        pack();
        setLocationRelativeTo(null); // 居中显示窗体
    }

}
