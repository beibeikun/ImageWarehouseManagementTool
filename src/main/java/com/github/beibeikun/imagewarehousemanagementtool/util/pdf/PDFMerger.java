package com.github.beibeikun.imagewarehousemanagementtool.util.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;

public class PDFMerger {

    /**
     * 合并多个PDF文件并删除原始文件。
     *
     * @param pdfFiles 要合并的PDF文件路径数组。
     * @param mergedPdfPath 合并后的PDF文件路径。
     */
    public static void mergeAndDeleteOriginals(String[] pdfFiles, String mergedPdfPath) {
        // 创建PDF合并文档
        try (PdfDocument mergedPdf = new PdfDocument(new PdfWriter(mergedPdfPath))) {
            // 遍历所有PDF文件路径
            for (String filePath : pdfFiles) {
                // 创建读取器读取每个PDF文件
                PdfDocument pdfToAdd = new PdfDocument(new PdfReader(filePath));
                // 将每个PDF的所有页面添加到合并文档中
                pdfToAdd.copyPagesTo(1, pdfToAdd.getNumberOfPages(), mergedPdf);
                // 关闭当前文档
                pdfToAdd.close();
                // 删除原始PDF文件
                new File(filePath).delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
