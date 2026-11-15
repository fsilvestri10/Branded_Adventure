package progetto.model;

/** MODEL: nemico (normale o boss). Nessuna immagine: la view sceglie lo sprite con isBoss(). */
public class Enemy extends Character {

    private int expDrop;
    private int goldDrop;
    private Box interactRange;
    private int id;

    public Enemy(int id, String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height) {
        super(name, totalHP, level, tileWorldX, tileWorldY, width, height, Direction.DOWN);
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
            	this.setSkill(SkillDatabase.getSlash());
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
    public void move(int dx, int dy, GameWorld world, Player player) {
    	this.moving = (dx != 0 || dy != 0);
    	// la direzione serve alla view per scegliere lo sprite
        if (dy < 0) this.direction = Direction.UP;
        if (dy > 0) this.direction = Direction.DOWN;
        if (dx < 0) this.direction = Direction.LEFT;
        if (dx > 0) this.direction = Direction.RIGHT;

        // controllo movimento asse x, se non ci sono ostacoli mi muovo
        Box nextX = new Box(this.tileWorldX + dx, this.tileWorldY, this.width, this.height);
        world.trampleBushes(nextX);
        if (!world.isBlocked(nextX) && !nextX.intersects(player.getHitbox())) {
        	this.tileWorldX += dx;
        	this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
        }

        // controllo movimento asse y, se non ci sono ostacoli mi muovo
        Box nextY = new Box(this.tileWorldX, this.tileWorldY + dy, this.width, this.height);
        world.trampleBushes(nextY);
        if (!world.isBlocked(nextY) && !nextY.intersects(player.getHitbox())) {
        	this.tileWorldY += dy;
        	this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
        }
    }

    public void useSkill(Character target) {
    	switch(this.getId()) {
    		case 0:
    			this.heal(SkillDatabase.getHeal().getPower());
    			break;
    		case 1:
    			target.takeDamage(SkillDatabase.getSlash().getPower());
    			break;
    		case 2:
    			break;
    		case 3:
    			break;
    	}
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
