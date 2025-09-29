import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PanelConsultas extends JPanel {

    private Hospital hospital;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private VentanaPrincipal ventanaPrincipal;

    public PanelConsultas(Hospital hospital, VentanaPrincipal ventanaPrincipal) {
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

        String[] columnas = {"ID", "Fecha", "Motivo", "Sala", "Paciente", "Médico"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarConsultas();
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnPacientesPorMedico = new JButton("Ver pacientes por médico");
        JButton btnVolver = new JButton("Volver");
        JButton btnConsultasPorFecha = new JButton("Consultas por fecha");
        

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnPacientesPorMedico);
        panelBotones.add(btnVolver);
        panelBotones.add(btnConsultasPorFecha);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarConsulta());
        btnEditar.addActionListener(e -> editarConsulta());
        btnEliminar.addActionListener(e -> eliminarConsulta());
        btnPacientesPorMedico.addActionListener(e -> mostrarPacientesPorMedico());
        btnConsultasPorFecha.addActionListener(e -> mostrarConsultasPorFecha());
        btnVolver.addActionListener(e -> volverAlMenuPrincipal());

        
        btnBuscar.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
        campoBusqueda.addActionListener(e -> filtrarTabla(campoBusqueda.getText()));
    }

    private void cargarConsultas() {
        modeloTabla.setRowCount(0);
        for (Medico m : hospital.getMedicos()) {
            for (Consulta c : m.getConsultas()) {
                modeloTabla.addRow(new Object[]{c.getId(), c.getFecha(), c.getMotivo(), c.getSala(), c.getPaciente().getNombre(), m.getNombre()
                });
            }
        }
    }

    private void filtrarTabla(String texto) {
        List<Map.Entry<Consulta, Medico>> filtradas = hospital.getMedicos().stream()
                .flatMap(m -> m.getConsultas().stream()
                        .map(c -> new AbstractMap.SimpleEntry<>(c, m)))
                .filter(entry ->
                        String.valueOf(entry.getKey().getId()).contains(texto) ||
                        entry.getKey().getPaciente().getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                        entry.getValue().getNombre().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());

        modeloTabla.setRowCount(0);
        for (Map.Entry<Consulta, Medico> entry : filtradas) {
            Consulta c = entry.getKey();
            Medico m = entry.getValue();
            modeloTabla.addRow(new Object[]{c.getId(), c.getFecha(), c.getMotivo(), c.getSala(), c.getPaciente().getNombre(), m.getNombre()});
        }
    }

