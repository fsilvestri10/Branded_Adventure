package progetto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // Dimensione di una casella
    private int tileSize = 50;

    // 1 = muro
    // 0 = terreno
    private int[][] map = new int[50][50];
    
    // Immagine del player
    private Image playerImage;
    private Image treeImage;

    // Posizione del player nella mappa
    private int playerX = 2;
    private int playerY = 1;


    // Costruttore
    public MapPanel() {

    	for(int i = 0; i < 50; i++) {
        	for(int j = 0; j < 50; j++) {
        		if(j == 49 || j == 9 || (j<9 && (i == 20 || i == 29)) || j == 0 && (i < 29 && i > 20) || (i == 0 || i == 49) && j > 9) {
        			map[i][j] = 1;
        		} else {
        			map[i][j] = 0;
        		}
        	}
        	
        }
        try {
            playerImage = ImageIO.read(getClass().getResource("/player.png"));
            treeImage = ImageIO.read(getClass().getResource("/tree.png"));

        } catch (IOException | IllegalArgumentException e) {

            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {

        // Disegno normale del JPanel
        super.paintComponent(g);

        // Convertiamo Graphics in Graphics2D
        Graphics2D g2 = (Graphics2D) g;


        // ====================================
        // DISEGNO DELLA MAPPA
        // ====================================

        for (int y = 0; y < map.length; y++) {

            for (int x = 0; x < map[y].length; x++) {

                // Se è un muro
                if (map[x][y] == 1) {

                    g2.setColor(Color.DARK_GRAY);

                } 
                // Se è terreno
                else {

                    g2.setColor(new Color(0,143,57));
                }


                // Disegniamo la casella
                g2.fillRect(
                    x * tileSize,
                    y * tileSize,
                    tileSize,
                    tileSize
                );


                // Bordo della casella
                g2.setColor(Color.BLACK);

                g2.drawRect(
                    x * tileSize,
                    y * tileSize,
                    tileSize,
                    tileSize
                );
            }
        }


        // ====================================
        // DISEGNO DEL PLAYER
        // ====================================

        if (playerImage != null) {

            g2.drawImage(
                playerImage,

                // X sullo schermo
                playerX * tileSize,

                // Y sullo schermo
                playerY * tileSize,

                // Larghezza
                tileSize,

                // Altezza
                tileSize,

                // Observer
                null
            );
        }
        g2.drawImage(
                treeImage,

                // X sullo schermo
                playerX * tileSize,

                // Y sullo schermo
                playerY * tileSize,

                // Larghezza
                tileSize,

                // Altezza
                tileSize,

                // Observer
                null
            );
    }
}