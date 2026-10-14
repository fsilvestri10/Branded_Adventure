package progetto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import javax.imageio.ImageIO;

public class GameWorld {
	
	private int tileSize;
    private int[][] map;
    private Random rnd;
	private ArrayList<WorldObject> objectHitbox;
	private int enemyCount;
	private BufferedImage grass;
	private BufferedImage subGrass;
	
	
	public GameWorld(int tileSize) {
		this.enemyCount = 0;
		this.tileSize = tileSize;
		objectHitbox = new ArrayList<>();
		rnd = new Random();
		map = new int[50][50];
		generateMap();
		try {
			this.grass = ImageIO.read(getClass().getResource("/Grass.png"));
			this.subGrass = this.grass.getSubimage(32, 96, 16, 16);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2, Player player, int screenCenterX, int screenCenterY) {
		
        // ====================================
        // DISEGNO DELLA MAPPA (Spostata rispetto al Player)
        // ====================================
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {

                // Posizione reale della casella nel MONDO (in pixel)
                int tileWorldX = x * tileSize;
                int tileWorldY = y * tileSize;

                // Posizione dove va disegnata la casella SULLO SCHERMO
                int screenX = tileWorldX - player.getPlayerX() + screenCenterX;
                int screenY = tileWorldY - player.getPlayerY() + screenCenterY;

                // OTTIMIZZAZIONE: Disegna la casella solo se è visibile a schermo
                //if (screenX + tileSize > 0 && screenX < getWidth() &&
                //    screenY + tileSize > 0 && screenY < getHeight()) {

                	switch(map[x][y]) {
                		case 0, 2, 3:
                			g2.setColor(new Color(0, 143, 57));
							g2.drawImage(this.subGrass, screenX, screenY, this.tileSize, this.tileSize, null);
							
                			break;
                		case 1:
                			
                			break;
                	}
                	//g2.fillRect(screenX, screenY, tileSize, tileSize);  
                    g2.setColor(Color.BLACK);
                    //g2.drawRect(screenX, screenY, tileSize, tileSize);
                //}
            }
        }
        
        this.checkEnemy();
        
        // ====================================
        // DISEGNO DEGLI ALBERI
        // ====================================
    	for(WorldObject object : this.objectHitbox) {
    		object.draw(g2, player.getPlayerX(), player.getPlayerY(), screenCenterX, screenCenterY);
    	}
	}
	
	public void generateMap() {
		for(int i = 0; i < 50; i++) {
            for(int j = 0; j < 50; j++) {
                if(j == 49 || j == 9 || (j<9 && (i == 20 || i == 29)) || j == 0 && (i < 29 && i > 20) || (i == 0 || i == 49) && j > 9) {
                    map[i][j] = 1;
                    this.objectHitbox.add(new Wall(i*this.tileSize, j*this.tileSize, this.tileSize, this.tileSize, "Wall"));
                } else {
                    map[i][j] = 0;
                    
                }
            }
        }
        
        for(int i = 0; i < 10; i++) if(!this.putTree()) i--;
        this.checkEnemy();
	}
	private void checkEnemy() {
		while(this.enemyCount < 5) {
        	if(!this.spawnEnemy()) this.enemyCount--;
        	this.enemyCount++;
        }
	}
	
	private boolean putTree() {
    	int posX = this.rnd.nextInt(2,48);
    	int posY = this.rnd.nextInt(11,48);
    	
    	if(this.map[posX][posY] == 0) {
    		this.map[posX][posY] = 2;
    		this.objectHitbox.add(new Tree(posX*this.tileSize, posY*this.tileSize, this.tileSize , this.tileSize, "Tree"));
    		return true;
    	} else {
    		return false;
    	}
    	
    }
	
    private boolean spawnEnemy() {
    	
    	int posX = this.rnd.nextInt(14,36);
    	int posY = this.rnd.nextInt(23,36);
    	
    	if(this.map[posX][posY] == 0) {
    		this.map[posX][posY] = 3;
    		this.objectHitbox.add(new Enemy("pus", 10, 2, posX*this.tileSize, posY*this.tileSize, this.tileSize , this.tileSize, "Enemy"));
    		return true;
    	} else {
    		return false;
    	}
    }
    
	public ArrayList<WorldObject> getObject() {
		return this.objectHitbox;
	}
}
