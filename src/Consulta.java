public class Consulta {
    private int id;
    private String fecha;
    private String motivo;
    private String sala;
    private Paciente paciente;

    public Consulta(){
    }

    public Consulta(int id, String fecha, String motivo, String sala, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.sala = sala;
        this.paciente = paciente;
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

}