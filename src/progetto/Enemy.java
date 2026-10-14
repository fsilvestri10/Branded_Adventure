package progetto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy extends Character {
	
	private int expDrop;
	private int goldDrop;
	private Rectangle interactRange;
	
	public Enemy(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, String imageId) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, imageId);
		this.interactRange = new Rectangle(this.tileWorldX-10, this.tileWorldY-10, this.width+20, this.height+20);
	}
	
	public void attack() {
		
	}
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		int screenX = this.tileWorldX - playerX + screenCenterX;
        int screenY = this.tileWorldY - playerY + screenCenterY;
        if(this.image != null) g2.drawImage(this.image, screenX - 6, screenY - 6, this.width + 12, this.height + 12, null);
        g2.setColor(Color.red);
        g2.drawRect(screenX-10, screenY-10, width+20, height+20);
	}
	public Rectangle getInteractRange() {
		return this.interactRange;
		
	}
}
