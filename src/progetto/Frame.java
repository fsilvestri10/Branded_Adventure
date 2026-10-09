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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800); 

        MapPanel mapPanel = new MapPanel();
        setContentPane(mapPanel);

        // Ciclo di gioco a 60 FPS per aggiornare posizione e schermo
        javax.swing.Timer timer = new javax.swing.Timer(16, e -> {
            mapPanel.updatePlayer();
            mapPanel.repaint();
        });
        timer.start();
    }
}
