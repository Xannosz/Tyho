package hu.xannosz.tyho;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class ThemeHandler {

    public static String getTheme(String name) {
        try {
            return FileUtils.readFileToString(new File("css/" + (name==null?"base":name) + ".css"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