private void agregarConsulta() {
    JTextField id = new JTextField();
    JTextField fecha = new JTextField();
    JTextField motivo = new JTextField();
    JTextField sala = new JTextField();
    JComboBox<String> comboPacientes = new JComboBox<>();
    JComboBox<String> comboMedicos = new JComboBox<>();

    for (Paciente p : hospital.getPacientes()) comboPacientes.addItem(p.getRut() + " - " + p.getNombre());
    for (Medico m : hospital.getMedicos()) comboMedicos.addItem(m.getRut() + " - " + m.getNombre());

    Object[] mensaje = {"ID:", id, "Fecha:", fecha, "Motivo:", motivo, "Sala:", sala, "Paciente:", comboPacientes, "Médico:", comboMedicos};

    int option = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Consulta", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        String pacienteRut = comboPacientes.getSelectedItem().toString().split(" - ")[0];
        String medicoRut = comboMedicos.getSelectedItem().toString().split(" - ")[0];

        try {
            int idConsulta = Integer.parseInt(id.getText().trim());
            hospital.agregarConsulta(idConsulta, fecha.getText().trim(), motivo.getText().trim(),
                    sala.getText().trim(), pacienteRut, medicoRut);
            cargarConsultas();
            JOptionPane.showMessageDialog(this, "Consulta agregada correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de consulta no es un número válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoEncontradoException | EntidadExistenteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


private void editarConsulta() {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una consulta para editar.");
        return;
    }

    int id = (int) modeloTabla.getValueAt(fila, 0);

    Medico medicoActual = hospital.getMedicos().stream().filter(m -> m.getConsultas().stream().anyMatch(c -> c.getId() == id)).findFirst().orElse(null);

    if (medicoActual == null) return;

    Consulta c = medicoActual.getConsultas().stream().filter(cons -> cons.getId() == id).findFirst().orElse(null);

    if (c == null) return;

    JTextField fechaField = new JTextField(c.getFecha());
    JTextField motivoField = new JTextField(c.getMotivo());
    JTextField salaField = new JTextField(c.getSala());
    JTextField rutPacienteField = new JTextField(c.getPaciente().getRut());
    JTextField rutMedicoField = new JTextField(medicoActual.getRut());

    Object[] mensaje = {"Fecha (YYYY-MM-DD):", fechaField, "Motivo:", motivoField, "Sala:", salaField, "RUT Paciente:", rutPacienteField, "RUT Médico:", rutMedicoField
    };

    int option = JOptionPane.showConfirmDialog(this, mensaje, "Editar Consulta", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            c.modificarConsulta(hospital, fechaField.getText(), motivoField.getText(), salaField.getText(), rutPacienteField.getText(), rutMedicoField.getText()
            );

            JOptionPane.showMessageDialog(this, "Consulta modificada exitosamente.");
            cargarConsultas();

        } catch (NoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al modificar consulta", JOptionPane.ERROR_MESSAGE);
        }
    }
}




    private void eliminarConsulta() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        Medico medico = hospital.getMedicos().stream()
                .filter(m -> m.getConsultas().stream().anyMatch(c -> c.getId() == id))
                .findFirst()
                .orElse(null);

        if (medico != null) {
            medico.getConsultas().removeIf(c -> c.getId() == id);
            cargarConsultas();
        }
    }
    
    private void mostrarConsultasPorFecha() {
        String fecha = JOptionPane.showInputDialog(this, "Ingrese la fecha (YYYY-MM-DD):");

        if (fecha == null || fecha.trim().isEmpty()) return;
        fecha = fecha.trim();

        Map<Consulta, Medico> consultas;
        try {
            consultas = hospital.obtenerConsultasPorFecha(fecha);
            if (consultas == null) consultas = java.util.Collections.emptyMap();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar consultas: " + e.getMessage());
            return;
        }

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron consultas para la fecha " + fecha);
            return;
        }

        StringBuilder sb = new StringBuilder("Consultas para la fecha " + fecha + ":\n");
        for (Map.Entry<Consulta, Medico> entry : consultas.entrySet()) {
            Consulta c = entry.getKey();
            Medico m = entry.getValue();
            sb.append("- ID: ").append(c.getId())
              .append(", Médico: ").append(m != null ? m.getNombre() : "—")
              .append(", Paciente: ").append(c.getPaciente() != null ? c.getPaciente().getNombre() : "—")
              .append(", Motivo: ").append(c.getMotivo())
              .append(", Sala: ").append(c.getSala())
              .append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    
private void mostrarPacientesPorMedico() {
    String[] opciones = {"RUT", "Nombre"};
    int seleccion = JOptionPane.showOptionDialog(
            this,
            "Seleccione cómo desea buscar al médico:",
            "Buscar Médico",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
    );

    if (seleccion == -1) return; 

    Medico medico = null;

    if (seleccion == 0) {
        String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT del médico:");
        if (rut != null && !rut.trim().isEmpty()) {
            medico = hospital.buscarMedicoPorRut(rut.trim());
        }
    } else {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del médico:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            for (Medico m : hospital.getMedicos()) {
                if (m.getNombre() != null && m.getNombre().equalsIgnoreCase(nombre.trim())) {
                    medico = m;
                    break;
                }
            }
        }
    }

    if (medico == null) {
        JOptionPane.showMessageDialog(this, "No se encontró el médico.");
        return;
    }

    List<Paciente> pacientes = hospital.obtenerPacientesPorMedico(medico.getRut());

    if (pacientes.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron pacientes para este médico.");
    } else {
        StringBuilder sb = new StringBuilder("Pacientes atendidos por el médico " + medico.getNombre() + " (" + medico.getRut() + "):\n");
        for (Paciente p : pacientes) {
            sb.append("- ").append(p.getNombre())
              .append(" (").append(p.getRut()).append(")\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
}


    private void volverAlMenuPrincipal() {
        ventanaPrincipal.getContentPane().removeAll();
        ventanaPrincipal.getContentPane().add(ventanaPrincipal.getPanelPrincipal());
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }
}

