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

    public abstract void attack(Character target);

    /**
     * Subisce un attacco: l'armatura riduce il danno. Gli HP non scendono sotto zero.
     * (Con la stessa formula di prima: HP - (attacco - armatura), ma l'armatura non può mai curare.)
     */
    public void takeDamage(int rawDamage) {
        int damage = Math.max(0, rawDamage - this.getEquipment(1).getValue());
        this.currentHP = Math.max(0, this.currentHP - damage);
    }

    /** Recupera HP, senza superare il massimo. */
    public void heal(int amount) {
        this.currentHP = Math.min(this.totalHP, this.currentHP + amount);
    }
    public void slash(int amount, Character target) {
        target.currentHP = Math.max(0, target.currentHP - amount);
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

    protected void setSkill(Skill skill) {
        this.skills.add(skill);
    }

    protected void levelUp() {
        this.level++;
    }

    protected void updateStrength() {
        this.strength = this.level * 3;
    }
}
