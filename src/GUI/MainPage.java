package GUI;

import algorithm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainPage extends JFrame implements ActionListener
{

    String filepath = "";
    String imgpath = "";
    String copypath = "";
    VersionNumber versionnumber = new VersionNumber();//获取版本号
    XuanMulu getfilepath = new XuanMulu();//获取指定excel文件路径
    int c1check = 0, c2check = 0;

    private final JLabel jtitle, jl1, build;

    private final JButton findfile, imgfilepath, copytopath, filecheck, renamestart;
    private final JLabel nowfilepath_excel, nowfilepath_img, nowfilepath_copy, checkreturn;

    private final JCheckBox c1, c2, c3, c4;

    private final JTextArea test;
    private final JScrollPane test1;

    public MainPage()
    {
        BufferedReader br;
        String line;
        int ii = 0;

        setLayout(null);
        jtitle = new JLabel("文件管理工具", JLabel.CENTER);
        jtitle.setFont(new java.awt.Font("汉仪尚巍手书W", Font.BOLD, 25));
        jtitle.setForeground(new Color(255, 109, 0));

        jl1 = new JLabel("用户名:");

        findfile = new JButton("cvs文件");
        imgfilepath = new JButton("源文件夹");
        copytopath = new JButton("目标文件夹");
        filecheck = new JButton("检查");
        nowfilepath_excel = new JLabel("当前所选路径:" + filepath);
        nowfilepath_img = new JLabel("当前所选路径:" + imgpath);
        nowfilepath_copy = new JLabel("当前所选路径:" + copypath);
        checkreturn = new JLabel("");
        c1 = new JCheckBox("使用3位lot号");
        c2 = new JCheckBox("添加Lot前缀");
        c3 = new JCheckBox("完成后分类");
        c4 = new JCheckBox("提取文件夹");

        renamestart = new JButton("开始重命名");
        renamestart.setEnabled(false);

        build = new JLabel(versionnumber.VersionNumber());
        build.setFont(new java.awt.Font("黑体", Font.BOLD, 10));
        build.setForeground(Color.lightGray);

        test = new JTextArea();
        test1 = new JScrollPane();

        this.add(jtitle);
        this.add(jl1);
        this.add(build);

        this.add(findfile);
        this.add(imgfilepath);
        this.add(copytopath);
        this.add(filecheck);
        this.add(nowfilepath_excel);
        this.add(nowfilepath_img);
        this.add(nowfilepath_copy);
        this.add(checkreturn);

        this.add(c1);
        this.add(c2);
        this.add(c3);
        this.add(c4);

        c4.setEnabled(false);//这玩意儿还没做好

        this.add(renamestart);

        this.add(test1);

        findfile.addActionListener(this);
        imgfilepath.addActionListener(this);
        copytopath.addActionListener(this);
        filecheck.addActionListener(this);
        renamestart.addActionListener(this);

        jtitle.setBounds(30, 20, 530, 50);
        build.setBounds(30, 640, 200, 10);

        findfile.setBounds(30, 90, 150, 30);
        imgfilepath.setBounds(30, 140, 150, 30);
        copytopath.setBounds(30, 190, 150, 30);
        filecheck.setBounds(30, 240, 150, 30);
        nowfilepath_excel.setBounds(210, 90, 600, 30);
        nowfilepath_img.setBounds(210, 140, 600, 30);
        nowfilepath_copy.setBounds(210, 190, 600, 30);
        checkreturn.setBounds(210, 240, 600, 30);

        c1.setBounds(30, 290, 100, 20);
        c2.setBounds(160, 290, 100, 20);
        c3.setBounds(290, 290, 100, 20);
        c4.setBounds(420, 290, 100, 20);

        renamestart.setBounds(30, 330, 150, 30);

        test1.setBounds(30, 380, 500, 200);


        test1.setViewportView(test);
        System.setOut(new PrintStream(new OutputStream()
        {
            @Override
            public void write(int b)
            {
                test.append(String.valueOf((char) b));
            }
        }));


        Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");
        this.setIconImage(icon);

        this.setSize(605, 710);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //获取屏幕大小
        this.setLocation((width - 605) / 2, (height - 710) / 2);
        //使窗体显示在屏幕中央
        this.setResizable(false); //禁止改变大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("寄寄寄寄摆摆摆摆摆摆");

        System.out.println("current version: " + versionnumber.VersionNumber());
        System.out.println("Github: " + versionnumber.Github());
        System.out.println("Load previous profile");
        File file2 = new File("savedpath.bbk");
        if (file2.exists())
        {
            try
            {
                br = new BufferedReader(new FileReader("savedpath.bbk"));
                while ((line = br.readLine()) != null)
                {
                    if (ii == 0)
                    {
                        filepath = line;
                        nowfilepath_excel.setText("当前所选文件:" + filepath);
                        System.out.println("--Directory selected-- " + filepath); //此处为测试输出点
                    }
                    else if (ii == 1)
                    {
                        imgpath = line;
                        nowfilepath_img.setText("当前所选文件:" + imgpath);
                        System.out.println("--Directory selected-- " + imgpath); //此处为测试输出点
                    }
                    else
                    {
                        copypath = line;
                        nowfilepath_copy.setText("当前所选文件:" + copypath);
                        System.out.println("--Directory selected-- " + copypath); //此处为测试输出点
                    }

                    ii++;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Previous profile not found");
        }


    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == findfile)
        {
            filepath = getfilepath.XuanMulu(1);
            nowfilepath_excel.setText("当前所选文件:" + filepath);
            //System.out.println(2);
            renamestart.setEnabled(false);
        }
        if (source == imgfilepath)
        {
            imgpath = getfilepath.XuanMulu(2);
            nowfilepath_img.setText("当前所选文件:" + imgpath);
            //System.out.println(1);
            renamestart.setEnabled(false);
        }
        if (source == copytopath)
        {
            copypath = getfilepath.XuanMulu(2);
            nowfilepath_copy.setText("当前所选文件:" + copypath);
            //System.out.println(1);
            renamestart.setEnabled(false);
        }
        if (source == filecheck)
        {
            FilenameCheck filenameCheck = new FilenameCheck();
            int passornot = filenameCheck.namecheck(filepath, imgpath, copypath);
            if (passornot == 1)
            {
                checkreturn.setText("检查通过");
                renamestart.setEnabled(true);
            }
            else
            {
                renamestart.setEnabled(false);
                if (passornot == 2)
                {
                    checkreturn.setText("请选择文件路径");
                }
                else if (passornot == 3)
                {
                    checkreturn.setText("请使用英文文件路径");
                }
                else if (passornot == 41)
                {
                    checkreturn.setText("csv文件不存在");
                }
                else if (passornot == 42)
                {
                    checkreturn.setText("源文件夹不存在");
                }
                else if (passornot == 43)
                {
                    checkreturn.setText("目标文件夹不存在");
                }
                else if (passornot == 5)
                {
                    checkreturn.setText("目标文件夹非空");
                }
            }
        }
        if (source == renamestart)
        {
            if (c1.isSelected())
            {
                c1check = 1;
            }
            else
            {
                c1check = 0;
            }
            if (c2.isSelected())
            {
                c2check = 1;
            }
            else
            {
                c2check = 0;
            }

            CopyFile copy = new CopyFile();
            copy.copyfile(filepath, imgpath, copypath);//复制文件

            Rename rename = new Rename();
            rename.renamefile(filepath, copypath, c1check, c2check);

            DeleteFailed delete = new DeleteFailed();
            delete.deletefiles(copypath);
            if (c3.isSelected()) //执行分类
            {
                Classification classification = new Classification();
                classification.classification(copypath);
            }

        }
    }

    public static void main(String[] args)
    {
        new MainPage();
    }
}
