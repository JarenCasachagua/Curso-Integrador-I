
package com.barberia.vistas;

import com.barberia.dao.UsuarioDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistroFrame extends JFrame {

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JPasswordField txtConfirmar = new JPasswordField();

    public RegistroFrame() {
        setTitle("Barbería | Crear cuenta");
        setSize(470, 570);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(39, 39, 43));

        JLabel titulo = new JLabel("Crear cuenta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(222, 174, 85));

        agregar(panel, titulo);
        agregar(panel, new JLabel("Nombre completo"));
        agregarCampo(panel, txtNombre);
        agregar(panel, new JLabel("Correo electrónico"));
        agregarCampo(panel, txtCorreo);
        agregar(panel, new JLabel("Contraseña"));
        agregarCampo(panel, txtClave);
        agregar(panel, new JLabel("Confirmar contraseña"));
        agregarCampo(panel, txtConfirmar);

        JButton btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setBackground(new Color(222, 174, 85));
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        btnRegistrar.addActionListener(e -> registrar());

        panel.add(Box.createVerticalStrut(20));
        panel.add(btnRegistrar);

        setContentPane(panel);

        // Mejora el color de las etiquetas del formulario.
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel && c != titulo) {
                c.setForeground(Color.WHITE);
            }
        }
        titulo.setForeground(new Color(222, 174, 85));
    }

   private void agregar(JPanel panel, Component componente) {
    if (componente instanceof JComponent) {
        ((JComponent) componente)
                .setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    panel.add(componente);
    panel.add(Box.createVerticalStrut(7));
}

    private void agregarCampo(JPanel panel, JTextField campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        agregar(panel, campo);
        panel.add(Box.createVerticalStrut(8));
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim().toLowerCase();
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nombre.isEmpty() || correo.isEmpty()
                || clave.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completa todos los campos.");
            return;
        }

        if (!correo.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa un correo electrónico válido.");
            return;
        }

        if (clave.length() < 8) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 8 caracteres.");
            return;
        }

        if (!clave.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "Las contraseñas no coinciden.");
            return;
        }

        try {
            boolean registrado = new UsuarioDAO().registrar(
                    nombre, correo, clave);

            if (registrado) {
                JOptionPane.showMessageDialog(this,
                        "Cuenta creada correctamente. Ya puedes iniciar sesión.");
                dispose();
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ese correo ya está registrado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el usuario:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
