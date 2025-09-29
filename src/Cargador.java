import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cargador {

    public static void cargarPacientes(Hospital hospital, String rutaArchivo) throws EntidadExistenteException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(";");
                if (datos.length != 4) {
                    System.out.println("Línea inválida ignorada: " + linea);
                    continue;
                }

                try {
                    String rut = datos[0];
                    String nombre = datos[1];
                    int edad = Integer.parseInt(datos[2]);
                    String diagnostico = datos[3];

                    Utilidad.validarRut(rut);

                    Paciente p = new Paciente(rut, nombre, edad, diagnostico);
                    hospital.agregarPaciente(p);

                } catch (NumberFormatException ex) {
                    System.out.println("Edad inválida en línea: " + linea);
                } catch (FormatoInvalidoException ex) {
                    System.out.println("RUT inválido en línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error cargando pacientes: " + e.getMessage());
        }
    }

    public static void cargarMedicos(Hospital hospital, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(";");
                if (datos.length != 3) {
                    System.out.println("Línea inválida ignorada (medico): " + linea);
                    continue;
                }

                Medico m = new Medico(datos[0], datos[1], datos[2]);
                hospital.agregarMedico(m);
            }
        } catch (IOException e) {
            System.out.println("Error cargando médicos: " + e.getMessage());
        }
    }

    public static void cargarConsultas(Hospital hospital, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(";");
                if (datos.length != 6) {
                    System.out.println("Línea inválida ignorada (consulta): " + linea);
                    continue;
                }

                Paciente paciente = null;
                try {
                    paciente = hospital.buscarPacientePorRut(datos[4]);
                } catch (NoEncontradoException ex) {
                    Logger.getLogger(Cargador.class.getName()).log(Level.SEVERE, null, ex);
                }
                Medico medico = hospital.buscarMedicoPorRut(datos[5]);

                if (paciente == null || medico == null) {
                    System.out.println("Consulta ignorada: paciente o médico no encontrado -> " + linea);
                    continue;
                }

                Consulta c = new Consulta(
                    Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], paciente);

                medico.getConsultas().add(c);
            }
        } catch (IOException e) {
            System.out.println("Error cargando consultas: " + e.getMessage());
        }
    }
    
    public static Hospital cargarHospital(String rutaArchivo) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(rutaArchivo));
            if (!lineas.isEmpty()) {
                String[] datos = lineas.get(0).split(";");
                return new Hospital(datos[0], datos[1], datos[2]);
            }
        } catch (IOException e) {
            System.out.println("Error cargando hospital: " + e.getMessage());
        }
        return new Hospital("Hospital Desconocido", "Dirección no disponible", "00000000");
    }
        //------------------------------ AQUÍ ESTÁN LOS MÉTODOS PARA GUARDAR-----------------------------------------
    public static void guardarHospital(Hospital hospital, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(hospital.getNombre() + ";" + hospital.getDireccion() + ";" + hospital.getTelefono());
        } catch (IOException e) {
            System.out.println("Error guardando hospital: " + e.getMessage());
        }
    }
    
    public static void guardarPacientes(Hospital hospital, String rutaArchivo) {
        List<Paciente> lista = new ArrayList<>(hospital.getPacientes());
        if (lista.isEmpty()) {
            System.out.println("No hay pacientes para guardar, se mantiene el archivo original.");
            return; 
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < lista.size(); i++) {
                Paciente p = lista.get(i);
                bw.write(p.getRut() + ";" + p.getNombre() + ";" + p.getEdad() + ";" + p.getDiagnostico());
                if (i != lista.size() - 1) bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando pacientes: " + e.getMessage());
        }
    }
    
    
    public static void guardarMedicos(Hospital hospital, String rutaArchivo) {
        List<Medico> lista = new ArrayList<>(hospital.getMedicos());
        if (lista.isEmpty()) {
            System.out.println("No hay médicos para guardar.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < lista.size(); i++) {
                Medico m = lista.get(i);
                bw.write(m.getRut() + ";" + m.getNombre() + ";" + m.getEspecialidad());
                if (i != lista.size() - 1) bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando médicos: " + e.getMessage());
        }
    }
    
    public static void guardarConsultas(Hospital hospital, String rutaArchivo) {
        List<Medico> medicos = new ArrayList<>(hospital.getMedicos());
        boolean hayConsultas = medicos.stream().anyMatch(m -> !m.getConsultas().isEmpty());
        if (!hayConsultas) {
            System.out.println("No hay consultas para guardar.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            boolean firstLine = true;
            for (Medico m : medicos) {
                for (Consulta c : m.getConsultas()) {
                    if (!firstLine) bw.newLine();
                    bw.write(c.getId() + ";" + c.getFecha() + ";" + c.getMotivo() + ";" + c.getSala()
                            + ";" + c.getPaciente().getRut() + ";" + m.getRut());
                    firstLine = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando consultas: " + e.getMessage());
        }
    }
}
