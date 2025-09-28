import java.util.ArrayList;
import java.util.Scanner;

public class Medico extends Persona{
    
    private String especialidad;
    private ArrayList<Consulta> consultas;

    public Medico(String rut, String nombre, String especialidad){
        super(rut,nombre);
        this.especialidad = especialidad;
        this.consultas = new ArrayList<>();
    }

    public ArrayList<Consulta> getConsultas(){
        return consultas;
    }

    public String getEspecialidad(){
        return especialidad;
    }

    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

    @Override
    public void printDatos(){        
        System.out.println("\n--- DATOS DEL MÉDICO ---");
        System.out.println("RUT: " + getRut());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Especialidad: " + getEspecialidad());
    }
    
    @Override
    public void modificar(Scanner sc){
        super.modificar(sc);
        System.out.print("Ingrese nueva especialidad: ");
        especialidad = sc.nextLine();
    }
    
    @Override
    public String toString() {
        return getNombre() + "," + getRut() + "," + getEspecialidad(); 
    }

}



