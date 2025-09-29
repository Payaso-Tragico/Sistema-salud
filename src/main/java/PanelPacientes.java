import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PanelPacientes extends JPanel {

    private Hospital hospital;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private VentanaPrincipal ventanaPrincipal;

public PanelPacientes(Hospital hospital, VentanaPrincipal ventanaPrincipal) {
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

        String[] columnas = {"RUT", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarPacientes();

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

        btnAgregar.addActionListener(e -> agregarPaciente());
        btnEditar.addActionListener(e -> {
            try {
                editarPaciente();
            } catch (NoEncontradoException ex) {
                Logger.getLogger(PanelPacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnEliminar.addActionListener(e -> eliminarPaciente());
        btnMostrarDatos.addActionListener(e -> mostrarDatosPaciente());
        btnVolver.addActionListener(e -> volverAlMenuPrincipal());

        btnBuscar.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
        campoBusqueda.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
    }


    private void cargarPacientes() {
        modeloTabla.setRowCount(0);
        for (Paciente p : hospital.getPacientes()) {
            modeloTabla.addRow(new Object[]{
                    p.getRut(),
                    p.getNombre()
            });
        }
    }
    
    private void filtrarTabla(String texto) {
        modeloTabla.setRowCount(0);
        for (Paciente p : hospital.getPacientes()) {
            if (p.getRut().toLowerCase().contains(texto.toLowerCase()) ||
                p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                modeloTabla.addRow(new Object[]{
                        p.getRut(),
                        p.getNombre()
                });
            }
        }
    }

    private void agregarPaciente() {
        JTextField rut = new JTextField();
        JTextField nombre = new JTextField();
        JTextField edad = new JTextField();
        JTextField diagnostico = new JTextField();
        Object[] mensaje = {"RUT:", rut,"Nombre:", nombre,"Edad:", edad,"Diagnóstico:", diagnostico};

        int option = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Paciente p = new Paciente(rut.getText().trim(),nombre.getText().trim(),Integer.parseInt(edad.getText().trim()),diagnostico.getText().trim());

                hospital.agregarPaciente(p);

                cargarPacientes();
                JOptionPane.showMessageDialog(this, "Paciente agregado exitosamente.");

            } catch (FormatoInvalidoException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al agregar paciente: " + e.getMessage(),
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "La edad ingresada no es un número válido.",
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo agregar el paciente: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarPaciente() throws NoEncontradoException {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para editar.");
            return;
        }

        String rut = (String) modeloTabla.getValueAt(fila, 0);
        Paciente p = hospital.buscarPacientePorRut(rut);
        if (p == null) return;

        JTextField nombreField = new JTextField(p.getNombre());
        JTextField edadField = new JTextField(String.valueOf(p.getEdad()));
        JTextField diagnosticoField = new JTextField(p.getDiagnostico());

        Object[] mensaje = {"Nombre:", nombreField,"Edad:", edadField,"Diagnóstico:", diagnosticoField};

        int option = JOptionPane.showConfirmDialog(this, mensaje, "Editar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String input = nombreField.getText() + "\n" + edadField.getText() + "\n" + diagnosticoField.getText() + "\n";
            Scanner sc = new Scanner(input);


            p.modificar(sc);
            cargarPacientes();
            JOptionPane.showMessageDialog(this, "Paciente modificado correctamente.");
        }
    }

    private void mostrarDatosPaciente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para mostrar.");
            return;
        }

        String rut = (String) modeloTabla.getValueAt(fila, 0);
        Paciente p = null;
        try {
            p = hospital.buscarPacientePorRut(rut);
            JOptionPane.showMessageDialog(this, p.printDatos(), "Datos del Paciente", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró ningún paciente con RUT: " + rut, 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            Logger.getLogger(PanelPacientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarPaciente() {
        try {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente para eliminar.");
                return;
            }
            
            String rut = (String) modeloTabla.getValueAt(fila, 0);
            Paciente p = hospital.buscarPacientePorRut(rut);
            if (p != null) {
                hospital.getPacientes().remove(p);
                cargarPacientes();
            }
        } catch (NoEncontradoException ex) {
            Logger.getLogger(PanelPacientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void volverAlMenuPrincipal() {
        ventanaPrincipal.getContentPane().removeAll();
        ventanaPrincipal.getContentPane().add(ventanaPrincipal.getPanelPrincipal()); 
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }
}


