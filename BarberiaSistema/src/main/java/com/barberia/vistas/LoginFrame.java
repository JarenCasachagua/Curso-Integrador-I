
package com.barberia.vistas;

import com.barberia.dao.UsuarioDAO;
import com.barberia.modelos.Usuario;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    private final JTextField txtCorreo = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();

    public LoginFrame() {
        setTitle("Barbería | Iniciar sesión");
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new GridLayout(1, 2));

        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.setBackground(new Color(28, 28, 32));
        izquierda.setBorder(new EmptyBorder(85, 40, 40, 35));

        JLabel logo = new JLabel("BARBERÍA");
        logo.setFont(new Font("SansSerif", Font.BOLD, 36));
        logo.setForeground(new Color(222, 174, 85));

        JLabel mensaje = new JLabel(
                "<html>Tu estilo,<br>nuestra prioridad.</html>");
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 27));
        mensaje.setForeground(Color.WHITE);

        JLabel detalle = new JLabel(
                "<html>Reserva tu cita y disfruta<br>"
                + "de una atención personalizada.</html>");
        detalle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        detalle.setForeground(new Color(190, 190, 195));

        izquierda.add(logo);
        izquierda.add(Box.createVerticalStrut(45));
        izquierda.add(mensaje);
        izquierda.add(Box.createVerticalStrut(20));
        izquierda.add(detalle);

        JPanel derecha = new JPanel();
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        derecha.setBackground(new Color(39, 39, 43));
        derecha.setBorder(new EmptyBorder(75, 45, 35, 45));

        JLabel titulo = new JLabel("Iniciar sesión");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 29));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Ingresa con tu cuenta");
        subtitulo.setForeground(new Color(190, 190, 195));

        JLabel correoLabel = new JLabel("Correo electrónico");
        JLabel claveLabel = new JLabel("Contraseña");

        configurarEtiqueta(correoLabel);
        configurarEtiqueta(claveLabel);
        configurarCampo(txtCorreo);
        configurarCampo(txtClave);

        JButton btnIngresar = new JButton("INGRESAR");
        JButton btnRegistro = new JButton("Crear una cuenta");

        btnIngresar.setBackground(new Color(222, 174, 85));
        btnIngresar.setForeground(Color.BLACK);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);

        btnRegistro.setBackground(new Color(55, 55, 60));
        btnRegistro.setForeground(Color.WHITE);
        btnRegistro.setFocusPainted(false);

        Dimension tamanoBoton = new Dimension(Integer.MAX_VALUE, 42);
        btnIngresar.setMaximumSize(tamanoBoton);
        btnRegistro.setMaximumSize(tamanoBoton);

        btnIngresar.addActionListener(e -> ingresar());
        btnRegistro.addActionListener(e ->
                new RegistroFrame().setVisible(true));

        agregar(derecha, titulo);
        agregar(derecha, subtitulo);
        derecha.add(Box.createVerticalStrut(28));
        agregar(derecha, correoLabel);
        agregar(derecha, txtCorreo);
        derecha.add(Box.createVerticalStrut(15));
        agregar(derecha, claveLabel);
        agregar(derecha, txtClave);
        derecha.add(Box.createVerticalStrut(25));
        agregar(derecha, btnIngresar);
        derecha.add(Box.createVerticalStrut(10));
        agregar(derecha, btnRegistro);

        principal.add(izquierda);
        principal.add(derecha);
        setContentPane(principal);

        getRootPane().setDefaultButton(btnIngresar);
    }

    private void configurarEtiqueta(JLabel etiqueta) {
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    private void configurarCampo(JTextField campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setPreferredSize(new Dimension(280, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void agregar(JPanel panel, Component componente) {
    if (componente instanceof JComponent) {
        JComponent jc = (JComponent) componente;
        jc.setAlignmentX(Component.LEFT_ALIGNMENT);
        jc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
    }

    panel.add(componente);
    panel.add(Box.createVerticalStrut(8));
}

    private void ingresar() {
        String correo = txtCorreo.getText().trim().toLowerCase();
        String clave = new String(txtClave.getPassword());

        if (correo.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa tu correo y contraseña.");
            return;
        }

        try {
            Usuario usuario = new UsuarioDAO()
                    .iniciarSesion(correo, clave);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this,
                        "Correo o contraseña incorrectos.");
                return;
            }

            switch (usuario.getRol()) {
                case "CLIENTE" ->
                    new PanelClienteFrame(usuario).setVisible(true);
                case "BARBERO" ->
                    new PanelBarberoFrame(usuario).setVisible(true);
                case "ADMIN" ->
                    JOptionPane.showMessageDialog(this,
                            "Cuenta de administrador válida. "
                            + "Crearemos su panel en el siguiente paso.");
                default ->
                    JOptionPane.showMessageDialog(this,
                            "El rol de la cuenta no es válido.");
            }

            if (!usuario.getRol().equals("ADMIN")) {
                dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al iniciar sesión:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
