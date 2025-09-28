
public class Reporte {
    private String tipo;      
    private String operacion; 
    private String detalle;   
    private String fechaHora; 

    public Reporte(String tipo, String operacion, String detalle) {
        this.tipo = tipo;
        this.operacion = operacion;
        this.detalle = detalle;
        this.fechaHora = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return fechaHora + " | " + tipo + " | " + operacion + " | " + detalle;
    }
}
