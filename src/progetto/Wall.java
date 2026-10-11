package progetto;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends WorldObject{

	private Image image;
	
	public Wall(int tileWorldX, int tileWorldY, int width, int height) {
		super(tileWorldX, tileWorldY, width, height);
		try {
			this.image = ImageIO.read(getClass().getResource("/wall.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		int screenX = this.tileWorldX - playerX + screenCenterX;
        int screenY = this.tileWorldY - playerY + screenCenterY;
        if(this.image != null) g2.drawImage(this.image, screenX - 6, screenY - 6, this.width + 12, this.height + 12, null);
	}
}