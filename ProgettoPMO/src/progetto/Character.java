package progetto;

public abstract class Character {
	private String name;
	private int level;
	private int totalHP;
	private int currentHP;
	private int strength;
	private int speed;
	private int resistance;
	private Item equipment[];
	private Skill skills[];
	private int status;
	
	public Character(String name, int totalHP, int level ) {
		this.name = name;
		this.level = level;
		this.totalHP = totalHP;
		this.currentHP = totalHP;
		this.strength = level*3;
		this.speed = level*3;
	}
	
	public void attack() {
		
	}
}
