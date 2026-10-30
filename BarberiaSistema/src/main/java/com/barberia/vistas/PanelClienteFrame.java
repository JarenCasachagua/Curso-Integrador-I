
package com.barberia.vistas;

import com.barberia.modelos.Usuario;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelClienteFrame extends JFrame {

    public PanelClienteFrame(Usuario usuario) {
        setTitle("Barbería | Panel del cliente");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(24, 24, 27));

        JPanel lateral = new JPanel();
        lateral.setPreferredSize(new Dimension(220, 0));
        lateral.setBackground(new Color(28, 28, 32));
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(30, 20, 20, 20));

        JLabel logo = new JLabel("BARBERÍA");
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(new Color(222, 174, 85));

        JButton btnInicio = new JButton("Inicio");
        JButton btnReservar = new JButton("Reservar cita");
        JButton btnCitas = new JButton("Mis citas");
        JButton btnSalir = new JButton("Cerrar sesión");

        for (JButton boton :
                new JButton[]{btnInicio, btnReservar, btnCitas, btnSalir}) {
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            boton.setFocusPainted(false);
        }

        lateral.add(logo);
        lateral.add(Box.createVerticalStrut(40));
        lateral.add(btnInicio);
        lateral.add(Box.createVerticalStrut(12));
        lateral.add(btnReservar);
        lateral.add(Box.createVerticalStrut(12));
        lateral.add(btnCitas);
        lateral.add(Box.createVerticalGlue());
        lateral.add(btnSalir);

        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(39, 39, 43));
        contenido.setBorder(new EmptyBorder(35, 35, 35, 35));
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel bienvenida = new JLabel(
                "¡Hola, " + usuario.getNombre() + "!");
        bienvenida.setFont(new Font("SansSerif", Font.BOLD, 30));
        bienvenida.setForeground(Color.WHITE);

        JLabel descripcion = new JLabel(
                "Bienvenido a tu espacio de reservas.");
        descripcion.setForeground(new Color(190, 190, 195));

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(new Color(50, 50, 55));
        tarjeta.setBorder(new EmptyBorder(25, 25, 25, 25));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        JLabel tarjetaTitulo = new JLabel("Reserva tu próxima cita");
        tarjetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 21));
        tarjetaTitulo.setForeground(new Color(222, 174, 85));

        JLabel tarjetaTexto = new JLabel(
                "Elige tu servicio, barbero, fecha y hora.");
        tarjetaTexto.setForeground(Color.WHITE);

        JButton btnNuevaCita = new JButton("NUEVA RESERVA");
        btnNuevaCita.setBackground(new Color(222, 174, 85));
        btnNuevaCita.setForeground(Color.BLACK);

        tarjeta.add(tarjetaTitulo);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(tarjetaTexto);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnNuevaCita);

        contenido.add(bienvenida);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(descripcion);
        contenido.add(Box.createVerticalStrut(35));
        contenido.add(tarjeta);

        btnInicio.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Estás en el inicio de tu cuenta."));

        btnReservar.addActionListener(e ->
           new ReservaFrame(usuario).setVisible(true));

        btnNuevaCita.addActionListener(e -> btnReservar.doClick());

        btnCitas.addActionListener(e ->
        new CitasFrame(usuario, false).setVisible(true));
        
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        principal.add(lateral, BorderLayout.WEST);
        principal.add(contenido, BorderLayout.CENTER);
        setContentPane(principal);
    }
}
