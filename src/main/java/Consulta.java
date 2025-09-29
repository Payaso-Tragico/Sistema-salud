import java.util.Scanner;

public class Consulta {
    private int id;
    private String fecha;
    private String motivo;
    private String sala;
    private Paciente paciente;

    public Consulta(int id, String fecha, String motivo, String sala, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.sala = sala;
        this.paciente = paciente;
    }    
    
    public Consulta() {
    }

    public int getId() {
        return id;
    }

    public String getSala() {
        return sala;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    
    public void modificarConsulta(Hospital h, String fecha, String motivo, String sala, String rutPaciente, String rutMedico) throws NoEncontradoException {
        if (fecha == null) fecha = "";
        if (motivo == null) motivo = "";
        if (sala == null) sala = "";
        if (rutPaciente == null) rutPaciente = "";
        if (rutMedico == null) rutMedico = "";

        Paciente paciente = h.buscarPacientePorRut(rutPaciente);

        Medico nuevoMedico = null;
        for (Medico m : h.getMedicos()) {
            if (m.getRut() != null && m.getRut().trim().equalsIgnoreCase(rutMedico.trim())) {
                nuevoMedico = m;
                break;
            }
        }
        if (nuevoMedico == null) {
            throw new NoEncontradoException("Médico con RUT " + rutMedico + " no encontrado.");
        }

        setFecha(fecha.trim());
        setMotivo(motivo.trim());
        setSala(sala.trim());
        setPaciente(paciente);

        for (Medico m : h.getMedicos()) {
            m.getConsultas().remove(this);
        }

        nuevoMedico.getConsultas().add(this);
    }

    public void modificarConsulta(Scanner sc) {
        String fecha;
        System.out.println("Ingrese la fecha de la nueva consulta");
        fecha = sc.nextLine();
        setFecha(fecha);
        System.out.println("Fecha de la consulta con ID: " + getId() + " actualizada a " + fecha);        
    }
    
    @Override
    public String toString() {
        return getId() + "," + getSala() + "," + getFecha() + "," + getMotivo(); 
    }
}
