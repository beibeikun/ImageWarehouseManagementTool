package Module.File;

import java.io.File;
//TODO:需要优化
public class FileExtensionConverter {
    public static void convertFileExtensionsToUppercase(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path: " + directoryPath);
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Error accessing files in directory: " + directoryPath);
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String extension = getFileExtension(fileName);

                if (!extension.equals(extension.toUpperCase())) {
                    String newFileName = fileName.substring(0, fileName.lastIndexOf('.') + 1) + extension.toUpperCase();
                    File newFile = new File(directory, newFileName);
                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed file: " + fileName + " -> " + newFileName);
                    } else {
                        System.out.println("Failed to rename file: " + fileName);
                    }
                }
            }
        }
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public static void main(String[] args) {
        String directoryPath = "/path/to/directory";
        convertFileExtensionsToUppercase(directoryPath);
    }
}
