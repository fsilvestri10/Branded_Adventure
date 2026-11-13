package progetto.model;

import java.util.ArrayList;

/** MODEL: dati e regole comuni a giocatore e nemici. Nessuna immagine, nessun disegno. */
public abstract class Character extends WorldObject {
    private String name;
    private int level;
    private int totalHP;
    private int currentHP;
    private int strength;
    private ArrayList<Item> equipment;
    private ArrayList<Skill> skills;
    private int status;

    public Character(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height) {
        super(tileWorldX, tileWorldY, width, height);
        this.name = name;
        this.level = level;
        this.totalHP = totalHP;
        this.currentHP = totalHP;
        this.strength = level * 3;
        this.equipment = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.status = 0;
        this.equipment.add(0, ItemDatabase.getSword());
        this.equipment.add(1, ItemDatabase.getArmor());
    }
    public void attack(Character target) {
        // l'armatura del bersaglio è considerata dentro takeDamage()
    	
    	if(this.getEquipmentSize() == 0) target.takeDamage(this.getStrength());
    	else target.takeDamage(this.getStrength() + this.getEquipment(0).getValue());
    }
    /**
     * Subisce un attacco: l'armatura riduce il danno. Gli HP non scendono sotto zero.
     * (Con la stessa formula di prima: HP - (attacco - armatura), ma l'armatura non può mai curare.)
     */
    public void takeDamage(int rawDamage) {
        int damage = 0;
        if(this.getEquipmentSize() == 2) damage = Math.max(0, rawDamage - this.getEquipment(1).getValue());
        else damage = Math.max(0, rawDamage);
        //applico l'eventuale debuff
        if(this.status == -1) damage*=1.5;
        this.currentHP = Math.max(0, this.currentHP - damage);
    }

    /** Recupera HP, senza superare il massimo. */
    public void heal(int amount) {
        this.currentHP = Math.min(this.totalHP, this.currentHP + amount);
    }
    public void slash(int amount, Character target) {
        target.currentHP = Math.max(0, target.currentHP - amount);
    }
    public void buff() {
    	this.status++;
    }
    public void debuff(Character target) {
    	target.status--;
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
}
