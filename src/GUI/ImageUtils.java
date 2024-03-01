package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static Module.Others.SystemPrintOut.systemPrintOut;

public class ImageUtils
{
    /**
     * 打开并显示图像及相关信息的GUI窗口。
     *
     * @param imagePath 图像文件的路径
     */
    public static void openImage(String imagePath)
    {
        try
        {
            // 读取图像文件
            File imageFile = new File(imagePath);
            if (! imageFile.exists())
            {
                JOptionPane.showMessageDialog(null, "文件不存在", "错误", JOptionPane.ERROR_MESSAGE);
                systemPrintOut("Image file does not exist", 2, 0);
                return;
            }
            Image image = ImageIO.read(imageFile);

            // 计算缩放后的图像尺寸
            int maxDimension = Math.max(image.getWidth(null), image.getHeight(null));
            int scaledWidth = image.getWidth(null);
            int scaledHeight = image.getHeight(null);
            if (maxDimension > 600)
            {
                double scale = 600.0 / maxDimension;
                scaledWidth = (int) (scale * image.getWidth(null));
                scaledHeight = (int) (scale * image.getHeight(null));
            }

            // 创建左侧面板用于显示图像
            int finalScaledWidth = scaledWidth;
            int finalScaledHeight = scaledHeight;
            JPanel imagePanel = new JPanel()
            {
                @Override
                protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, finalScaledWidth, finalScaledHeight, null);
                }
            };
            imagePanel.setPreferredSize(new Dimension(scaledWidth, scaledHeight));

            // 创建右侧面板用于显示信息
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BorderLayout());
            JTextArea infoTextArea = new JTextArea("This is some information about the image.");
            infoTextArea.setEditable(false);
            infoPanel.add(new JScrollPane(infoTextArea), BorderLayout.CENTER);

            // 创建分割窗格，左侧显示图像，右侧显示信息
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, infoPanel);
            splitPane.setResizeWeight(0.5); // 设置左右两侧面板的宽度比例

            // 创建窗口
            JFrame frame = new JFrame();
            frame.setTitle("Image Info Viewer");
            frame.setSize(1200, 600); // 设置窗口大小为左右两个面板的宽度之和
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(splitPane);
            frame.setVisible(true);
            systemPrintOut("Open:" + imagePath, 1, 0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
