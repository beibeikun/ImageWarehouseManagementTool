package Module.File;

import Module.IdentifySystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/*--------------------------------------------
将参数写入对应的配置文件中。

代码中包含一个方法writetoproperties，它接受两个参数filename和writeinlist，用于指定要写入的配置文件名和参数列表。

在方法内部，首先创建一个Properties对象properties，用于表示配置文件的属性集合。

接下来，通过FileInputStream和InputStreamReader从配置文件中加载已有的属性值，以便后续的修改和写入操作。加载时使用StandardCharsets.UTF_8编码来读取配置文件，确保正确处理中文字符。

然后，使用一个计数器i遍历参数列表writeinlist，将参数的键值对写入properties对象。

在完成参数的写入后，通过FileOutputStream将properties对象以XML格式写入到配置文件中。写入时使用StandardCharsets.UTF_8编码，确保正确处理中文字符。

最后，输出一条提示信息表示配置文件已成功写入。
--------------------------------------------*/

public class WriteToProperties {
    public void writetoproperties(String filename,String[][] writeinlist)
    {
        IdentifySystem system =new IdentifySystem();
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("properties"+system.identifysystem_String()+filename+".properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(fis);
            // 读取属性值...
        } catch (IOException ea) {
            ea.printStackTrace();
        }
        int i = 0;//计数器
        while(writeinlist[0][i]!=null)
        {
            properties.setProperty(writeinlist[0][i],writeinlist[1][i]);
            i++;
        }

        try (FileOutputStream fos = new FileOutputStream("properties"+system.identifysystem_String()+filename+".properties")) {
            // 将属性以XML格式写入文件
            properties.store(fos, null);
            System.out.println("Properties written to XML file.");
        } catch (IOException ea) {
            ea.printStackTrace();
        }

    }
}
