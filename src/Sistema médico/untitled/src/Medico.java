import java.util.ArrayList;
import java.util.Scanner;

public class Medico {
    private String rut;
    private String nombre;
    private String especialidad;
    private ArrayList<Consulta> consultas;

    public Medico(){
    }

    public Medico(String rut, String nombre, String especialidad){
        this.rut = rut;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.consultas = new ArrayList<>();
    }

    public ArrayList<Consulta> getConsultas(){
        return consultas;
    }

    public String getRut(){
        return rut;
    }

    public String getNombre(){
        return nombre;
    }

    public String getEspecialidad(){
        return especialidad;
    }

    public void setRut(String rut){
        this.rut = rut;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

    public void modificarMedico(Scanner sc){
        String nombre, especialidad;

        System.out.print("Ingrese nuevo nombre: ");
        nombre = sc.nextLine();
        setNombre(nombre);

        System.out.print("Ingrese nueva especialidad: ");
        especialidad = sc.nextLine();
        setEspecialidad(especialidad);
    }
}


