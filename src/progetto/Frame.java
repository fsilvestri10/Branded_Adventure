package progetto;
import java.awt.EventQueue;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    
    protected FightPanel fightPanel = null;
    

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

    public Frame() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800); // ridotto leggermente per stare nello schermo

        int tileSize = 50;
        
        KeyHandler keyH = new KeyHandler();
        
        GameWorld world = new GameWorld(tileSize);
        
        Player player = new Player("ez", 100, 1, 25*tileSize, 25*tileSize, tileSize, tileSize, ImageIO.read(getClass().getResource("/Player.png")));
        
        MapPanel mapPanel = new MapPanel(keyH, world, player);
        setContentPane(mapPanel);
        
        // Ciclo di gioco a ~60 FPS per aggiornare posizione e schermo
        javax.swing.Timer timer = new javax.swing.Timer(16, e -> {
        	
        	player.updatePlayer(keyH, world, this);
			this.getContentPane().revalidate();
			this.getContentPane().repaint();
        });
        timer.start();
    }
    
}
