package progetto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private int tileSize = 50;
    private int[][] map = new int[50][50];
    
    private Image playerImage;
    private Image treeImage;

    private int playerX = 25*this.tileSize;
    private int playerY = 25*this.tileSize;
    private int speed = 10;
    private Rectangle hitbox = new Rectangle(playerX, playerY, tileSize, tileSize);
    private ArrayList<Rectangle> treesHitbox = new ArrayList<>();

    private Random rnd = new Random();
    
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
        
        for(int i = 0; i < 10; i++) {
        	if(!this.putTree()) i--; 
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

        if (keyH.upPressed) nextY-=this.speed;
        if (keyH.downPressed) nextY+=this.speed;
        if (keyH.leftPressed) nextX-=this.speed;
        if (keyH.rightPressed) nextX+=this.speed;

        int x = nextX/this.tileSize;
        int y = nextY/this.tileSize;
        
        for(int i = 0; i < this.treesHitbox.size(); i++) {
	        if (!this.hitbox.intersects(this.treesHitbox.get(i))) {
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
        int playerWorldX = playerX;
        int playerWorldY = playerY;

        // DISEGNO DELLA MAPPA (Spostata rispetto al Player)
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {

                // Posizione reale della tessera nel MONDO (in pixel)
                int tileWorldX = x * tileSize;
                int tileWorldY = y * tileSize;

                // Posizione dove va disegnata la tessera SULLO SCHERMO
                int screenX = tileWorldX - playerWorldX + screenCenterX;
                int screenY = tileWorldY - playerWorldY + screenCenterY;

                switch(map[x][y]) {
                	case 0, 2:
                		g2.setColor(new Color(0, 143, 57));
                		break;
                	case 1:
                		g2.setColor(Color.DARK_GRAY);
                		break;
                }
                g2.fillRect(screenX, screenY, tileSize, tileSize);
                if(map[x][y] == 2) {
                	g2.drawImage(treeImage, screenX, screenY, tileSize, tileSize, null);
                	this.treesHitbox.add(new Rectangle(screenX, screenY, 70, 70));
                	System.out.println(new Rectangle(screenX, screenY, 70, 70) + " " + treesHitbox.getLast());
                	g2.draw(treesHitbox.getLast());
                }
                g2.setColor(Color.BLACK);
                g2.drawRect(screenX, screenY, tileSize, tileSize);
                
            }
        }
        
        // DISEGNO DEL PLAYER (Sempre al centro)
        if (playerImage != null) {
            // Il player NON usa più (playerX * tileSize), ma viene disegnato sempre al centro dello schermo!
            g2.drawImage(
                playerImage,
                screenCenterX,
                screenCenterY,
                tileSize,
                tileSize,
                null
            );
        }
        this.hitbox.setBounds(screenCenterX, screenCenterY, this.tileSize, this.tileSize);
        g2.draw(hitbox);
        
    }
    private boolean putTree() {
    	int posX = this.rnd.nextInt(1,49);
    	int posY = this.rnd.nextInt(10,49);
    	
    	if(this.map[posX][posY] == 0) {
    		this.map[posX][posY] = 2;
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
}