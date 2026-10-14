package progetto;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class WorldObject {
	
	protected int tileWorldX,tileWorldY;
	protected int width, height;
	public Rectangle hitbox;
	protected BufferedImage image;
	
	public WorldObject(int tileWorldX, int tileWorldY, int width, int height, BufferedImage image) {
		this.tileWorldX = tileWorldX;
		this.tileWorldY = tileWorldY;
		this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(tileWorldX, tileWorldY, width, height);
        this.image = image;
	}
	public abstract void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY);
	public Rectangle getHibox() {
		return this.hitbox;
	}
	public int getX() {
		return this.tileWorldX;
	}
	public int getY() {
		return this.tileWorldY;
	}
}
