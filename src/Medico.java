public class Medico {
    private String rut;
    private String nombre;
    private String especialidad;

    public Medico(){
    }

    public Medico(String rut, String nombre, String especialidad){
        this.rut = rut;
        this.nombre = nombre;
        this.especialidad = especialidad;
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
}


