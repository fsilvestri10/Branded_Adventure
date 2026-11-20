package progetto.model;

public class SkillDatabase {
    public static Skill getHeal() {
        return new Skill("Heal", SkillType.HEAL, 20, 3);
    }
    public static Skill getSlash() {
        return new Skill("Slash", SkillType.DAMAGE, 20, 3);
    }
    public static Skill getBuff() {
    	return new Skill("Buff", SkillType.BUFF, 3, 5);
    }
    public static Skill getDebuff() {
    	return new Skill("Debuff", SkillType.DEBUFF, 3, 5);
    }
}
