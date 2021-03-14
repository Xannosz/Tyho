package hu.xannosz.tyho.window;

import com.googlecode.lanterna.gui2.Panel;
import hu.xannosz.tyho.factory.ThemeFactory;

public class MainPanel extends Panel {
    public MainPanel(){
        this.setTheme(ThemeFactory.getMainPanelTheme());
    }
}
