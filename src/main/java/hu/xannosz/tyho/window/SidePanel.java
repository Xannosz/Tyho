package hu.xannosz.tyho.window;

import com.googlecode.lanterna.gui2.Panel;
import hu.xannosz.tyho.factory.ThemeFactory;

public class SidePanel  extends Panel {
    public SidePanel(){
        this.setTheme(ThemeFactory.getSidePanelTheme());
    }
}
