package progetto;

import java.awt.Color;
import java.awt.Font;
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
	private int id;
	
	public Enemy(int id, String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, BufferedImage image, BufferedImage fighingSprite) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, image, fighingSprite);
		this.interactRange = new Rectangle(this.tileWorldX-10, this.tileWorldY-10, this.width+20, this.height+20);
		this.rnd = new Random();
		this.id = id;
		this.expDrop = this.getLevel() *10;
		this.goldDrop = this.getLevel() *100;
	}
	
	public void attack(Character player) {
		
		player.setCurrentHP((player.getCurrentHP() + player.getEquipment(1).getValue()) - (this.getStrength() + this.getEquipment(0).getValue()));
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
	
	public void drawBattle(Graphics2D g2, int screenWidth, int screenHeight) {
		//this.currentSprite = this.image.getSubimage(32, 1*32, 32, 32);
		//g2.drawImage(this.currentSprite, screenCenterX, screenCenterY, width, height, null);
		
		g2.drawImage(this.getFightingSprite(), screenWidth/2 - screenWidth/6, screenHeight/4, screenWidth/3, screenHeight/2, null);
		
		g2.setColor(Color.GREEN);
		/*
		g2.fillRect(screenWidth/5 + 15, screenHeight - screenHeight/10 + 15, screenWidth/3 - 30, screenHeight/10 - 30);
		g2.setFont(new Font("sanserif", Font.BOLD, screenWidth/30));
		g2.drawString("HP", screenWidth/5 - screenWidth/20, screenHeight - screenHeight/10 + screenHeight/15);
		g2.setFont(new Font("sanserif", Font.BOLD, screenWidth/20));
		g2.drawString(this.getCurrentHP() + "/" + this.getTotalHP(), screenWidth/2 - screenWidth/6, screenHeight/5);
		*/
		double percentHP = (double)this.getCurrentHP() / this.getTotalHP();
		g2.fillRect(screenWidth/2 - screenWidth/6, screenHeight/5, (int)(screenWidth/3 * percentHP), screenHeight/10 - 30);
	}
	
	public Rectangle getInteractRange() {
		return this.interactRange;
	}
	public int getId() {
		return this.id;
	}
	public int getExp() {
		return this.expDrop;
	}
	public int getGold() {
		return this.goldDrop;
	}
}
