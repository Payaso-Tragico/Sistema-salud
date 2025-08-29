public class Paciente {
    private String rut;
    private String nombre;
    private int edad;
    private String diagnostico;

    public Paciente(){
    }

    public Paciente(String rut, String nombre, int edad, String diagnostico){
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.diagnostico = diagnostico;
    }

    public String getRut(){
        return rut;
    }

    public String getNombre(){
        return nombre;
    }

    public int getEdad(){
        return edad;
    }

    public String getDiagnostico(){
        return diagnostico;
    }

    public void setRut(String rut){
        this.rut = rut;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }

    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }
}