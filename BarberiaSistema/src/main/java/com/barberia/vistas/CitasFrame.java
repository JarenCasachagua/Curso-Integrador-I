
package com.barberia.vistas;

import com.barberia.dao.CitaDAO;
import com.barberia.modelos.Usuario;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CitasFrame extends JFrame {

    private final Usuario usuario;
    private final boolean esBarbero;
    private final CitaDAO dao = new CitaDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Cliente", "Barbero", "Servicio",
                         "Fecha y hora", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    private final JTable tabla = new JTable(modelo);
    private final JComboBox<String> cmbEstado = new JComboBox<>(
            new String[]{"CONFIRMADA", "EN_ATENCION",
                         "FINALIZADA", "CANCELADA"});

    public CitasFrame(Usuario usuario, boolean esBarbero) {
        this.usuario = usuario;
        this.esBarbero = esBarbero;

        setTitle(esBarbero ? "Barbería | Agenda del barbero"
                           : "Barbería | Mis citas");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBackground(new Color(39, 39, 43));
        principal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(
                esBarbero ? "Agenda de mis citas" : "Mis reservas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(new Color(222, 174, 85));

        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnAccion = new JButton(
                esBarbero ? "Cambiar estado" : "Cancelar cita");

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acciones.setOpaque(false);

        acciones.add(btnActualizar);

        if (esBarbero) {
            acciones.add(cmbEstado);
        }

        acciones.add(btnAccion);

        principal.add(titulo, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);
        principal.add(acciones, BorderLayout.SOUTH);

        setContentPane(principal);

        btnActualizar.addActionListener(e -> cargarCitas());
        btnAccion.addActionListener(e -> ejecutarAccion());

        cargarCitas();
    }

    private void cargarCitas() {
        try {
            List<Object[]> citas = esBarbero
                    ? dao.listarCitasBarbero(usuario.getId())
                    : dao.listarCitasCliente(usuario.getId());

            modelo.setRowCount(0);

            for (Object[] cita : citas) {
                modelo.addRow(cita);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar las citas:\n" + ex.getMessage());
        }
    }

    private void ejecutarAccion() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una cita de la tabla.");
            return;
        }

        int filaModelo = tabla.convertRowIndexToModel(fila);
        int citaId = ((Number) modelo.getValueAt(filaModelo, 0)).intValue();
        String estadoActual = modelo.getValueAt(filaModelo, 5).toString();

        try {
            if (esBarbero) {
                if (estadoActual.equals("FINALIZADA")
                        || estadoActual.equals("CANCELADA")) {
                    JOptionPane.showMessageDialog(this,
                            "Esta cita ya está cerrada.");
                    return;
                }

                String estadoNuevo = cmbEstado.getSelectedItem().toString();

                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Cambiar el estado a " + estadoNuevo + "?",
                        "Confirmar cambio",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }

                dao.actualizarEstado(
                        citaId, usuario.getId(), estadoNuevo);

            } else {
                if (!estadoActual.equals("PENDIENTE")) {
                    JOptionPane.showMessageDialog(this,
                            "Solo puedes cancelar citas pendientes.");
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas cancelar esta cita?",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }

                dao.cancelarCitaCliente(citaId, usuario.getId());
            }

            cargarCitas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar la cita:\n" + ex.getMessage());
        }
    }
}
