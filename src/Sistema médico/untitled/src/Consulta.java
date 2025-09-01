import java.util.Scanner;

public class Consulta {
    private int id;
    private String fecha;
    private String motivo;
    private String sala;
    private Paciente paciente;

    public Consulta() {
    }

    public Consulta(int id, String fecha, String motivo, String sala, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.sala = sala;
        this.paciente = paciente;
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

    public void modificarConsulta(Hospital h, Scanner sc) {

        System.out.print("Nueva fecha: ");
        String fecha = sc.nextLine();
        setFecha(fecha);

        System.out.print("Nuevo motivo: ");
        String motivo = sc.nextLine();
        setMotivo(motivo);

        System.out.print("Nueva sala: ");
        String sala = sc.nextLine();
        setSala(sala);

        System.out.println("Seleccionar nuevo paciente:");
        Paciente paciente = h.buscarPaciente(sc);
        if (paciente != null) {
            setPaciente(paciente);
        }

        System.out.println("Igrese el RUT del nuevo medico:");
        String rut = sc.nextLine();
        for (Medico m : h.getMedicos()) {
            if (m.getRut().equals(rut)) {
                for (Medico med : h.getMedicos()) {
                    med.getConsultas().remove(this);
                }
                m.getConsultas().add(this);
            }
        }
    }

    //La función de sobrecarga. la idea es que funciona como una especie de "reagendación"
    public void modificarConsulta(Scanner sc) {
        String fecha;
        System.out.println("Ingrese la fecha de la nueva consulta");
        fecha = sc.nextLine();
        setFecha(fecha);
        System.out.println("Fecha de la consulta con ID: " + getId() + " actualizada a " + fecha);
    }
}
