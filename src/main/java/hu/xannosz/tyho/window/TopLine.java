package hu.xannosz.tyho.window;

import com.googlecode.lanterna.gui2.Panel;
import hu.xannosz.tyho.factory.ThemeFactory;

public class TopLine extends Panel {
    public TopLine(){
        this.setTheme(ThemeFactory.getTopLineTheme());
    }
}
