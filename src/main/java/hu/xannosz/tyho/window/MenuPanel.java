package hu.xannosz.tyho.window;

import com.googlecode.lanterna.gui2.Panel;
import hu.xannosz.tyho.factory.ThemeFactory;
import lombok.Getter;

public class MenuPanel extends Panel {

    @Getter
    private final Side side;

    public MenuPanel(Side side) {
        this.side=side;
        this.setTheme(ThemeFactory.getMenuTheme());
    }

    public enum Side {
        LEFT, RIGHT, BOTTOM
    }
}
