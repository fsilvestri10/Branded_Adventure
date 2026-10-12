package progetto;

import java.awt.Graphics2D;

public class Player extends Character {
	
	private static int MOVEMENT_SPEED = 10;
	private int exp;
	private int gold;
	
	public Player(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, String imageId) {
		super(name, totalHP, level, tileWorldX, tileWorldY, width, height, imageId);
		
	}
	public void attack() {
		
	}
	public void draw(Graphics2D g2, int playerX, int playerY, int screenCenterX, int screenCenterY) {
		
	}
}
