import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

   public void agregarPaciente(Paciente paciente) throws FormatoInvalidoException, EntidadExistenteException {
    Utilidad.validarRut(paciente.getRut());

    if (pacientes.containsKey(paciente.getRut())) {
        throw new EntidadExistenteException(
            "El paciente con RUT " + paciente.getRut() + " ya existe en el hospital."
        );
    }

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
    
    public Medico buscarMedicoPorRut(String rut) {
        if (rut == null) return null;
        String clave = rut.trim();
        for (Medico m : this.getMedicos()) {
            if (m.getRut() != null && m.getRut().trim().equalsIgnoreCase(clave)) {
                return m;
            }
        }
        return null;
    }    

    public Medico buscarMedico(Scanner sc) throws NoEncontradoException{
        String rut;
        rut = sc.nextLine();

        for(Medico m : this.getMedicos()){
            if(m.getRut().equals(rut)){
                return m;
            }
        }
        throw new NoEncontradoException("Error: ");
    }
    
    public Medico buscarMedico(String nombre) throws NoEncontradoException{
        for (Medico m : this.getMedicos()) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
                }
            }
        throw new NoEncontradoException("Error: ");
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

    public void agregarMedico(Scanner sc) throws FormatoInvalidoException, EntidadExistenteException {
        System.out.print("Ingrese RUT del médico: ");
        String rut = sc.nextLine().trim();
        Utilidad.validarRut(rut); 

        if (medicos.stream().anyMatch(m -> m.getRut().equalsIgnoreCase(rut))) {
            throw new EntidadExistenteException("El médico con RUT " + rut + " ya existe en el hospital.");
        }

        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese especialidad: ");
        String especialidad = sc.nextLine();

        Medico medico = new Medico(rut, nombre, especialidad);
        HospitalLogger.registrar(new Reporte("Médico", "AGREGADO", medico.toString()));
        agregarMedico(medico);
    }


    public void listarMedicos(){
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : getMedicos()){
            System.out.println("RUT: " +m.getRut() +" | Nombre: " +m.getNombre() +" | Especialidad: " +m.getEspecialidad());
        }
    }

    public void eliminarMedico(Scanner sc) throws NoEncontradoException {
        System.out.print("Ingrese RUT del médico a eliminar: ");
        String rut = sc.nextLine();

        Medico m = buscarMedicoPorRut(rut);
        if (m == null) throw new NoEncontradoException("Médico no encontrado");

        getMedicos().remove(m);
        HospitalLogger.registrar(new Reporte("Médico", "ELIMINADO", m.toString()));
    }
    
    public void modificarMedico(Scanner sc) throws FormatoInvalidoException, NoEncontradoException {
        System.out.print("Ingrese RUT del médico a modificar: ");
        String rut = sc.nextLine();

        Medico m = buscarMedicoPorRut(rut);
        if (m == null) throw new NoEncontradoException("Médico no encontrado");

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nueva especialidad: ");
        String especialidad = sc.nextLine();

        m.setNombre(nombre);
        m.setEspecialidad(especialidad);
        HospitalLogger.registrar(new Reporte("Médico", "MODIFICADO", m.toString()));
    }
    
    /*-----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODOS PARA CONSULTAS*/
    
    public Map<Consulta, Medico> obtenerConsultasPorFecha(String fecha) {
        Map<Consulta, Medico> resultado = new HashMap<>(); 
        for (Medico m : getMedicos()) {
            for (Consulta c : m.getConsultas()) {
                if (c.getFecha().equals(fecha.trim())) {
                    resultado.put(c, m);
                }
            }
        }
        return resultado;
    }


    
    public void agregarConsulta(int id, String fecha, String motivo, String sala, String rutPaciente, String rutMedico)
            throws NoEncontradoException, EntidadExistenteException {

        Paciente paciente = buscarPacientePorRut(rutPaciente);
        if (paciente == null) throw new NoEncontradoException("Paciente con RUT " + rutPaciente + " no encontrado.");

        Medico medico = buscarMedicoPorRut(rutMedico);
        if (medico == null) throw new NoEncontradoException("Médico con RUT " + rutMedico + " no encontrado.");

        for (Medico m : getMedicos()) {
            for (Consulta c : m.getConsultas()) {
                if (c.getId() == id) {
                    throw new EntidadExistenteException("Ya existe una consulta con ID " + id + " en el hospital.");
                }
            }
        }

        Consulta c = new Consulta(id, fecha, motivo, sala, paciente);
        medico.getConsultas().add(c);
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
    
    public List<Paciente> obtenerPacientesPorMedico(String rutMedico) {
        List<Paciente> listPacientes = new ArrayList<>();

        for (Medico m : medicos) {
            if (m.getRut().equals(rutMedico)) {
                for (Consulta c : m.getConsultas()) {
                    listPacientes.add(c.getPaciente());
                }
                break; 
            }
        }

        return listPacientes;
    }
    
    public Paciente buscarPacientePorRut(String rut) throws NoEncontradoException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new NoEncontradoException("RUT vacío o nulo.");
        }
        String clave = rut.trim();
        for (Paciente p : this.getPacientes()) {
            if (p.getRut() != null && p.getRut().trim().equalsIgnoreCase(clave)) {
                return p;
            }
        }
        throw new NoEncontradoException("Paciente con RUT " + rut + " no encontrado.");
    }

    
    public Paciente buscarPaciente(Scanner sc) throws NoEncontradoException {
        System.out.print("Ingrese RUT del paciente objetivo: ");
        String rut = sc.nextLine();
        Paciente p = buscarPacientePorRut(rut);
        if (p == null) {
            throw new NoEncontradoException("Paciente con RUT " + rut + " no encontrado.");
        }
        return p;
    }

    public void eliminarPaciente(Scanner sc) throws NoEncontradoException{
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