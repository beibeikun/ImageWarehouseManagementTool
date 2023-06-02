package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test1 {
    private JButton button1;
    private JPanel panel1;
public test1() {
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            test2 test2 =new test2();
            test2.setVisible(true);
        }
    });
}

    public static void main(String[] args) {
        JFrame frame = new JFrame("test1");
        frame.setContentPane(new test1().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

