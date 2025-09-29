import java.util.Scanner;
import javax.swing.*;

public class Main {
public static void main(String[] args) throws EntidadExistenteException {
    Hospital hospitalCentral = Cargador.cargarHospital("src/main/resources/data/hospital.txt");
    Cargador.cargarPacientes(hospitalCentral, "src/main/resources/data/pacientes.txt");
    Cargador.cargarMedicos(hospitalCentral, "src/main/resources/data/medicos.txt");
    Cargador.cargarConsultas(hospitalCentral, "src/main/resources/data/consultas.txt");

    SwingUtilities.invokeLater(() -> {VentanaPrincipal ventana = new VentanaPrincipal(hospitalCentral);

    ventana.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            Cargador.guardarHospital(hospitalCentral, "src/main/resources/data/hospital.txt");
            Cargador.guardarPacientes(hospitalCentral, "src/main/resources/data/pacientes.txt");
            Cargador.guardarMedicos(hospitalCentral, "src/main/resources/data/medicos.txt");
            Cargador.guardarConsultas(hospitalCentral, "src/main/resources/data/consultas.txt");
            System.exit(0);
        }
    });
    ventana.setVisible(true);
});
}

}