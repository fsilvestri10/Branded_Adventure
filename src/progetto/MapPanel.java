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

    private int playerX = 25*this.tileSize;
    private int playerY = 25*this.tileSize;
    private int playerSide = 0;
    private int speed = 10;
    private Rectangle hitbox = new Rectangle(playerX, playerY, tileSize, tileSize); //(NON SERVE PIU)
    private ArrayList<WorldObject> objectHitbox = new ArrayList<>();

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
            		this.objectHitbox.add(new Wall(i*this.tileSize, j*this.tileSize, this.tileSize, this.tileSize));
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
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        
    }
    

    // METODO PER AGGIORNARE IL MOVIMENTO
    public void updatePlayer() {
        int nextX = 0;
        int nextY = 0;
        
        if (keyH.upPressed) nextY-=this.speed;
        if (keyH.downPressed) nextY+=this.speed;
        if (keyH.leftPressed) {
        	nextX-=this.speed;
        	playerSide = 2;
        }
        if (keyH.rightPressed) {
        	nextX+=this.speed;
        	playerSide = 0;
        }

        // Controllo movimento asse x
        boolean collisionX = false;
        int dx = playerX + nextX;
        Rectangle nextHitboxX = new Rectangle(dx, playerY, tileSize, tileSize);
        for (WorldObject object : this.objectHitbox) {
	        if (nextHitboxX.intersects(object.getHibox())) {
	            collisionX = true;
	        }
        }
        if(!collisionX) playerX = dx;
        
        // Controllo movimento asse y
        	boolean collisionY = false;
        int dy = playerY + nextY;
        Rectangle nextHitboxY = new Rectangle(playerX, dy, tileSize, tileSize);
        for (WorldObject object : this.objectHitbox) {
	        if (nextHitboxY.intersects(object.getHibox())) {
	            collisionY = true;
	        }
        }
        if(!collisionY) playerY = dy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. TROVA IL CENTRO DELLA FINESTRA
        int screenCenterX = getWidth() / 2;
        int screenCenterY = getHeight() / 2;

        // 2. CALCOLA LA POSIZIONE DEL PLAYER NEL MONDO (in pixel)
        int playerWorldX = playerX;
        int playerWorldY = playerY;

        // ====================================
        // DISEGNO DELLA MAPPA (Spostata rispetto al Player)
        // ====================================
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {

                // Posizione reale della casella nel MONDO (in pixel)
                int tileWorldX = x * tileSize;
                int tileWorldY = y * tileSize;

                // Posizione dove va disegnata la casella SULLO SCHERMO
                int screenX = tileWorldX - playerWorldX + screenCenterX;
                int screenY = tileWorldY - playerWorldY + screenCenterY;

                // OTTIMIZZAZIONE: Disegna la casella solo se è visibile a schermo
                //if (screenX + tileSize > 0 && screenX < getWidth() &&
                //    screenY + tileSize > 0 && screenY < getHeight()) {

                	switch(map[x][y]) {
                		case 0, 2:
                			g2.setColor(new Color(0, 143, 57));
                			break;
                		case 1:
                			g2.setColor(Color.DARK_GRAY);
                			break;
                	}
                	g2.fillRect(screenX, screenY, tileSize, tileSize);          	
                    g2.setColor(Color.BLACK);
                    //g2.drawRect(screenX, screenY, tileSize, tileSize);
                //}
            }
        }
        
        // ====================================
        // DISEGNO DEGLI ALBERI
        // ====================================
    	for(WorldObject object : this.objectHitbox) {
    		object.draw(g2, playerWorldX, playerWorldY, screenCenterX, screenCenterY);
    	}
        
        // ====================================
        // DISEGNO DEL PLAYER (Sempre al centro)
        // ====================================
        if (playerImage != null) {
        	switch (playerSide) {
        		case 0:
                    g2.drawImage(
                            playerImage,
                            screenCenterX,
                            screenCenterY,
                            tileSize,
                            tileSize,
                            null
                        );
        			break;
        			
        		case 1:
        			
        			break;
        			
        		case 2:
        			//drawX = -tileSize;
                    g2.drawImage(
                            playerImage,
                            screenCenterX + tileSize,
                            screenCenterY,
                            -tileSize,
                            tileSize,
                            null
                    );
        			break;
        		case 3:
        			
        			break;
        	}
        }
        this.hitbox.setBounds(screenCenterX, screenCenterY, this.tileSize, this.tileSize);  // ERA PER IL DEBUG (DISEGNAVA LA HITBOX)
        g2.draw(hitbox);																	// MA NON SERVE
    }
    private boolean putTree() {
    	int posX = this.rnd.nextInt(2,48);
    	int posY = this.rnd.nextInt(11,48);
    	
    	if(this.map[posX][posY] == 0) {
    		this.map[posX][posY] = 2;
    		this.objectHitbox.add(new Tree(posX*this.tileSize, posY*this.tileSize, this.tileSize * 2, this.tileSize * 2));
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
}