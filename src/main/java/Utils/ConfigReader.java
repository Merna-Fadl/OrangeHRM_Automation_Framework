package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private  static Properties properties;
    // حصالة لكل Thread (متصفح) لوحده
    private static ThreadLocal<Double> expectedTotal = ThreadLocal.withInitial(() -> 0.0);

    // ميثود لإضافة سعر
    public static void addToTotal(double price) {
        expectedTotal.set(expectedTotal.get() + price);
    }

    // ميثود لجلب المجموع
    public static double getTotal() {
        return expectedTotal.get();
    }

    // ميثود لتصفير العداد (مهمة جداً بين التستات)
    public static void clearTotal() {
        expectedTotal.set(0.0);
    }
    // to open file of config.properties

    static {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found");
        }
    }
    public  static  String getProperty(String key){
        return  properties.getProperty(key);
    }
        }


