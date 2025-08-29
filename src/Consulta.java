public class Consulta {
    int id;
    private String fecha;
    private String motivo;
    private String sala;
    private Paciente paciente;
    private Medico medico;

    public Consulta(){
    }

    public Consulta(int id, String fecha, String motivo, String sala, Paciente paciente, Medico medico) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.sala = sala;
        this.paciente = paciente;
        this.medico = medico;
    }

    public int getId(){
        return id;
    }

    public String getSala(){
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

    public Medico getMedico() {
        return medico;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setSala(String sala){
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

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}