/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.base;

import jakarta.servlet.http.Part;
import java.util.Random;

/**
 *
 * @author sonng
 */
public class FileHelper {
     // Extracts file name from HTTP header content-disposition
    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public static String generateRandomFileName() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomInt = random.nextInt(1000); // Generate a random number between 0 and 999
        return "file_" + timestamp + "_" + randomInt;
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
}
