package hu.xannosz.tyho.window;

import com.googlecode.lanterna.gui2.Panel;
import hu.xannosz.tyho.factory.ThemeFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends Panel {

    @Getter
    private final Side side;
    private final List<String> panels = new ArrayList<>();
    private int selected = -1;

    public MenuPanel(Side side) {
        this.side = side;
        this.setTheme(ThemeFactory.getMenuTheme());
    }

    public MenuPanel addPanel(String panel) {
        panels.add(panel);
        return this;
    }

    public String getSelected() {
        return panels.get(selected);
    }

    private void selectNext() {
        selected++;
        if (selected >= panels.size()) {
            selected = -1;
        }
    }

    private void selectPrevious() {
        selected--;
        if (selected < -1) {
            selected = panels.size() - 1;
        }
    }

    public enum Side {
        LEFT, RIGHT, BOTTOM
    }
}
