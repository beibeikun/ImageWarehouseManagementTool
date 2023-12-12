package Module.DataOperations;

public class GetPrefix {
    /**
     * 提取文件名中的前缀。
     *
     * @param fileName 文件名
     * @return 前缀字符串
     */
    public static String getPrefix(String fileName) {
        int startIndex = fileName.indexOf("(");
        int endIndex = fileName.indexOf(")");

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return fileName.substring(0, startIndex).trim();
        } else {
            int pointIndex = fileName.indexOf(".");
            return fileName.substring(0, pointIndex).trim();
        }
    }
}
