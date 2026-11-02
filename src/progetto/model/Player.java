package progetto.model;

/**
 * MODEL: il giocatore.
 * Contiene solo stato e regole di gioco: nessuna grafica, nessun input, nessuna schermata.
 */
public class Player extends Character {

    // velocità in pixel per ogni aggiornamento (il controller la usa per calcolare dx e dy)
    public static final int SPEED = 10;

    private Direction direction = Direction.DOWN;
    private boolean moving = false;
    private int exp;
    private int gold;

    public Player(String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height) {
        super(name, totalHP, level, tileWorldX, tileWorldY, width, height);
    }

    /**
     * Sposta il giocatore di (dx, dy) se non ci sono ostacoli.
     * Gli assi sono controllati separatamente, così si può "strisciare" lungo i muri.
     * Non legge la tastiera: dx e dy li decide il controller.
     */
    public void move(int dx, int dy, GameWorld world) {
        this.moving = (dx != 0 || dy != 0);

        // la direzione serve alla view per scegliere lo sprite
        if (dy < 0) this.direction = Direction.UP;
        if (dy > 0) this.direction = Direction.DOWN;
        if (dx < 0) this.direction = Direction.LEFT;
        if (dx > 0) this.direction = Direction.RIGHT;

        // controllo movimento asse x, se non ci sono ostacoli mi muovo
        Box nextX = new Box(this.tileWorldX + dx, this.tileWorldY, this.width, this.height);
        world.trampleBushes(nextX);
        if (!world.isBlocked(nextX)) this.tileWorldX += dx;

        // controllo movimento asse y, se non ci sono ostacoli mi muovo
        Box nextY = new Box(this.tileWorldX, this.tileWorldY + dy, this.width, this.height);
        world.trampleBushes(nextY);
        if (!world.isBlocked(nextY)) this.tileWorldY += dy;
    }

    @Override
    public void attack(Character target) {
        // l'armatura del bersaglio è considerata dentro takeDamage()
        target.takeDamage(this.getStrength() + this.getEquipment(0).getValue());
    }

    /** Usa l'abilità in posizione index (per ora esiste solo la cura). */
    public void useSkill(int index, Enemy enemy) {
        if (index < 0 || index >= this.getSkills().size()) return;
        Skill skill = this.getSkills().get(index);
        switch (skill.getType()) {
            case HEAL:
                this.heal(skill.getPower());
                break;
            default:
                break;
        }
    }

    public void learnSkill(Skill skill) {
        this.setSkill(skill);
    }

    public void giveExp(int expBonus) {
        this.exp += expBonus;
        // salita di livello: servono livello*20 punti esperienza
        while (this.exp >= this.expToNextLevel()) {
            this.exp -= this.expToNextLevel();
            this.levelUp();
            this.updateStrength();
        }
    }

    public void giveGold(int goldBonus) {
        this.gold += goldBonus;
    }

    public int expToNextLevel() {
        return this.getLevel() * 20;
    }

    /** Dopo un game over: il giocatore riparte da (x, y) con la vita al massimo. */
    public void respawn(int x, int y) {
        this.tileWorldX = x;
        this.tileWorldY = y;
        this.moving = false;
        this.direction = Direction.DOWN;
        this.heal(this.getTotalHP());
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public int getExp() {
        return this.exp;
    }

    public int getGold() {
        return this.gold;
    }
}
