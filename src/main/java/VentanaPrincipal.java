import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private Hospital hospital;
    private JPanel panelPrincipal; 
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    public VentanaPrincipal(Hospital hospital) {
        this.hospital = hospital;
        setTitle("Sistema Hospitalario");
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 350));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarPanelPrincipal();

        setVisible(true);
    }

    private void inicializarPanelPrincipal() {
        int anchoBoton = 200;
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnPacientes = new JButton("Pacientes");
        JButton btnMedicos = new JButton("Médicos");
        JButton btnConsultas = new JButton("Consultas");


        JButton[] botones = {btnPacientes, btnMedicos, btnConsultas};
        for (JButton btn : botones) {
            btn.setMaximumSize(new Dimension(anchoBoton, btn.getPreferredSize().height));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelPrincipal.add(btn);
            panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(panelPrincipal);

        btnPacientes.addActionListener(e -> mostrarPanelPacientes());
        btnMedicos.addActionListener(e -> mostrarPanelMedicos());
        btnConsultas.addActionListener(e -> mostrarPanelConsultas());
    }

    private void mostrarPanelPacientes() {
        getContentPane().removeAll();
        add(new PanelPacientes(hospital, this));
        revalidate();
        repaint();
    }

    private void mostrarPanelMedicos() {
        getContentPane().removeAll();
        add(new PanelMedicos(hospital, this));
        revalidate();
        repaint();
    }

    private void mostrarPanelConsultas() {
        getContentPane().removeAll();
        add(new PanelConsultas(hospital, this));
        revalidate();
        repaint();
    }

    public void volverAlMenuPrincipal() {
        getContentPane().removeAll();
        add(panelPrincipal);
        revalidate();
        repaint();
    }
}





