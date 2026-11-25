package progetto.model;

import java.util.Random;

public class Enemy extends Character {

    private int expDrop;
    private int goldDrop;
    private Box interactRange;
    private int id;

    // movimento
    private static final Random RND = new Random();
    private static final int SPEED = 2;            // pixel per aggiornamento
    private static final int WANDER_RANGE = 150;   // quanto può allontanarsi dal punto di nascita
    private final int spawnX, spawnY;
    
    private int waitTicks = RND.nextInt(60, 180);
    private int walkTicks = 0;           
    private int stepX, stepY;    
    
    public Enemy(int id, String name, int totalHP, int level, int tileWorldX, int tileWorldY, int width, int height) {
        super(name, totalHP, level, tileWorldX, tileWorldY, width, height, Direction.DOWN);
        this.spawnX = tileWorldX;
        this.spawnY = tileWorldY;
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
            	this.setSkill(SkillDatabase.getBuff());
                break;
            case 3:
            	this.setSkill(SkillDatabase.getDebuff());
                break;
        }
    }
    
    // metodi nel fight
    public void useSkill(Character target) {
    	switch(this.getId()) {
    		case 0:
    			this.heal(SkillDatabase.getHeal().getPower());
    			break;
    		case 1:
    			target.takeDamage(SkillDatabase.getSlash().getPower());
    			break;
    		case 2:
    			this.buff();
    			break;
    		case 3:
    			target.debuff();
    			break;
    	}
    }
    
    // metodi nella mappa
    public boolean move(int dx, int dy, GameWorld world, Player player) {
    	boolean moved = false;
    	
    	// la direzione serve alla view per scegliere lo sprite
        if (dy < 0) this.direction = Direction.UP;
        if (dy > 0) this.direction = Direction.DOWN;
        if (dx < 0) this.direction = Direction.LEFT;
        if (dx > 0) this.direction = Direction.RIGHT;

        // controllo movimento asse x, se non ci sono ostacoli mi muovo
        Box nextX = new Box(this.tileWorldX + dx, this.tileWorldY, this.width, this.height);
        world.trampleBushes(nextX);
        if (this.canGo(nextX, world, player)) {
        	this.tileWorldX += dx;
        	moved = moved || dx != 0;        
        }

        // controllo movimento asse y, se non ci sono ostacoli mi muovo
        Box nextY = new Box(this.tileWorldX, this.tileWorldY + dy, this.width, this.height);
        world.trampleBushes(nextY);
        if (this.canGo(nextY, world, player)) {
        	this.tileWorldY += dy;
        	moved = moved || dy != 0;
        }
        this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
        return moved;
    }
    // metodo per controllare se il nemico puo spostarsi in una certa direzione
    private boolean canGo(Box next, GameWorld world, Player player) {
    	if (Math.abs(next.getX() - this.spawnX) > WANDER_RANGE || Math.abs(next.getY() - this.spawnY) > WANDER_RANGE) return false;
        return !world.isBlocked(next, this) && !next.intersects(player.getHitbox());    
    }

    public void update(GameWorld world, Player player) {
    	// se il player è vicino il nemico si ferma
        if (this.interactRange.intersects(player.getHitbox())) {
            this.moving = false;
            return;
        }
        if (this.walkTicks > 0) {
            this.walkTicks--;
            this.moving = this.move(this.stepX * SPEED, this.stepY * SPEED, world, player);
            if (!this.moving) this.walkTicks = 0;   // c'è un ostacolo
        } else {
            this.moving = false;
            this.waitTicks--;
            if (this.waitTicks <= 0) this.startWalking();
        }
    }
    // sceglie una direzione a caso e per quanto tempo camminare
    private void startWalking() {
        Direction d = Direction.values()[RND.nextInt(Direction.values().length)];
        this.stepX = (d == Direction.LEFT) ? -1 : (d == Direction.RIGHT) ? 1 : 0;
        this.stepY = (d == Direction.UP) ? -1 : (d == Direction.DOWN) ? 1 : 0;
        this.walkTicks = RND.nextInt(25, 50);
        this.waitTicks = RND.nextInt(60, 180);
    }
    
    // I boss hanno id da 0 a 3, i nemici normali partono da 4
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
    public int getSpawnX() {
        return this.spawnX;
    }

    public int getSpawnY() {
        return this.spawnY;
    }
}
