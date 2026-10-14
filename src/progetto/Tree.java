package progetto;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tree extends WorldObject{
	
	public Tree(int tileWorldX, int tileWorldY, int width, int height, BufferedImage image) {
		super(tileWorldX, tileWorldY, width, height, image);
		
	}

	@Override
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		int screenX = this.tileWorldX - playerX + screenCenterX;
        int screenY = this.tileWorldY - playerY + screenCenterY;
        if(this.image != null) g2.drawImage(this.image, screenX-20, screenY-20, this.width+40, this.height+40, null);
        
        g2.drawRect(screenX, screenY, this.width, this.height);				//hitbox alberi per debug
	}
}