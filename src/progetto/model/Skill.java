package progetto.model;

public class Skill {
    private String name;
    private SkillType type;
    private int power;
    private int cooldown;
    private int totalCooldown;

    public Skill(String name, SkillType type, int power, int cooldown) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.cooldown = 0;
        this.totalCooldown = cooldown;
    }

    public int getCooldown() {
    	return this.cooldown;
    }
    public void decreaseCooldown() {
    	this.cooldown--;
    }
    public void resetCooldown() {
    	this.cooldown = this.totalCooldown;
    }
    public String getName() {
        return this.name;
    }

    public SkillType getType() {
        return this.type;
    }

    public int getPower() {
        return this.power;
    }
}
