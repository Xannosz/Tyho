package hu.xannosz.tyho.window;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import hu.xannosz.tyho.factory.ConfigurationFactory;
import hu.xannosz.tyho.util.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainWindow extends BasicWindow implements WindowListener {

    private final Panel topLine = new TopLine();
    private final Panel leftMenu = new MenuPanel(MenuPanel.Side.LEFT);
    private final Panel rightMenu = new MenuPanel(MenuPanel.Side.RIGHT);
    private final Panel bottomMenu = new MenuPanel(MenuPanel.Side.BOTTOM);

    private final Panel mainPanel = new MainPanel();
    private final Panel leftPanel = new SidePanel();
    private final Panel rightPanel = new SidePanel();
    private final Panel bottomPanel = new SidePanel();

    private final List<Panel> panels = Arrays.asList(topLine, leftMenu, rightMenu, bottomMenu, mainPanel, leftPanel, rightPanel, bottomPanel);
    private int selected = 4;

    private TerminalSize size;

    public MainWindow() {
        Panel content = new Panel();

        panels.forEach(content::addComponent);

        content.setLayoutManager(null);

        addWindowListener(this);
        setHints(Arrays.asList(Window.Hint.NO_DECORATIONS, Window.Hint.FULL_SCREEN));
        setComponent(content);
    }

    @Override
    public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
        size = newSize;
        resizePanels();
    }

    @Override
    public void onMoved(Window window, TerminalPosition terminalPosition, TerminalPosition terminalPosition1) {

    }

    @Override
    public void onInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {
        Configuration config = ConfigurationFactory.getConfiguration();
        if (keyStroke.isCtrlDown()) {
            switch (keyStroke.getKeyType()) {
                case ArrowLeft:
                    if (keyStroke.isShiftDown()) {
                        if (keyStroke.isAltDown()) {
                            config.setLeftMenuEnabled(!config.isLeftMenuEnabled());
                        } else {
                            config.setLeftPanelOpened(!config.isLeftPanelOpened());
                        }
                    } else {
                        setNewFocus(Direction.WEST);
                    }
                    break;
                case ArrowRight:
                    if (keyStroke.isShiftDown()) {
                        if (keyStroke.isAltDown()) {
                            config.setRightMenuEnabled(!config.isRightMenuEnabled());
                        } else {
                            config.setRightPanelOpened(!config.isRightPanelOpened());
                        }
                    } else {
                        setNewFocus(Direction.EAST);
                    }
                    break;
                case ArrowDown:
                    if (keyStroke.isShiftDown()) {
                        if (keyStroke.isAltDown()) {
                            config.setBottomMenuEnabled(!config.isBottomMenuEnabled());
                        } else {
                            config.setBottomPanelOpened(!config.isBottomPanelOpened());
                        }
                    } else {
                        setNewFocus(Direction.SOUTH);
                    }
                    break;
                case ArrowUp:
                    if (!keyStroke.isShiftDown()) {
                        setNewFocus(Direction.NORTH);
                    }
                    break;
                default:
            }
            resizePanels();
        }
    }

    @Override
    public void onUnhandledInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {
        System.out.println("##U: " + keyStroke + " ||| " + atomicBoolean);
    }

    private void resizePanels() {
        Configuration config = ConfigurationFactory.getConfiguration();

        setSize(topLine, 0, 0, size.getColumns(), 1);

        setSize(config.isBottomMenuEnabled(), bottomMenu, 0, size.getRows() - 1,
                size.getColumns(), 1);
        setSize(config.isLeftMenuEnabled(), leftMenu, 0, 1,
                1, size.getRows() - 1 - bottomMenu.getSize().getRows());
        setSize(config.isRightMenuEnabled(), rightMenu, size.getColumns() - 1, 1,
                1, size.getRows() - 1 - bottomMenu.getSize().getRows());

        setSize(config.isBottomPanelOpened(), bottomPanel, leftMenu.getSize().getColumns(), size.getRows() - config.getBottomPanelSize() - bottomMenu.getSize().getRows(),
                size.getColumns() - leftMenu.getSize().getColumns() - rightMenu.getSize().getColumns(), config.getBottomPanelSize());
        setSize(config.isLeftPanelOpened(), leftPanel, leftMenu.getSize().getColumns(), 1,
                config.getLeftPanelSize(), size.getRows() - bottomPanel.getSize().getRows() - 1 - bottomMenu.getSize().getRows());
        setSize(config.isRightPanelOpened(), rightPanel, size.getColumns() - config.getRightPanelSize() - rightMenu.getSize().getColumns(), 1,
                config.getRightPanelSize(), size.getRows() - bottomPanel.getSize().getRows() - 1 - bottomMenu.getSize().getRows());

        setSize(mainPanel, leftMenu.getSize().getColumns() + leftPanel.getSize().getColumns(), 1,
                size.getColumns() - rightMenu.getSize().getColumns() - rightPanel.getSize().getColumns() - leftMenu.getSize().getColumns() - leftPanel.getSize().getColumns(),
                size.getRows() - bottomPanel.getSize().getRows() - 1 - bottomMenu.getSize().getRows());

        panels.forEach(Panel::invalidate);
    }

    private void setSize(boolean enabled, Panel panel, int x, int y, int w, int h) {
        if (enabled) {
            setSize(panel, x, y, w, h);
        } else {
            setSizeZero(panel);
        }
    }

    private void setSize(Panel panel, int x, int y, int w, int h) {
        panel.setPosition(new TerminalPosition(x, y));
        panel.setSize(new TerminalSize(w, h));
    }

    private void setSizeZero(Panel panel) {
        setSize(panel, 0, 0, 0, 0);
    }

    private void setNewFocus(Direction direction) {
        Configuration config = ConfigurationFactory.getConfiguration();
        switch (selected) {
            case 0: // topLine
                switch (direction) {
                    case EAST:
                        selected = 2;
                        return;
                    case WEST:
                        selected = 1;
                        return;
                    case NORTH:
                        return;
                    case SOUTH:
                        selected = 4;
                        return;
                }
            case 1: // leftMenu
                switch (direction) {
                    case EAST:
                        if (config.isLeftPanelOpened()) {
                            selected = 5;
                        } else {
                            selected = 4;
                        }
                        return;
                    case WEST:
                        return;
                    case NORTH:
                        selected = 0;
                        return;
                    case SOUTH:
                        selected = 3;
                        return;
                }
            case 2: // rightMenu
                switch (direction) {
                    case EAST:
                        return;
                    case WEST:
                        if (config.isRightPanelOpened()) {
                            selected = 6;
                        } else {
                            selected = 4;
                        }
                        return;
                    case NORTH:
                        selected = 0;
                        return;
                    case SOUTH:
                        selected = 3;
                        return;
                }
            case 3: // bottomMenu
                switch (direction) {
                    case EAST:
                        selected = 1;
                        return;
                    case WEST:
                        selected = 2;
                        return;
                    case NORTH:
                        if (config.isBottomPanelOpened()) {
                            selected = 7;
                        } else {
                            selected = 4;
                        }
                        return;
                    case SOUTH:
                        return;
                }
            case 4: // mainPanel
                switch (direction) {
                    case EAST:
                        if (config.isRightPanelOpened()) {
                            selected = 6;
                        } else {
                            selected = 2;
                        }
                        return;
                    case WEST:
                        if (config.isLeftPanelOpened()) {
                            selected = 5;
                        } else {
                            selected = 1;
                        }
                        return;
                    case NORTH:
                        selected = 0;
                        return;
                    case SOUTH:
                        if (config.isBottomPanelOpened()) {
                            selected = 7;
                        } else {
                            selected = 3;
                        }
                        return;
                }
            case 5: //leftPanel
                switch (direction) {
                    case EAST:
                        selected = 4;
                        return;
                    case WEST:
                        selected = 1;
                        return;
                    case NORTH:
                        selected = 0;
                        return;
                    case SOUTH:
                        if (config.isBottomPanelOpened()) {
                            selected = 7;
                        } else {
                            selected = 3;
                        }
                        return;
                }
            case 6: // rightPanel
                switch (direction) {
                    case EAST:
                        selected = 2;
                        return;
                    case WEST:
                        selected = 4;
                        return;
                    case NORTH:
                        selected = 0;
                        return;
                    case SOUTH:
                        if (config.isBottomPanelOpened()) {
                            selected = 7;
                        } else {
                            selected = 3;
                        }
                        return;
                }
            case 7: // bottomPanel
                switch (direction) {
                    case EAST:
                        selected = 2;
                        return;
                    case WEST:
                        selected = 1;
                        return;
                    case NORTH:
                        selected = 4;
                        return;
                    case SOUTH:
                        selected = 3;
                        return;
                }
            default:
        }
    }

    public enum Direction {
        NORTH, EAST, WEST, SOUTH
    }
}
