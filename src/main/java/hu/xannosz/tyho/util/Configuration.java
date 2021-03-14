package hu.xannosz.tyho.util;

import com.google.gson.Gson;
import com.googlecode.lanterna.TextColor;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Data
public class Configuration {
    private TextColor foreGround = new TextColor.RGB(0, 99, 0);
    private TextColor backGround = new TextColor.RGB(0, 0, 33);
    private TextColor menuBackGround = new TextColor.RGB(33, 0, 0);
    private TextColor sideBackGround = new TextColor.RGB(55, 0, 0);
    private TextColor mainBackGround = new TextColor.RGB(0, 55, 0);
    private TextColor topLineBackGround = new TextColor.RGB(33, 22, 0);

    private int leftPanelSize = 20;
    private int rightPanelSize = 20;
    private int bottomPanelSize = 10;
    private boolean leftPanelOpened = true;
    private boolean rightPanelOpened = true;
    private boolean bottomPanelOpened = true;
    private boolean leftMenuEnabled = true;
    private boolean rightMenuEnabled = true;
    private boolean bottomMenuEnabled = true;

    private Map<String, Object> otherConfigs;

    public static Configuration read(Path path) throws IOException {
        return new Gson().fromJson(FileUtils.readFileToString(path.toFile(), Constants.ENCODING), Configuration.class);
    }

    public void write(Path path) throws IOException {
        FileUtils.write(path.toFile(), new Gson().toJson(this), Constants.ENCODING);
    }
}