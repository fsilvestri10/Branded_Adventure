package progetto.model;

public class SkillDatabase {
    public static Skill getHeal() {
        return new Skill("Heal", SkillType.HEAL, 20, 3);
    }
    public static Skill getSlash() {
        return new Skill("Slash", SkillType.DAMAGE, 20, 3);
    }
}
