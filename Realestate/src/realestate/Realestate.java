package realestate;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Realestate {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set System Look & Feel to match the operating system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            // Create and display the SimpleUI instance
            SimpleUI ui = new SimpleUI();
            ui.setVisible(true);
        });
    }
}
