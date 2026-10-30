
package com.barberia;

import com.barberia.vistas.LoginFrame;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Exception e) {
                System.err.println("No se pudo aplicar el diseño .");
            }

            new LoginFrame().setVisible(true);
        });
    }
}
