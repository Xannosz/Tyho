package hu.xannosz.tyho;

public class Logger {
    public static void log(String format, String... logs) {
        System.out.println(String.format(format, (Object[]) logs));
    }
}
