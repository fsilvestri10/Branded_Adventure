package progetto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Enemy extends Character {
	
	private int expDrop;
	private int goldDrop;
	private Rectangle interactRange;
	private Random rnd;
	private BufferedImage currentSprite;
	private int spriteChange = 60;
	private int spriteCounter = 60;
	
	private BufferedImage fightingSprite;
	
	public Enemy(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, BufferedImage image) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, image);
		this.interactRange = new Rectangle(this.tileWorldX-10, this.tileWorldY-10, this.width+20, this.height+20);
		this.rnd = new Random();
		this.fightingSprite = this.image.getSubimage(32, 0*32, 32, 32);
	}
	
	public void attack() {
		
	}
	
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		this.spriteCounter++;
		int screenX = this.tileWorldX - playerX + screenCenterX;
        int screenY = this.tileWorldY - playerY + screenCenterY;
        if (this.spriteCounter >= spriteChange) {
	        if(this.image != null) {
	        	this.currentSprite = this.image.getSubimage(32, this.rnd.nextInt(4)*32, 32, 32);
	        }
	        this.spriteCounter = 0;
        }
        
		g2.drawImage(this.currentSprite, screenX - 6, screenY - 6, this.width + 12, this.height + 12, null);
        g2.setColor(Color.red);
        g2.drawRect(screenX-10, screenY-10, width+20, height+20);
	}
	
	public void drawBattle(Graphics2D g2, int width, int height, int screenCenterX, int screenCenterY) {
		//this.currentSprite = this.image.getSubimage(32, 1*32, 32, 32);
		//g2.drawImage(this.currentSprite, screenCenterX, screenCenterY, width, height, null);
		
		g2.drawImage(this.fightingSprite, screenCenterX, screenCenterY, width, height, null);
	}
	
	public Rectangle getInteractRange() {
		return this.interactRange;
	}
}
