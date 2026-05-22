package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private  static Properties properties;
    private static ThreadLocal<Double> expectedTotal = ThreadLocal.withInitial(() -> 0.0);

    public static void addToTotal(double price) {
        expectedTotal.set(expectedTotal.get() + price);
    }

    public static double getTotal() {
        return expectedTotal.get();
    }

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


