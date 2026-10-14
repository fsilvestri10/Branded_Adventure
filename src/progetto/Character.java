package progetto;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Character extends WorldObject {
	private String name;
	private int level;
	private int totalHP;
	private int currentHP;
	private int strength;
	private int speed;
	private int resistance;
	private ArrayList<Item> equipment;
	private ArrayList<Skill> skills;
	private int status;
	
	public Character(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, BufferedImage image) {
		super(tileWorldX, tileWorldY, width, height, image);
		this.name = name;
		this.level = level;
		this.totalHP = totalHP;
		this.currentHP = totalHP;
		this.strength = level*3;
		this.speed = level*3;
		this.equipment = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.status = 0;
	}
	
	public abstract void attack();
	
	public abstract void drawBattle(Graphics2D g2, int width, int height, int screenCenterX, int screenCenterY);
}
