package progetto;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class WorldObject {
	protected int tileWorldX,tileWorldY;
	protected int width, height;
	public Rectangle hitbox;
	
	public WorldObject(int tileWorldX, int tileWorldY, int width, int height) {
		this.tileWorldX = tileWorldX;
		this.tileWorldY = tileWorldY;
		this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(tileWorldX, tileWorldY, width, height);
	}
	public abstract void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY);
	public Rectangle getHibox() {
		return this.hitbox;
	}
}
