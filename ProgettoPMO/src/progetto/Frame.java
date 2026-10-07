package progetto;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {

                    Frame frame = new Frame();

                    frame.setVisible(true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public Frame() {

        // Chiude il programma quando chiudi la finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Posizione e dimensione della finestra
        setBounds(100, 100, 2500, 1464);

        // Creiamo il pannello della mappa
        contentPane = new MapPanel();

        // Bordo del pannello
        contentPane.setBorder(
            new EmptyBorder(5, 5, 5, 5)
        );

        // Inseriamo il pannello nella finestra
        setContentPane(contentPane);

        // Per ora lasciamo il layout assoluto
        contentPane.setLayout(null);
    }
    //prova
}
