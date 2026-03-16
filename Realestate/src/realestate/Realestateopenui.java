/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package realestate;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
/**
 *
 * @author timothyrajthadani
 */


public class Realestateopenui extends JWindow {

    private JProgressBar progressBar;

    public Realestateopenui() {
        // Setup the splash screen layout
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        content.setBackground(Color.WHITE);

        // Header Label
        JLabel label = new JLabel("Real Estate Property System", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(70, 130, 180));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // GIF Loading Area
        // Note: Ensure loading-wtf.gif is in the root of your project or update the path
     ImageIcon loadingIcon = new ImageIcon(getClass().getResource("loading-wtf.gif"));
        JLabel gifLabel = new JLabel(loadingIcon);

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(60, 179, 113));
        progressBar.setPreferredSize(new Dimension(450, 20));

        content.add(label, BorderLayout.NORTH);
        content.add(gifLabel, BorderLayout.CENTER);
        content.add(progressBar, BorderLayout.SOUTH);

        setContentPane(content);
        pack(); // Adjust size based on GIF dimensions
        setLocationRelativeTo(null); // Center on screen
    }

    public void playAnimation() {
        setVisible(true);
        try {
            // Simulate loading progress with the GIF running
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(40); 
                progressBar.setValue(i);
                
                if (i == 20) progressBar.setString("Fetching Database...");
                if (i == 50) progressBar.setString("Loading Assets...");
                if (i == 80) progressBar.setString("Launching Interface...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Close Splash and Open Main UI
        setVisible(false);
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            SimpleUI ui = new SimpleUI();
            ui.setVisible(true);
        });
    }
}