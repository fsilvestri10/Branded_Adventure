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
		
		try {
			this.grass = ImageIO.read(getClass().getResource("/Grass.png"));
			this.subGrass = this.grass.getSubimage(32, 96, 16, 16);
			generateMap();
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

                if(map[x][y] != 1) g2.drawImage(this.subGrass, screenX, screenY, this.tileSize, this.tileSize, null);
                
                g2.setColor(Color.BLACK);
            }
        }
        try {
			this.checkEnemy();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // ====================================
        // DISEGNO DEGLI OGGETTI
        // ====================================
    	for(WorldObject object : this.objectHitbox) {
    		object.draw(g2, player.getPlayerX(), player.getPlayerY(), screenCenterX, screenCenterY);
    	}
	}
	
	public void generateMap() throws IOException {
		for(int i = 0; i < 50; i++) {
            for(int j = 0; j < 50; j++) {
                if(j == 49 || j == 9 || (j<9 && (i == 20 || i == 29)) || j == 0 && (i < 29 && i > 20) || (i == 0 || i == 49) && j > 9) {
                    map[i][j] = 1;
                    this.objectHitbox.add(new Wall(i*this.tileSize, j*this.tileSize, this.tileSize, this.tileSize, ImageIO.read(getClass().getResource("/Wall.png"))));
                } else {
                    map[i][j] = 0;
                }
            }
        }
		
		// posiziono nemici, alberi e cespugli
		for(int i = 0; i < 10; i++) if(!this.putObject(2, new Tree(this.rnd.nextInt(2,48)*this.tileSize, this.rnd.nextInt(11,48)*this.tileSize, this.tileSize , this.tileSize, ImageIO.read(getClass().getResource("/Tree.png"))))) i--;
		for(int i = 0; i < 30; i++) if(!this.putObject(4, new Bush(this.rnd.nextInt(2,48)*this.tileSize+10, this.rnd.nextInt(11,48)*this.tileSize+10, this.tileSize-20, this.tileSize-20, this.grass))) i--;
        this.checkEnemy();
	}
	private void checkEnemy() throws IOException {
		while(this.enemyCount < 5) {
        	if(!this.putObject(3, new Enemy("pus", 10, 2, this.rnd.nextInt(14,36)*this.tileSize, this.rnd.nextInt(23,36)*this.tileSize, this.tileSize , this.tileSize, ImageIO.read(getClass().getResource("/Enemy.png"))))) this.enemyCount--;
        	this.enemyCount++;
        }
	}
	private boolean putObject(int objectNum, WorldObject object) {
    	if(this.map[object.getX()/this.tileSize][object.getY()/this.tileSize] == 0) {
    		this.map[object.getX()/this.tileSize][object.getY()/this.tileSize] = objectNum;
    		this.objectHitbox.add(object);
    		return true;
    	} else {
    		return false;
    	}
	}
	public ArrayList<WorldObject> getObject() {
		return this.objectHitbox;
	}
	
}
