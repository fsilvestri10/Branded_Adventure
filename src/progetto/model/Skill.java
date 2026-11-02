package progetto.model;

public class Skill {
    private String name;
    private SkillType type;
    private int power;

    public Skill(String name, SkillType type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
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
