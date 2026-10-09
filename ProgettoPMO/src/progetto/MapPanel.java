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

    private int tileSize = 50;
    private int[][] map = new int[50][50];
    
    private Image playerImage;
    private Image treeImage;

    private int playerX = 20;
    private int playerY = 20;

    // Gestore tastiera
    private KeyHandler keyH = new KeyHandler();

    public MapPanel() {
        // PERMETTE AL PANNELLO DI RICEVERE L'INPUT TASTIERA
        this.setFocusable(true);
        this.addKeyListener(keyH);

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

    // METODO PER AGGIORNARE IL MOVIMENTO
    public void updatePlayer() {
        int nextX = playerX;
        int nextY = playerY;

        if (keyH.upPressed) nextY--;
        if (keyH.downPressed) nextY++;
        if (keyH.leftPressed) nextX--;
        if (keyH.rightPressed) nextX++;

        // Controlla che la nuova casella non sia un muro (valore 1)
        if (nextX >= 0 && nextX < 50 && nextY >= 0 && nextY < 50) {
            if (map[nextX][nextY] != 1) {
                playerX = nextX;
                playerY = nextY;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. TROVA IL CENTRO DELLA FINESTRA
        int screenCenterX = getWidth() / 2 - (tileSize / 2);
        int screenCenterY = getHeight() / 2 - (tileSize / 2);

        // 2. CALCOLA LA POSIZIONE DEL PLAYER NEL MONDO (in pixel)
        int playerWorldX = playerX * tileSize;
        int playerWorldY = playerY * tileSize;

        // DISEGNO DELLA MAPPA (Spostata rispetto al Player)
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {

                // Posizione reale della tessera nel MONDO (in pixel)
                int tileWorldX = x * tileSize;
                int tileWorldY = y * tileSize;

                // Posizione dove va disegnata la tessera SULLO SCHERMO
                int screenX = tileWorldX - playerWorldX + screenCenterX;
                int screenY = tileWorldY - playerWorldY + screenCenterY;

                // Disegna la tessera solo se è visibile a schermo
                if (screenX + tileSize > 0 && screenX < getWidth() &&
                    screenY + tileSize > 0 && screenY < getHeight()) {

                    if (map[x][y] == 1) {
                        g2.setColor(Color.DARK_GRAY);
                    } else {
                        g2.setColor(new Color(0, 143, 57));
                    }

                    g2.fillRect(screenX, screenY, tileSize, tileSize);

                    g2.setColor(Color.BLACK);
                    g2.drawRect(screenX, screenY, tileSize, tileSize);
                }
            }
        }

        // DISEGNO DEL PLAYER (Sempre al centro)
        if (playerImage != null) {
            g2.drawImage(
                playerImage,
                screenCenterX,
                screenCenterY,
                tileSize,
                tileSize,
                null
            );
        }
    }
}