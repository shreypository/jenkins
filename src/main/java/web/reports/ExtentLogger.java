package web.reports;

public class ExtentLogger {
    public static void step(String message) {
        if (ExtentTestHolder.get() != null) {
            ExtentTestHolder.get().info("STEP: " + message);
        }
    }
}