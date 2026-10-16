package progetto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Player extends Character {
	private static int MOVEMENT_SPEED = 10;
	
	private int battleX = 0;
	private int playerSide = 1;
	private int exp;
	private int gold;
	private int collisionInfo;
	// 0 - no collision  1 - enemy collision
	private BufferedImage currentSprite;
	private boolean isMoving = false;
    
    private Sprite sprite;
	
	public Player(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, BufferedImage image, BufferedImage fightingSprite) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, image, fightingSprite);
		
		// Istanza e popolamento degli array per animazioni
		this.sprite = new Sprite(image);
	}
	
	public void attack() {
		
	}
	
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		
		this.currentSprite = this.sprite.getCurrentSprite(this.playerSide);
        if (this.currentSprite != null) g2.drawImage(this.currentSprite, screenCenterX, screenCenterY, this.width, this.height, null);
        
        if(this.collisionInfo == 1) {
        	g2.setColor(Color.BLACK);
        	g2.drawString("ez", screenCenterX+18, screenCenterY);
        }
	}
	
	public void drawBattle(Graphics2D g2, int screenWidth, int screenHeight) {
		
		g2.drawImage(this.sprite.getCombatSprite(), 0, screenHeight - screenHeight/5, screenHeight/5, screenHeight/5, null);
		g2.fillRect(screenWidth/5, screenHeight - screenHeight/10, screenWidth/3, screenHeight/10);
		g2.setColor(Color.GREEN);
		g2.fillRect(screenWidth/5 + 15, screenHeight - screenHeight/10 + 15, screenWidth/3 - 30, screenHeight/10 - 30);
		g2.setFont(new Font("sanserif", Font.BOLD, screenWidth/30));
		g2.drawString("HP", screenWidth/5 - screenWidth/20, screenHeight - screenHeight/10 + screenHeight/15);
		g2.setFont(new Font("sanserif", Font.BOLD, screenWidth/20));
		g2.drawString(this.getCurrentHP() + "/" + this.getTotalHP(), screenWidth/5 + screenHeight/10, screenHeight - screenHeight/10 - 20);
	}
	
	public WorldObject updatePlayer(KeyHandler keyH, GameWorld world, JFrame frame) {
		this.collisionInfo = 0;
		this.isMoving = false;
		int nextX = 0;
        int nextY = 0;
        WorldObject collidingObject = null;
        
        if (keyH.upPressed) {
        	nextY-=MOVEMENT_SPEED;
        	this.playerSide = 0;
        }
        if (keyH.downPressed) {
        	nextY+=MOVEMENT_SPEED;
        	this.playerSide = 1;
        }
        if (keyH.leftPressed) {
        	nextX-=MOVEMENT_SPEED;
        	this.playerSide = 2;
        }
        if (keyH.rightPressed) {
        	nextX+=MOVEMENT_SPEED;
        	this.playerSide = 3;
        }
        
        if(nextX != 0 || nextY != 0) this.isMoving = true;

        // Controllo movimento asse x
        if(!checkMovement(this.tileWorldX + nextX, this.tileWorldY, world)) this.tileWorldX+=nextX;
        
        // Controllo movimento asse y
        if(!checkMovement(this.tileWorldX, this.tileWorldY + nextY, world)) this.tileWorldY+=nextY;

        for (WorldObject object : world.getObject()) {
	        if (Enemy.class.isInstance(object)) {
	        	if(((Enemy)object).getInteractRange().intersects(this.getPlayerHitbox())) this.collisionInfo = 1;
	        	collidingObject = object;
	        } 
        }
        
        if (this.isMoving) {
        	this.sprite.updateCounter();
        } else {
        	this.sprite.setSpriteNum(1);
        }
        
        if(keyH.ePressed && this.collisionInfo == 1) {
        	FightPanel fightPanel = new FightPanel(this, (Enemy)collidingObject);
        	frame.setContentPane(fightPanel);
        	return collidingObject;
        }
        return null;
	}
	
	private boolean checkMovement(int x, int y, GameWorld world) {
		Rectangle nextHitbox = new Rectangle(x, y, this.width, this.height);
        for (WorldObject object : world.getObject()) {
	        if (this.checkObject(object) && nextHitbox.intersects(object.getHibox())) return true;
	        //controllo se sto calpestando un bush
	        if(Bush.class.isInstance(object))
	        	if(nextHitbox.intersects(object.getHibox())) ((Bush) object).trample();
	        	//else ((Bush) object).untrample();
        }
        return false;
	}
	
	private boolean checkObject(WorldObject object) {
		if(Enemy.class.isInstance(object) || Tree.class.isInstance(object) || Wall.class.isInstance(object)) return true;
		return false;
	}
	private Rectangle getPlayerHitbox() {
		return new Rectangle(this.tileWorldX, this.tileWorldY, this.width, this.height);
	}
	public int getPlayerX() {
		return this.tileWorldX;
	}
	public int getPlayerY() {
		return this.tileWorldY;
	}
	public int getCollisionInfo() {
		return this.collisionInfo;
	}
	public int getBattleX() {
		return this.battleX;
	}
	private void setBattleX(int n) {
		this.battleX = n;	
	}
}
