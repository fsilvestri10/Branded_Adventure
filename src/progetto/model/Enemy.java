package progetto.model;

/** MODEL: nemico (normale o boss). Nessuna immagine: la view sceglie lo sprite con isBoss(). */
public class Enemy extends Character {

    private int expDrop;
    private int goldDrop;
    private Box interactRange;
    private int id;

    public Enemy(int id, String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height) {
        super(name, totalHP, level, tileWorldX, tileWorldY, width, height);
        this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
        this.id = id;
        // le ricompense variano a seconda del livello
        this.expDrop = this.getLevel() * 10;
        this.goldDrop = this.getLevel() * 100;
        // se il nemico è un boss (identificati con id da 0 a 3), gli assegniamo un'abilità
        switch (this.getId()) {
            case 0:
                this.setSkill(SkillDatabase.getHeal());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void attack(Character target) {
        target.takeDamage(this.getStrength() + this.getEquipment(0).getValue());
    }

    /** I boss hanno id da 0 a 3, i nemici normali partono da 4. */
    public boolean isBoss() {
        return this.id < 4;
    }

    public Box getInteractRange() {
        return this.interactRange;
    }

    public int getId() {
        return this.id;
    }

    public int getExp() {
        return this.expDrop;
    }

    public int getGold() {
        return this.goldDrop;
    }
}
