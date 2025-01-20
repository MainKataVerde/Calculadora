import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Calse que se encarga de mostrar una ventana emergente
 */
public class VentanaEmergente {
    private JDialog dialog;
    private JLabel texto;

    /**
     * Constructor de la clase
     * 
     * @param Ventana Titulo de la ventana
     * @param mensaje Mensaje que se mostrara en la ventana
     */
    public VentanaEmergente(String Ventana, String mensaje) {
        dialog = new JDialog();
        texto = new JLabel(mensaje);
        dialog.setLayout(new BorderLayout());
        dialog.setTitle(Ventana);
        dialog.setSize(500, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        texto.setFont(new Font("", Font.BOLD, 20));
        texto.setForeground(new Color(18, 198, 0));
        dialog.add(texto, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
