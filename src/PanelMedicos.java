import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PanelMedicos extends JPanel {

    private Hospital hospital;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private VentanaPrincipal ventanaPrincipal;

    public PanelMedicos(Hospital hospital, VentanaPrincipal ventanaPrincipal) {
        this.hospital = hospital;
        this.ventanaPrincipal = ventanaPrincipal;

        setLayout(new BorderLayout());

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoBusqueda = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar: "));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        String[] columnas = {"RUT", "Nombre", "Especialidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarMedicos();

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrarDatos = new JButton("Mostrar Datos");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrarDatos);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarMedico());
        btnEditar.addActionListener(e -> modificarMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());
        btnMostrarDatos.addActionListener(e -> mostrarDatosMedico());
        btnVolver.addActionListener(e -> volverAlMenuPrincipal());

        btnBuscar.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
        campoBusqueda.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
    }


    private void cargarMedicos() {
        modeloTabla.setRowCount(0);
        for (Medico m : hospital.getMedicos()) {
            modeloTabla.addRow(new Object[]{m.getRut(), m.getNombre(), m.getEspecialidad()});
        }
    }

    private void filtrarTabla(String texto) {
        List<Medico> filtrados = hospital.getMedicos().stream()
                .filter(m -> m.getRut().contains(texto) || m.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());

        modeloTabla.setRowCount(0);
        for (Medico m : filtrados) {
            modeloTabla.addRow(new Object[]{m.getRut(), m.getNombre(), m.getEspecialidad()});
        }
    }

    private void agregarMedico() {
        JTextField rut = new JTextField();
        JTextField nombre = new JTextField();
        JTextField especialidad = new JTextField();
        Object[] mensaje = {"RUT:", rut,"Nombre:", nombre,"Especialidad:", especialidad};

        int option = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Médico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Scanner sc = new Scanner(rut.getText() + "\n" + nombre.getText() + "\n" + especialidad.getText());
                hospital.agregarMedico(sc);
                cargarMedicos();
                JOptionPane.showMessageDialog(this, "Médico agregado correctamente.");
            } catch (FormatoInvalidoException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            } catch (EntidadExistenteException e) {
                JOptionPane.showMessageDialog(this, "No se puede agregar: " + e.getMessage());
            }
        }
    }


    private void modificarMedico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico para modificar.");
            return;
        }

        String rutSeleccionado = (String) modeloTabla.getValueAt(fila, 0);
        Medico m = hospital.buscarMedicoPorRut(rutSeleccionado);
        if (m == null) return;

        JTextField nombreField = new JTextField(m.getNombre());
        JTextField especialidadField = new JTextField(m.getEspecialidad());

        Object[] mensaje = {
            "Nuevo nombre:", nombreField,
            "Nueva especialidad:", especialidadField
        };

        int option = JOptionPane.showConfirmDialog(this, mensaje, "Modificar Médico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String input = nombreField.getText() + "\n" + especialidadField.getText() + "\n";
                Scanner sc = new Scanner(input);

                m.modificar(sc);

                cargarMedicos();
                JOptionPane.showMessageDialog(this, "Médico modificado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void mostrarDatosMedico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico para mostrar.");
            return;
        }

        String rut = (String) modeloTabla.getValueAt(fila, 0);
        Medico m = hospital.buscarMedicoPorRut(rut);
        if (m == null) return;

        JOptionPane.showMessageDialog(this, m.printDatos(), "Datos del Médico", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarMedico() {
        String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del médico a eliminar:");
        if (rut != null && !rut.trim().isEmpty()) {
            try {
                Scanner sc = new Scanner(rut);
                hospital.eliminarMedico(sc);
                cargarMedicos();
                JOptionPane.showMessageDialog(this, "Médico eliminado correctamente.");
            } catch (NoEncontradoException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void volverAlMenuPrincipal() {
        ventanaPrincipal.getContentPane().removeAll();
        ventanaPrincipal.getContentPane().add(ventanaPrincipal.getPanelPrincipal());
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }
}
