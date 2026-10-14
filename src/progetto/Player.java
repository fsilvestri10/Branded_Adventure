package progetto;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends Character {
	private static int MOVEMENT_SPEED = 10;
	
	private int playerSide;
	private int exp;
	private int gold;
	private int collisionInfo;
	
	public Player(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, String imageId) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, imageId);
		this.playerSide = 0;
		this.collisionInfo = 0;
	}
	public void attack() {
		
	}
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {

        if (this.image != null) {
        	switch (playerSide) {
        		case 0:
                    g2.drawImage(
                            this.image,
                            screenCenterX,
                            screenCenterY,
                            this.width,
                            this.height,
                            null
                        );
        			break;
        			
        		case 1:
        			
        			break;
        			
        		case 2:
        			//drawX = -tileSize;
                    g2.drawImage(
                            this.image,
                            screenCenterX + this.width,
                            screenCenterY,
                            -this.width,
                            this.height,
                            null
                    );
        			break;
        			
        		case 3:
        			
        			break;
        	}
        }
        this.hitbox.setBounds(screenCenterX, screenCenterY, this.width, this.height);
        g2.draw(this.hitbox);
        if(this.collisionInfo == 1) {
        	g2.drawString("ez", screenCenterX, screenCenterY);
        	
        }
        
	}
        
	public void updatePlayer(KeyHandler keyH, GameWorld world) {
		WorldObject collidedObject = null;
		this.collisionInfo = 0;
		int nextX = 0;
        int nextY = 0;
        
        if (keyH.upPressed) nextY-=MOVEMENT_SPEED;
        if (keyH.downPressed) nextY+=MOVEMENT_SPEED;
        if (keyH.leftPressed) {
        	nextX-=MOVEMENT_SPEED;
        	this.playerSide = 2;
        }
        if (keyH.rightPressed) {
        	nextX+=MOVEMENT_SPEED;
        	this.playerSide = 0;
        }

        // Controllo movimento asse x
        boolean collisionX = false;
        int dx = this.tileWorldX + nextX;
        Rectangle nextHitboxX = new Rectangle(dx, this.tileWorldY, this.width, this.height);
        for (WorldObject object : world.getObject()) {
	        if (nextHitboxX.intersects(object.getHibox())) {
	            collisionX = true;
	            collidedObject = object;
	        }
        }
        if(!collisionX) this.tileWorldX = dx;
        
        // Controllo movimento asse y
        boolean collisionY = false;
        int dy = this.tileWorldY + nextY;
        Rectangle nextHitboxY = new Rectangle(this.tileWorldX, dy, this.width, this.height);
        for (WorldObject object : world.getObject()) {
	        if (nextHitboxY.intersects(object.getHibox())) {
	            collisionY = true;
	            collidedObject = object;
	        }
        }
        if(!collisionY) this.tileWorldY = dy;
        
        for (WorldObject object : world.getObject()) {
	        if (Enemy.class.isInstance(object)) {
	        	if(((Enemy)object).getInteractRange().intersects(this.getPlayerHitbox())) {
	        		this.collisionInfo = 1;
	        		System.out.println("ez");
	        	}
	        }
        }
        /*
        if(Enemy.class.isInstance(collidedObject)) {
        	if(this.getPlayerHitbox().intersects(((Enemy) collidedObject).getInteractRange())) {
        		
        		
        	}
        } else {
        	this.collisionInfo = 0;
        }*/
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
}
