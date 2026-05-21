package Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    // ميثود مخصصة للـ DataProvider (بتقرأ الملف كـ Array)
    public static Object[][] getJsonData(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            // قراءة الملف وتحويله لـ JSONArray
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath, StandardCharsets.UTF_8));

            // تجهيز مصفوفة الـ Object[][] (عدد الصفوف = عدد الموظفين، والأعمدة = 6 بيانات)
            Object[][] data = new Object[jsonArray.size()][6];

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject person = (JSONObject) jsonArray.get(i);
                data[i][0] = person.get("firstName").toString();
                data[i][1] = person.get("middleName").toString();
                data[i][2] = person.get("lastName").toString();
                data[i][3] = person.get("employeeId").toString();
                data[i][4] = person.get("username").toString();
                data[i][5] = person.get("password").toString();
            }
            return data;
        } catch (Exception e) {
            System.err.println("CRITICAL: Could not load JSON file at: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    // الميثود  (بتقرأ قيمة واحدة بناءً على Key)
    public static String getTestData(String key, String filePath) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filePath, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) obj;
            Object value = jsonObject.get(key);
            return (value != null) ? value.toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Object[][] getRecruitmentJsonData(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath, StandardCharsets.UTF_8));

            // المصفوفة هنا 6 أعمدة برضه بس ببيانات مختلفة
            Object[][] data = new Object[jsonArray.size()][6];

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject person = (JSONObject) jsonArray.get(i);

                data[i][0] = person.get("firstName").toString();
                data[i][1] = person.get("middleName").toString();
                data[i][2] = person.get("lastName").toString();
                data[i][3] = person.get("email").toString();      // القيمة الـ 4
                data[i][4] = person.get("vacancy").toString();    // القيمة الـ 5
                data[i][5] = person.get("cvFile").toString();     // القيمة الـ 6
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}