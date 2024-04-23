package com.github.beibeikun.imagewarehousemanagementtool.ui;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.WriteToProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionForm extends JFrame
{
    private final JTextField addressField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private JToggleButton showPasswordToggle;

    public DatabaseConnectionForm(Mainpage.DataCallback callback)
    {
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
        Properties properties = new Properties();
        SystemChecker system = new SystemChecker();//获取系统类型
        try (FileInputStream fis = new FileInputStream("properties" + system.identifySystem_String() + "settings.properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            properties.load(reader);
            addressField.setText(properties.getProperty("databaseaddress"));
            usernameField.setText(properties.getProperty("databaseusername"));
            passwordField.setText(properties.getProperty("databasepassword"));
            // 读取属性值...
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        confirmButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String address = addressField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                try
                {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://" + address, username, password);
                    try
                    {

                        String[][] filenamelist = new String[2][10];
                        filenamelist[0][0] = "databaseaddress";
                        filenamelist[1][0] = address;
                        filenamelist[0][1] = "databaseusername";
                        filenamelist[1][1] = username;
                        filenamelist[0][2] = "databasepassword";
                        filenamelist[1][2] = password;
                        WriteToProperties.writeToProperties("settings", filenamelist);

                        // 调用主窗口的回调方法，将数据传递回去
                        callback.onDataEntered(address, username, password);
                        JOptionPane.showMessageDialog(null, "数据库连接测试通过");
                        dispose(); // 关闭当前窗口
                    }
                    catch (Exception ea)
                    {
                        ea.printStackTrace();
                    }
                    // 连接成功后的操作
                }
                catch (SQLException ea)
                {
                    ea.printStackTrace();
                    JOptionPane.showMessageDialog(null, "数据库连接测试失败", "连接错误", JOptionPane.WARNING_MESSAGE);
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
