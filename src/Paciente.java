import java.util.Scanner;

public class Paciente extends Persona {
    private int edad;
    private String diagnostico;

    public Paciente(String rut, String nombre, int edad, String diagnostico){
        super(rut, nombre);
        this.edad = edad;
        this.diagnostico = diagnostico;
    }

    public int getEdad(){
        return edad;
    }

    public String getDiagnostico(){
        return diagnostico;
    }


    public void setEdad(int edad){
        this.edad = edad;
    }

    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }

    @Override
    public void printDatos(){
        System.out.println("Paciente encontrado:");
        System.out.println("RUT: " + getRut());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Edad: " + getEdad());
        System.out.println("Diagnóstico: " + getDiagnostico());
    }
    
    @Override
    public void modificar(Scanner sc) {
        super.modificar(sc);
        System.out.println("Ingrese nueva edad: ");
        sc.nextLine();
        setEdad(edad);
        System.out.print("Ingrese nuevo diagnóstico: ");
        String diag = sc.nextLine();
        setDiagnostico(diag);
    }
    
    @Override
    public String toString() {
        return getNombre() + "," + getRut() + "," + getEdad() + "," + getDiagnostico();
    }

}