import java.util.Scanner;

public class Persona {
    private String nombre;
    private String rut;

    public Persona(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre + ", RUT: " + rut);
    }
    
    public void modificar(Scanner sc) {
        System.out.print("Ingrese nuevo nombre: ");
        this.nombre = sc.nextLine();
    }   
    
    public String printDatos() {
        return "RUT: " + getRut() + "\n" +
               "Nombre: " + getNombre();
    }
}
