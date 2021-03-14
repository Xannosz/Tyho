package hu.xannosz.tyho.factory;

import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import hu.xannosz.tyho.util.Configuration;

public class ThemeFactory {
    public static Theme getUITheme(){
        Configuration configuration = ConfigurationFactory.getConfiguration();
        return new SimpleTheme(configuration.getForeGround(), configuration.getBackGround());
    }

    public static Theme getMenuTheme(){
        Configuration configuration = ConfigurationFactory.getConfiguration();
        return new SimpleTheme(configuration.getForeGround(), configuration.getMenuBackGround());
    }

    public static Theme getTopLineTheme(){
        Configuration configuration = ConfigurationFactory.getConfiguration();
        return new SimpleTheme(configuration.getForeGround(), configuration.getTopLineBackGround());
    }

    public static Theme getSidePanelTheme(){
        Configuration configuration = ConfigurationFactory.getConfiguration();
        return new SimpleTheme(configuration.getForeGround(), configuration.getSideBackGround());
    }

    public static Theme getMainPanelTheme(){
        Configuration configuration = ConfigurationFactory.getConfiguration();
        return new SimpleTheme(configuration.getForeGround(), configuration.getMainBackGround());
    }
}
