package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// đọc file properties.env
public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            // Dùng class loader để đọc file từ resources(src/main/resources/properties.env)
            var inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("properties.env");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("Không tìm thấy file properties.env trong resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}