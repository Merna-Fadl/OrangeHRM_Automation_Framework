package Utils;

import org.testng.asserts.SoftAssert;

public class CustomSoftAssertion extends SoftAssert {


    public  static  CustomSoftAssertion softAssertion = new CustomSoftAssertion();

    public static void  customAssertAll(){
        try {
            softAssertion.assertAll("Custom soft Assertion");
        }
        catch (Exception e){
            System.out.println("Custom soft Assertion Failed");
        }

    }
}
