import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Hospital {
    private String nombre;
    private String direccion;
    private String telefono;
    private ArrayList<Consulta> consultas;
    private HashMap<String, Paciente> pacientes;
    private ArrayList<Medico> medicos;

    public Hospital(){
    }

    public Hospital(String nombre, String direccion, String telefono){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        consultas = new ArrayList<>();
        pacientes = new HashMap<>();
        medicos = new ArrayList<>();
    }

    public ArrayList<Consulta> getConsultas(){
        return consultas;
    }

    public Collection<Paciente> getPacientes(){
        return pacientes.values();
    }

    public ArrayList<Medico> getMedicos(){
        return medicos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion(){
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void agregarConsulta(Consulta consulta){
        consultas.add(consulta);
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

    public boolean removePaciente(String rut){
        return pacientes.remove(rut) != null;
    }

    public boolean removeMedico(Medico medico){
        return medicos.remove(medico);
    }

    public boolean removeConsulta(Consulta consulta){
        return consultas.remove(consulta);
    }

    public Paciente buscarPaciente(String rut){
        return pacientes.get(rut);
    }
}
