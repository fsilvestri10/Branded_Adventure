package progetto.model;

import java.util.ArrayList;

// MODEL: dati e regole comuni a giocatore e nemici
public abstract class Character extends WorldObject {
    private String name;
    private int level;
    private int totalHP;
    private int currentHP;
    private int strength;
    private ArrayList<Item> equipment;
    private ArrayList<Skill> skills;
    private int status;
    private int statusTimer; // per far scadere la durata dello stato
    protected Direction direction;
    protected boolean moving = false;

    public Character(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height, Direction direction) {
        super(tileWorldX, tileWorldY, width, height);
        this.name = name;
        this.level = level;
        this.totalHP = totalHP;
        this.currentHP = totalHP;
        this.strength = level * 3;
        this.equipment = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.status = 0;
        this.statusTimer = 0;
        this.equipment.add(0, ItemDatabase.getSword());
        this.equipment.add(1, ItemDatabase.getArmor());
        this.direction = direction;
    }
    
    public void attack(Character target) {
        // l'armatura del bersaglio è considerata dentro takeDamage()
    	// qua considero eventuali buff e l'equipment che aumenta l'attacco
    	
    	int damage = this.getStrength();
    	if(status == 1) damage *=1.5;
    	if(this.getEquipmentSize() == 0) target.takeDamage(damage);
    	else target.takeDamage(damage + this.getEquipment(0).getValue());
    	this.statusTimer--;
    	if(this.statusTimer == 0) this.status = 0;
    }
    
    public void takeDamage(int rawDamage) {
    	// qua considero eventuali debuff e l'equipment che aumenta la difesa
    	
        int damage = 0;
        if(this.getEquipmentSize() == 2) damage = Math.max(0, rawDamage - this.getEquipment(1).getValue());
        else damage = Math.max(0, rawDamage);
        if(this.status == -1) damage*=1.5;
        this.currentHP = Math.max(0, this.currentHP - damage);
    }

    // Recupera HP, senza superare il massimo.
    public void heal(int amount) {
        this.currentHP = Math.min(this.totalHP, this.currentHP + amount);
    }
    // Fa danni, senza scendere sotto 0 hp
    public void slash(int amount, Character target) {
        target.currentHP = Math.max(0, target.currentHP - amount);
    }
    // mi potenzio se non lo sono gia
    public void buff() {
    	if(++this.status > 1) {
    		this.status--;    	
    	} else {
    		this.statusTimer = SkillDatabase.getBuff().getPower();
    	}
    }
    // vengo depotenziato se non lo sono gia
    public void debuff() {
    	if(--this.status < -1) {
    		this.status++;
    	} else {
        	this.statusTimer = SkillDatabase.getDebuff().getPower();
    	}
    }
    public int getStatusTimer() {
    	return this.statusTimer;
    }
    public int getStatus() {
    	return this.status;
    }

    public boolean isDead() {
        return this.currentHP <= 0;
    }

    public String getName() {
        return this.name;
    }

    public int getTotalHP() {
        return this.totalHP;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getLevel() {
        return this.level;
    }

    public int getStrength() {
        return this.strength;
    }

    public ArrayList<Skill> getSkills() {
        return this.skills;
    }

    public Item getEquipment(int i) {
        return this.equipment.get(i);
    }
    
    public int getEquipmentSize() {
    	return this.equipment.size();
    }

    protected void setSkill(Skill skill) {
        this.skills.add(skill);
    }

    protected void levelUp() {
        this.level++;
    }

    protected void updateStrength() {
        this.strength = this.level * 3;
    }
    public void changeEquipment(int i,Item item) {
    	this.equipment.set(i, item);
    }
    public Direction getDirection() {
        return this.direction;
    }
    public boolean isMoving() {
        return this.moving;
    }
    public void setMoving(boolean moving) {
    	this.moving = moving;
    }
}
