
package com.barberia.vistas;

import com.barberia.dao.CitaDAO;
import com.barberia.modelos.Usuario;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReservaFrame extends JFrame {

    private final Usuario usuario;
    private final CitaDAO dao = new CitaDAO();

    private final JComboBox<String> cmbServicios = new JComboBox<>();
    private final JComboBox<String> cmbBarberos = new JComboBox<>();
    private final JTextField txtFecha = new JTextField();

    private List<Object[]> servicios;
    private List<Object[]> barberos;

    public ReservaFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Barbería | Reservar cita");
        setSize(520, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));
        panel.setBackground(new Color(39, 39, 43));

        JLabel titulo = new JLabel("Reservar una cita");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 27));
        titulo.setForeground(new Color(222, 174, 85));

        JLabel lblServicio = etiqueta("Servicio");
        JLabel lblBarbero = etiqueta("Barbero");
        JLabel lblFecha = etiqueta("Fecha y hora");

        cmbServicios.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        cmbBarberos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtFecha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JButton btnReservar = new JButton("CONFIRMAR RESERVA");
        btnReservar.setBackground(new Color(222, 174, 85));
        btnReservar.setForeground(Color.BLACK);
        btnReservar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel ayuda = new JLabel("Formato: 2026-10-25 15:30");
        ayuda.setForeground(Color.LIGHT_GRAY);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(25));
        agregar(panel, lblServicio);
        agregar(panel, cmbServicios);
        agregar(panel, lblBarbero);
        agregar(panel, cmbBarberos);
        agregar(panel, lblFecha);
        agregar(panel, txtFecha);
        agregar(panel, ayuda);
        panel.add(Box.createVerticalStrut(20));
        agregar(panel, btnReservar);

        setContentPane(panel);

        cargarOpciones();

        btnReservar.addActionListener(e -> reservar());
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }


        private void agregar(JPanel panel, Component componente) {
            if (componente instanceof JComponent) {
                ((JComponent) componente)
                        .setAlignmentX(Component.LEFT_ALIGNMENT);
            }

            panel.add(componente);
            panel.add(Box.createVerticalStrut(7));
        }


    private void cargarOpciones() {
        try {
            servicios = dao.listarServicios();
            barberos = dao.listarBarberos();

            cmbServicios.removeAllItems();
            for (Object[] servicio : servicios) {
                cmbServicios.addItem(
                        servicio[1] + " - S/ " + servicio[2]
                        + " (" + servicio[3] + " min)");
            }

            cmbBarberos.removeAllItems();
            for (Object[] barbero : barberos) {
                cmbBarberos.addItem(barbero[1].toString());
            }

            if (servicios.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Primero debes registrar servicios en MySQL.");
            }

            if (barberos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todavía no hay barberos registrados.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los datos:\n" + ex.getMessage());
        }
    }

    private void reservar() {
        if (servicios == null || barberos == null
                || cmbServicios.getSelectedIndex() < 0
                || cmbBarberos.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Necesitas tener servicios y barberos registrados.");
            return;
        }

        try {
            LocalDateTime fecha = LocalDateTime.parse(
                    txtFecha.getText().trim(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            int servicioId = (int) servicios.get(
                    cmbServicios.getSelectedIndex())[0];

            int barberoId = (int) barberos.get(
                    cmbBarberos.getSelectedIndex())[0];

            dao.reservarCita(
                    usuario.getId(), barberoId, servicioId, fecha);

            JOptionPane.showMessageDialog(this,
                    "¡Cita reservada correctamente!");
            dispose();

        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Usa el formato AAAA-MM-DD HH:MM, por ejemplo 2026-10-25 15:30.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo reservar la cita:\n" + ex.getMessage());
        }
    }
}
