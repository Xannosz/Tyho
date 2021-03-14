package hu.xannosz.tyho;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import hu.xannosz.tyho.factory.ThemeFactory;
import hu.xannosz.tyho.window.MainWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Screen screen = null;
        try {
            screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.setTheme(ThemeFactory.getUITheme());

            textGUI.addWindowAndWait(new MainWindow());
        } catch (Exception e) {
            // Empty
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    // Empty
                }
            }
        }
    }
}
