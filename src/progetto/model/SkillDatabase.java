package progetto.model;

public class SkillDatabase {
    public static Skill getHeal() {
        return new Skill("Heal", SkillType.HEAL, 20);
    }
}
