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
    
    /*-----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODO PARA VALIDAR RUT"*/
    public static void validarRut(String rut) throws FormatoInvalidoException {
        String regex = "\\d{7,8}-[\\dkK]";       
        if (!rut.matches(regex)) {
            throw new FormatoInvalidoException("RUT inválido: " + rut + ". Debe tener formato 21316835-5");
        }
    }   
    
    /*-----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODOS APLICADOS SOBRE "MÉDICO"*/
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

    public void agregarMedico(Scanner sc){
        String rut = null, nombre, especialidad;
        try{
        System.out.print("Ingrese RUT del médico: ");
        rut = sc.nextLine();
        validarRut(rut);
        }catch(FormatoInvalidoException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        System.out.print("Ingrese especialidad: ");
        especialidad = sc.nextLine();

        Medico medico = new Medico(rut, nombre, especialidad);
        Logger.registrar(new Reporte("Médico", "AGREGADO", medico.toString()));
        agregarMedico(medico);
    }

    public void listarMedicos(){
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : getMedicos()){
            System.out.println("RUT: " +m.getRut() +" | Nombre: " +m.getNombre() +" | Especialidad: " +m.getEspecialidad());
        }
    }

    public void eliminarMedico(Scanner sc){
        try{
            System.out.print("Ingrese RUT del médico a Elimar: ");
            Medico m = buscarMedico(sc);                
            getMedicos().remove(m);  
            System.out.print("Médico eliminado exitosamente");      
            Logger.registrar(new Reporte("Médico", "ELIMINADO", m.toString()));
        } catch (NoEncontradoException e){
                        System.out.println(e.getMessage() + "El medico a eliminar no existe.");
                    } 
    }
    /*-----------------------------------------------------------------------------------------------------------------*/
    /*MÉTODOS PARA CONSULTAS*/
    public void agregarConsulta(Scanner sc){
        int id;
        String fecha, motivo, sala;
        Paciente paciente = null;
        id = Utilidad.leerEntero(sc,"Ingrese ID de la consulta: " );

        System.out.print("Ingrese fecha de la consulta (DD-MM-YYYY): ");
        fecha = sc.nextLine();

        System.out.print("Ingrese motivo de la consulta: ");
        motivo = sc.nextLine();

        System.out.print("Ingrese sala: ");
        sala = sc.nextLine();
        
        
        try{
        paciente = buscarPaciente(sc);    
        }catch (NoEncontradoException e){
                System.out.println(e.getMessage() + "el paciente buscado no existe.");
            }         
        
        try{
        Medico medico = buscarMedico(sc);        
        Consulta consulta = new Consulta(id, fecha, motivo, sala, paciente);
        agregarConsulta(medico.getRut(), consulta);
        Logger.registrar(new Reporte("Consulta", "AGREGADO", consulta.toString()));
        } catch (NoEncontradoException e){
                        System.out.println(e.getMessage() + "el médico buscado no existe.");
                    } 
        
    }

    public void agregarConsulta(String rut, Consulta consulta){
        for(Medico m : getMedicos()){
            if(m.getRut().equals(rut)){
                m.getConsultas().add(consulta);
            }
        }
    }

    public Consulta buscarConsulta(Scanner sc) throws NoEncontradoException{
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
        throw new NoEncontradoException("Error: ");
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
        try{
        Consulta c = buscarConsulta(sc);
        for(Medico m : getMedicos()){
            m.getConsultas().remove(c);
            Logger.registrar(new Reporte("Consulta", "ELIMINADO", c.toString()));
        }
        }catch (NoEncontradoException e){
            System.out.println(e.getMessage() + "el Consulta buscado no existe.");
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
        String rut = null, nombre, diagnostico;
        int edad;
        try{
        System.out.print("Ingrese RUT del paciente: ");
        rut = sc.nextLine();
        validarRut(rut);
        }catch (FormatoInvalidoException e) {
        System.out.println(e.getMessage());
        return;
        }
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        edad = Utilidad.leerEntero(sc, "Ingrese edad: ");        
        System.out.print("Ingrese diagnóstico: ");
        diagnostico = sc.nextLine();

        Paciente paciente = new Paciente(rut, nombre, edad, diagnostico);
        agregarPaciente(paciente);        
        Logger.registrar(new Reporte("Paciente", "AGREGADO", paciente.toString()));
    }

    public Paciente buscarPaciente(Scanner sc) throws NoEncontradoException{
        System.out.print("Ingrese RUT del paciente objetivo: ");
        String rut = sc.nextLine();

        for (Paciente p : getPacientes()) {
            if (p.getRut().equals(rut)) {
                return p;
            }
        }
        throw new NoEncontradoException("Error:");
    }

    public void eliminarPaciente(Scanner sc){    
    try{
        Paciente p = buscarPaciente(sc);
        if(p != null){
            removePaciente(p.getRut());
            Logger.registrar(new Reporte("Paciente", "ELIMINADO", p.toString()));
        }
        }catch (NoEncontradoException e){
                System.out.print(e.getMessage() + "paciente no encontrado");
            }
    }
    

    public void listarPacientes(){
        System.out.println("\n--- LISTA DE PACIENTES ---");
        for(Paciente p : getPacientes()) {
            System.out.println("RUT: " + p.getRut() + " | Nombre: " + p.getNombre() + " | Edad: " + p.getEdad() + " | Diagnóstico: " + p.getDiagnostico());
        }
    }

}
