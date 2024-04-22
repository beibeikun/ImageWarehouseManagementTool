import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class PdfFormFiller
{
    public static void main(String[] args) throws IOException
    {
        String src = "/Users/bbk/photographs/各种模版/test1.pdf";  // 源PDF文件路径
        String destPath = "/Users/bbk/photographs/各种模版/";  // 填写完表单后的PDF文件路径
        String csv = "/Users/bbk/photographs/各种模版/test.csv";
        String staff = "陳";

        batchFillPdfForms(src,destPath,staff,CsvReaderToList.readCsvAndProcess(csv));


    }
    /**
     * 批量处理CSV数据，填充多个PDF表单。
     *
     * @param pdfTemplatePath PDF模板文件的路径。
     * @param outputDirectory PDF输出文件的目录。
     * @param operator 操作员姓名。
     * @param dataGroups 所有数据分组，每个分组是一个表单的数据。
     * @throws IOException 如果读写文件发生错误。
     */
    public static void batchFillPdfForms(String pdfTemplatePath, String outputDirectory, String operator, List<List<String[][]>> dataGroups) throws IOException {
        for (int i = 0; i < dataGroups.size(); i++) {
            fillPdfForm(pdfTemplatePath, outputDirectory, operator, dataGroups.get(i));
        }
    }

    /**
     * 填充一个PDF表单，根据给定的数据。
     *
     * @param pdfTemplatePath PDF模板文件路径。
     * @param outputDirectory 输出文件目录。
     * @param operator 操作员姓名。
     * @param fieldData 字段数据，每个数组对应表单的一行。
     * @throws IOException 如果读写PDF时发生错误。
     */
    private static void fillPdfForm(String pdfTemplatePath, String outputDirectory, String operator, List<String[][]> fieldData) throws IOException {
        String JBId = fieldData.get(0)[0][1];
        for (int formIndex = 0; formIndex < fieldData.size(); formIndex++) {
            String outputPath = formatOutputPath(outputDirectory, formIndex, fieldData.size(), JBId);
            try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfTemplatePath), new PdfWriter(outputPath))) {
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
                populateFormFields(form, fieldData.get(formIndex));

                // 设置通用的表单字段
                form.getField("membername").setValue(extractMemberName(fieldData.get(formIndex)));
                form.getField("staff").setValue(operator);
                form.getField("date").setValue(currentDateString());
                form.getField("page").setValue((formIndex + 1) + "/" + fieldData.size());
            } // 自动关闭PdfDocument资源
        }
        if (fieldData.size() > 1)
        {
            String[] pdfName = new String[fieldData.size()];
            for (int i=0; i <fieldData.size(); i++)
            {
                pdfName[i] = outputDirectory + JBId + "-" + (i + 1) + ".pdf";
            }
            PDFMerger.mergeAndDeleteOriginals(pdfName,outputDirectory+ JBId+ ".pdf");
            System.out.println(Arrays.toString(pdfName));

        }
    }

    /**
     * 填充PDF表单中的每个字段。
     *
     * @param form PDF表单对象。
     * @param data 当前CSV行的数据数组。
     */
    private static void populateFormFields(PdfAcroForm form, String[][] data) {
        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            setFormFieldValue(form, "JB_" + (rowIndex + 1), data[rowIndex][2]);
            setFormFieldValue(form, "content_" + (rowIndex + 1), buildContentText(data[rowIndex]));
            setFormFieldValue(form, "message_" + (rowIndex + 1), data[rowIndex][11]);
            setFormFieldValue(form, "price_" + (rowIndex + 1), data[rowIndex][10]);
        }
    }

    /**
     * 为指定的PDF表单字段设置值。
     *
     * @param form PDF表单对象。
     * @param fieldName 字段名。
     * @param value 要设置的值。
     */
    private static void setFormFieldValue(PdfAcroForm form, String fieldName, String value) {
        PdfFormField field = form.getField(fieldName);
        if (field != null) {
            field.setValue(value);
        }
    }

    /**
     * 根据提供的参数构建输出PDF文件的路径。
     *
     * @param outputDirectory 输出文件目录。
     * @param formIndex 当前表单的索引。
     * @param totalForms 总表单数。
     * @param jobId 工作编号，用于文件命名。
     * @return 完整的输出文件路径。
     */
    private static String formatOutputPath(String outputDirectory, int formIndex, int totalForms, String jobId) {
        if (totalForms == 1) {
            return outputDirectory + jobId + ".pdf";
        } else {
            return outputDirectory + jobId + "-" + (formIndex + 1) + ".pdf";
        }
    }

    /**
     * 从CSV数据中提取成员名称。
     *
     * @param data CSV数据数组。
     * @return 成员名称字符串。
     */
    private static String extractMemberName(String[][] data) {
        return data[0][0];
    }

    /**
     * 获取当前日期，格式化为字符串。
     *
     * @return 当前日期的字符串表示，格式为"yyyy/MM/dd"。
     */
    private static String currentDateString() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }

    /**
     * 根据提供的单行CSV数据构建内容文本。
     *
     * @param content 单行CSV数据。
     * @return 构建好的内容文本字符串。
     */
    private static String buildContentText(String[] content) {
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 3; i <= 9; i++) {
            if (!content[i].isEmpty()) {
                contentBuilder.append(content[i]).append("  ");
            }
        }
        return contentBuilder.toString().trim();
    }
}