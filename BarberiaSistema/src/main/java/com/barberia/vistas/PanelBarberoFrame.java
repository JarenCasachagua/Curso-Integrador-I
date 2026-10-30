
package com.barberia.vistas;

import com.barberia.modelos.Usuario;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelBarberoFrame extends JFrame {

    public PanelBarberoFrame(Usuario usuario) {
        setTitle("Barbería | Panel del barbero");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(24, 24, 27));

        JPanel lateral = new JPanel();
        lateral.setPreferredSize(new Dimension(230, 0));
        lateral.setBackground(new Color(28, 28, 32));
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(30, 20, 20, 20));

        JLabel logo = new JLabel("BARBERÍA");
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(new Color(222, 174, 85));

        JLabel rol = new JLabel("PANEL DEL BARBERO");
        rol.setForeground(Color.WHITE);

        JButton btnInicio = new JButton("Inicio");
        JButton btnCitas = new JButton("Mis citas");
        JButton btnSalir = new JButton("Cerrar sesión");

        for (JButton boton :
                new JButton[]{btnInicio, btnCitas, btnSalir}) {
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            boton.setFocusPainted(false);
        }

        lateral.add(logo);
        lateral.add(Box.createVerticalStrut(15));
        lateral.add(rol);
        lateral.add(Box.createVerticalStrut(35));
        lateral.add(btnInicio);
        lateral.add(Box.createVerticalStrut(12));
        lateral.add(btnCitas);
        lateral.add(Box.createVerticalGlue());
        lateral.add(btnSalir);

        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(39, 39, 43));
        contenido.setBorder(new EmptyBorder(35, 35, 35, 35));
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Panel de trabajo");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JLabel saludo = new JLabel(
                "Bienvenido, " + usuario.getNombre());
        saludo.setForeground(new Color(190, 190, 195));

        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(new Color(50, 50, 55));
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(25, 25, 25, 25));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JLabel tarjetaTitulo = new JLabel("Agenda de citas");
        tarjetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        tarjetaTitulo.setForeground(new Color(222, 174, 85));

        JLabel tarjetaTexto = new JLabel(
                "Aquí podrás revisar y administrar tus citas.");
        tarjetaTexto.setForeground(Color.WHITE);

        JButton btnVerCitas = new JButton("VER MIS CITAS");
        btnVerCitas.setBackground(new Color(222, 174, 85));
        btnVerCitas.setForeground(Color.BLACK);

        tarjeta.add(tarjetaTitulo);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(tarjetaTexto);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnVerCitas);

        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(saludo);
        contenido.add(Box.createVerticalStrut(35));
        contenido.add(tarjeta);

        btnInicio.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Estás en el panel del barbero."));

       btnCitas.addActionListener(e ->
              new CitasFrame(usuario, true).setVisible(true));

      btnVerCitas.addActionListener(e ->
              new CitasFrame(usuario, true).setVisible(true));

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        principal.add(lateral, BorderLayout.WEST);
        principal.add(contenido, BorderLayout.CENTER);
        setContentPane(principal);
    }

    private void mostrarAviso() {
        JOptionPane.showMessageDialog(this,
                "En el siguiente paso conectaremos la agenda "
                + "con las citas guardadas en MySQL.");
    }
}
