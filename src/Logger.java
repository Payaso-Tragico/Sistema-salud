import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String ARCHIVO = "hospital_log.txt";
    
    public static void registrar(Reporte accion) {
        try (FileWriter writer = new FileWriter(ARCHIVO, true)) { 
            writer.write(accion.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error:");
        }
    }
}