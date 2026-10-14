package progetto;

import java.awt.image.BufferedImage;

public class Sprite {
	private BufferedImage[] walkUp;
    private BufferedImage[] walkDown;
    private BufferedImage[] walkLeft;
    private BufferedImage[] walkRight;
	private int spriteNum = 0;
	private int spriteCounter = 0;
	private int spriteChange = 12;
	private BufferedImage image;
    
    public Sprite(BufferedImage image) {
    	// Istanza e popolamento degli array per animazioni
    	this.walkUp = new BufferedImage[3];
    	this.walkDown = new BufferedImage[3];
   		this.walkLeft = new BufferedImage[3];
    	this.walkRight = new BufferedImage[3];
    	
    	for(int i = 0; i < 3; i++) {
			this.walkUp[i] = image.getSubimage(i * 32, 96, 32, 32);
			this.walkDown[i] = image.getSubimage(i * 32, 0, 32, 32);
			this.walkLeft[i] = image.getSubimage(i * 32, 32, 32, 32);
			this.walkRight[i] = image.getSubimage(i * 32, 64, 32, 32);
		}
    	this.image = image;
    }
    public BufferedImage getCurrentSprite(int playerSide) {
    	switch (playerSide) {
	    	// Verso l'alto
	    	case 0:
	    		return this.walkUp[this.spriteNum];
	    	// Verso giù
	    	case 1:
	    		return this.walkDown[this.spriteNum];
	    	// Verso Sinistra
	    	case 2:
	    		return this.walkLeft[this.spriteNum];
	        // Verso Destra	
	    	case 3:
	    		return this.walkRight[this.spriteNum];
	    	default:
	    		return null;
	    }
    }
    public void updateCounter() {
    	this.spriteCounter++;
    	if (this.spriteCounter >= this.spriteChange) {
    		this.spriteNum++;
    		if (this.spriteNum >= 3) this.spriteNum = 0;
    		this.spriteCounter = 0;
    	}
    }
    protected void setSpriteNum(int n) {
    	this.spriteNum = n;
    }
    protected BufferedImage getWalkRightSprite() {
    	return this.walkRight[this.spriteNum];
    }
    protected BufferedImage getCombatSprite() {
    	return this.image.getSubimage(32, 0, 32, 32);
    	
    }
}
