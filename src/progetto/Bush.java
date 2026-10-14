package progetto;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bush extends WorldObject {
	private BufferedImage currentImage;
	public Bush(int tileWorldX, int tileWorldY, int width, int height, BufferedImage image) {
		super(tileWorldX, tileWorldY, width, height, image);
		this.currentImage = this.image.getSubimage(160 + 96, 176, 16, 16);
	}

	@Override
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		int screenX = this.tileWorldX - playerX + screenCenterX;
        int screenY = this.tileWorldY - playerY + screenCenterY;
        if(this.image != null) g2.drawImage(this.currentImage, screenX, screenY, this.width, this.height, null);
        
        g2.drawRect(screenX, screenY, this.width, this.height);				//hitbox alberi per debug
	}
	public void trample() {
		this.currentImage = this.image.getSubimage(176 + 96, 176, 16, 16);
	}
	public void untrample() {
		this.currentImage = this.image.getSubimage(160 + 96, 176, 16, 16);
	}
}
