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
        setBounds(100, 100, 1000, 800); // ridotto leggermente per stare nello schermo

        int tileSize = 50;
        
        KeyHandler keyH = new KeyHandler();
        
        GameWorld world = new GameWorld(tileSize);
        
        Player player = new Player("ez", 100, 1, 25*tileSize, 25*tileSize, tileSize, tileSize, "Player");
        
        MapPanel mapPanel = new MapPanel(keyH, world, player);
        setContentPane(mapPanel);

        
        
        // Ciclo di gioco a ~60 FPS per aggiornare posizione e schermo
        javax.swing.Timer timer = new javax.swing.Timer(16, e -> {
        	mapPanel.repaint();
        	player.updatePlayer(keyH, world);
        });
        timer.start();
    }
    
}
