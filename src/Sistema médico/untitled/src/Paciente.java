import java.util.Scanner;

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

    public void printPaciente(){
        System.out.println("Paciente encontrado:");
        System.out.println("RUT: " + getRut());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Edad: " + getEdad());
        System.out.println("Diagnóstico: " + getDiagnostico());
    }

    public void modificarPaciente(Scanner sc) {

        System.out.print("Ingrese nuevo nombre: ");
        String nombre = sc.nextLine();
        setNombre(nombre);

        int edad = Utilidad.leerEntero(sc,"Ingrese nueva edad: ");
        sc.nextLine();
        setEdad(edad);

        System.out.print("Ingrese nuevo diagnóstico: ");
        String diag = sc.nextLine();
        setDiagnostico(diag);
    }
}
