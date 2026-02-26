package web.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestHolder {
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static void set(ExtentTest t) { test.set(t); }
    public static ExtentTest get() { return test.get(); }
    public static void remove() { test.remove(); }
}