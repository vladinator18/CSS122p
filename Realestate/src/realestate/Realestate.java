package realestate;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Realestate {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Run animation on a separate thread to keep the GIF playing smoothly
        new Thread(() -> {
            Realestateopenui splash = new Realestateopenui();
            splash.playAnimation();
        }).start();
    }
}
