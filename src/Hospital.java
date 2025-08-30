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
        System.out.print("El medico no existe. ");
        return null;
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

    /*BUSQUEDA POR NOMBRE, SOBRECARGA 1*/
    public void printMedico(String nombre){
        for(Medico m : getMedicos()){
            if(m.getNombre().equals(nombre)){
                printMedico(m);
            }
        }

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

    public void listarMedicos(){
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : getMedicos()){
            System.out.println("RUT: " +m.getRut() +" | Nombre: " +m.getNombre() +" | Especialidad: " +m.getEspecialidad());
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

        id = Utilidad.leerEntero(sc,"Ingrese ID de la consulta: " );

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

        id = Utilidad.leerEntero(sc,"Ingrese ID de la consulta: ");
        sc.nextLine();

        for(Medico m : getMedicos()){
            for(Consulta c : m.getConsultas()){
                if(id == c.getId()){
                    return c;
                }
            }
        }
        System.out.print("La consulta no existe.");
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

    public void eliminarConsulta(Scanner sc){
        Consulta c = buscarConsulta(sc);

        for(Medico m : getMedicos()){
            m.getConsultas().remove(c);
        }
    }

    public void printConsulta(Consulta c){
        Medico med = null;

        if(c != null) {
            for (Medico m : getMedicos()) {
                for (Consulta consulta : m.getConsultas()) {
                    if (consulta.getId() == c.getId()) {
                        med = m;
                    }
                }
            }

            if (med != null) {
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
        edad = Utilidad.leerEntero(sc, "Ingrese edad: ");
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
        System.out.print("El paciente no existe.");
        return null;
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

}
