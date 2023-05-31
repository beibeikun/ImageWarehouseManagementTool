package UIPAGE;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class MainpageUI {
    private JPanel panel1;
    private JComboBox comboBox1;
    private JTabbedPane tabbedPane1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JButton button1;
    private JButton button2;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame("MainpageUI");
        frame.setContentPane(new MainpageUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setVisible(true);
    }
}
