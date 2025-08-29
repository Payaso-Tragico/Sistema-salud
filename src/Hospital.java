import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class Hospital {
    private String nombre;
    private String direccion;
    private String telefono;
    private HashMap<String, Paciente> pacientes;
    private ArrayList<Medico> medicos;

    public Hospital(){
    }

    public Hospital(String nombre, String direccion, String telefono){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        pacientes = new HashMap<>();
        medicos = new ArrayList<>();
    }

    public Collection<Paciente> getPacientes(){
        return pacientes.values();
    }

    public ArrayList<Medico> getMedicos(){
        return medicos;
    }

    public String getNombre() { return nombre; }

    public String getDireccion(){ return direccion; }

    public String getTelefono() {
        return telefono;
    }

    public void agregarPaciente(Paciente paciente){
        pacientes.put(paciente.getRut(), paciente);
    }

    public void agregarMedico(Medico medico){
        medicos.add(medico);
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public boolean removePaciente(String rut){ return pacientes.remove(rut) != null; }

    public boolean removeMedico(Medico medico){ return medicos.remove(medico); }

    public Paciente buscarPaciente(String rut){
        return pacientes.get(rut);
    }

    /*MÉTODOS APLICADOS SOBRE "MÉDICO"*/
    public Medico buscarMedico(Scanner sc){
        String rut;

        System.out.print("Ingrese RUT del médico objetivo: ");
        rut = sc.nextLine();

        for(Medico m : this.getMedicos()){
            if(m.getRut().equals(rut)){
                return m;
            }
        }
        return null;
    }

    public void agregarMedico(Scanner sc){
        String rut, nombre, especialidad;

        System.out.print("Ingrese RUT del médico: ");
        rut = sc.nextLine();
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        System.out.print("Ingrese especialidad: ");
        especialidad = sc.nextLine();

        Medico medico = new Medico(rut, nombre, especialidad);
        agregarMedico(medico);
    }

    public void printMedico(Scanner sc){
        Medico m = buscarMedico(sc);

        if(m != null){
            System.out.println("Médico encontrado:");
            System.out.println("RUT: " +m.getRut());
            System.out.println("Nombre: " +m.getNombre());
            System.out.println("Especialidad: " +m.getEspecialidad());
        }else{
            System.out.println("Médico no encontrado.");
        }
    }

    public void printMedico(Medico m){
        if(m != null){
            System.out.println("\n--- DATOS DEL MÉDICO ---");
            System.out.println("RUT: " +m.getRut());
            System.out.println("Nombre: " +m.getNombre());
            System.out.println("Especialidad: " +m.getEspecialidad());
        }else{
            System.out.println("Médico no encontrado.");
        }
    }

    //El otro metodo con sobrecarga. este imprime todas las consultas asociadas al medico buscado.
    public void printMedico(String nombre){
        /*CAMBIARLO POR UN BUSCAR POR NOMBRE*/
        for(Medico m : getMedicos()){
            if(m.getNombre().equals(nombre)){
                printMedico(m);
            }
        }

    }

    public void listarMedicos(){
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : getMedicos()){
            System.out.println("RUT: " +m.getRut() +" | Nombre: " +m.getNombre() +" | Especialidad: " +m.getEspecialidad());
        }
    }

    public void modificarMedico(Scanner sc){
        String nombre, especialidad;
        Medico m = buscarMedico(sc);

        if(m != null){
            System.out.print("Ingrese nuevo nombre: ");
            nombre = sc.nextLine();
            m.setNombre(nombre);

            System.out.print("Ingrese nueva especialidad: ");
            especialidad = sc.nextLine();
            m.setEspecialidad(especialidad);

        }else{
            System.out.println("El medico no existe.");
        }
    }

    public void eliminarMedico(Scanner sc){
        Medico m = buscarMedico(sc);

        if(m != null){
            getMedicos().remove(m);
        } else {
            System.out.println("El medico no existe.");
        }
    }
    /*-----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODOS PARA CONSULTAS*/
    public void agregarConsulta(Scanner sc){
        int id;
        String fecha, motivo, sala;

        System.out.print("Ingrese ID de la consulta: ");
        id = sc.nextInt();
        sc.nextLine();

        System.out.print("Ingrese fecha de la consulta (DD-MM-YYYY): ");
        fecha = sc.nextLine();

        System.out.print("Ingrese motivo de la consulta: ");
        motivo = sc.nextLine();

        System.out.print("Ingrese sala: ");
        sala = sc.nextLine();

        Paciente paciente = buscarPaciente(sc);
        if(paciente == null){
            System.out.println("El paciente no existe.");
            return;
        }

        Medico medico = buscarMedico(sc);
        if(medico == null){
            System.out.println("El medico no existe.");
            return;
        }

        Consulta consulta = new Consulta(id, fecha, motivo, sala,paciente);
        agregarConsulta(medico.getRut(), consulta);
    }

    public void agregarConsulta(String rut, Consulta consulta){
        for(Medico m : getMedicos()){
            if(m.getRut().equals(rut)){
                m.getConsultas().add(consulta);
            }
        }
    }

    public Consulta buscarConsulta(Scanner sc){
        int id;

        System.out.print("Ingrese ID de la consulta: ");
        id = sc.nextInt();
        sc.nextLine();

        for(Medico m : getMedicos()){
            for(Consulta c : m.getConsultas()){
                if(id == c.getId()){
                    return c;
                }
            }
        }
        return null;
    }

    public void listarConsultas() {
        System.out.println("\n--- LISTA DE CONSULTAS ---");
        for(Medico m : getMedicos()){
            for(Consulta c : m.getConsultas()){
                System.out.println("ID: " +c.getId() +" | Paciente: " +c.getPaciente().getNombre() +" | Médico: " +m.getNombre());
            }

        }
    }

    public void modificarConsulta(Scanner sc) {
        Consulta c = buscarConsulta(sc);

        if(c != null){
            System.out.print("Nueva fecha: ");
            String fecha = sc.nextLine();
            c.setFecha(fecha);

            System.out.print("Nuevo motivo: ");
            String motivo = sc.nextLine();
            c.setMotivo(motivo);

            System.out.print("Nueva sala: ");
            String sala = sc.nextLine();
            c.setSala(sala);

            System.out.println("Seleccionar nuevo paciente:");
            Paciente paciente = buscarPaciente(sc);
            if(paciente != null) {
                c.setPaciente(paciente);
            }

            /*TENGO MIS DUDAS SOBRE ESTO*/
            System.out.println("Igrese el RUT del nuevo medico:");
            String rut = sc.nextLine();
            for(Medico m : getMedicos()){
                if(m.getRut().equals(rut)){
                    for(Medico med : getMedicos()){
                        med.getConsultas().remove(c);
                    }
                    m.getConsultas().add(c);
                }
            }

        } else {
            System.out.println("La consulta no existe.");
        }
    }

    //La función de sobrecarga. la idea es que funciona como una especie de "reagendación"
    public void modificarConsulta(int id, String fecha){
        for(Medico m : getMedicos()) {
            for (Consulta c : m.getConsultas()) {
                if (c.getId() == id) {
                    c.setFecha(fecha);
                    System.out.println("Fecha de la consulta ID " + id + " actualizada a " + fecha);
                    return;
                }
            }
        }
        System.out.println("Consulta no encontrada.");
    }

    public void opcionModificarConsulta(Scanner sc){
        int opcion, id;
        String fecha;
        System.out.print("¿Desea solo reagendar la fecha de la consulta? (1 = Sí, 0 = No): ");
        opcion = sc.nextInt();
        sc.nextLine();

        if(opcion == 1){
            System.out.print("Ingrese ID de la consulta: ");
            id = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nueva fecha (DD-MM-YYYY): ");
            fecha = sc.nextLine();
            modificarConsulta(id, fecha);
        }else{
            modificarConsulta(sc);
        }
    }

    public void eliminarConsulta(Scanner sc){
        Consulta c = buscarConsulta(sc);

        for(Medico m : getMedicos()){
            m.getConsultas().remove(c);
        }
    }

    public void printConsulta(Consulta c){
        Medico med = null;

        for(Medico m : getMedicos()){
            for(Consulta consulta : m.getConsultas()){
                if(consulta.getId() == c.getId()){
                    med = m;
                }
            }
        }


        if (med != null && c != null) {
            System.out.println("CONSULTA:" + c.getId());
            System.out.println("Fecha: " + c.getFecha());
            System.out.println("Motivo: " + c.getMotivo());
            System.out.println("Sala: " + c.getSala());
            System.out.println("Paciente: " + c.getPaciente().getNombre() + " (RUT: " + c.getPaciente().getRut() + ")");
            System.out.println("Médico: " + med.getNombre() + " (RUT: " + med.getRut() + ")");
        } else {
            System.out.println("Consulta no encontrada.");
        }

    }
    /*----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODOS PARA PACIENTES*/
    public void agregarPaciente(Scanner sc){
        String rut, nombre, diagnostico;
        int edad;

        System.out.print("Ingrese RUT del paciente: ");
        rut = sc.nextLine();
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        System.out.print("Ingrese edad: ");
        edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese diagnóstico: ");
        diagnostico = sc.nextLine();

        Paciente paciente = new Paciente(rut, nombre, edad, diagnostico);
        agregarPaciente(paciente);
    }

    public Paciente buscarPaciente(Scanner sc){
        System.out.print("Ingrese RUT del paciente objetivo: ");
        String rut = sc.nextLine();

        for (Paciente p : getPacientes()) {
            if (p.getRut().equals(rut)) {
                return p;
            }
        }
        return null;
    }

    public void printPaciente(Scanner sc){
        Paciente p = buscarPaciente(sc);

        if (p != null) {
            System.out.println("Paciente encontrado:");
            System.out.println("RUT: " + p.getRut());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Edad: " + p.getEdad());
            System.out.println("Diagnóstico: " + p.getDiagnostico());
        } else {
            System.out.println("El paciente no existe.");
        }
    }

    public void eliminarPaciente(Scanner sc){
        Paciente p = buscarPaciente(sc);

        if(p != null){
            removePaciente(p.getRut());
        } else {
            System.out.println("El paciente no existe.");
        }
    }

    public void listarPacientes(){
        System.out.println("\n--- LISTA DE PACIENTES ---");
        for(Paciente p : getPacientes()) {
            System.out.println("RUT: " + p.getRut() + " | Nombre: " + p.getNombre() + " | Edad: " + p.getEdad() + " | Diagnóstico: " + p.getDiagnostico());
        }
    }

    public void modificarPaciente(Scanner sc){
        Paciente p = buscarPaciente(sc);

        if (p != null) {
            System.out.print("Ingrese nuevo nombre: ");
            String nombre = sc.nextLine();
            p.setNombre(nombre);

            System.out.print("Ingrese nueva edad: ");
            int edad = sc.nextInt();
            sc.nextLine();
            p.setEdad(edad);

            System.out.print("Ingrese nuevo diagnóstico: ");
            String diag = sc.nextLine();
            p.setDiagnostico(diag);

        } else {
            System.out.println("El paciente no existe.");
        }
    }
}