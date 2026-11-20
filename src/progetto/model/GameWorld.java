package progetto.model;

import java.util.ArrayList;
import java.util.Random;

 // il mondo di gioco (mappa, ostacoli, nemici)
public class GameWorld extends Map {

    private static final int COLUMNS = 50;
    private static final int ROWS = 50;

    private Random rnd = new Random();
    private int enemyId = 4;
    
    private int enemyCountZone[]  = new int[4];

    public GameWorld(int tileSize, Player player ) {
    	super(tileSize, player, COLUMNS, ROWS);
        for(int i = 0; i < 4; i++) this.enemyCountZone[i] = 0;
    	this.generateMap();
    }

    //se un nemico è stato sconfitto ne compare uno nuovo e muove i nemici
    public void update() {
    	for (WorldObject object : this.objectHitbox) {
            if (object instanceof Enemy && !((Enemy) object).isBoss()) {
                ((Enemy) object).update(this, this.player);
            }
        }
        for (int i = 0; i < 4; i++) this.checkEnemy(i);
    }

    /** Riporta il mondo allo stato iniziale (usato dopo un game over). */
    public void reset() {
        this.objectHitbox.clear();
        for(int i = 0; i < 4; i++) this.enemyCountZone[i] = 0;
        this.enemyId = 4;
        this.generateMap();
    }

    // genero la mappa con i muri
    protected void generateMap() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (j == 49 || j == 9 || (j < 9 && (i == 20 || i == 29)) || j == 0 && (i < 29 && i > 20) || (i == 0 || i == 49) && j > 9) {
                	// muri
                    map[i][j] = 1;
                    this.objectHitbox.add(new Wall(i * this.tileSize, j * this.tileSize, this.tileSize, this.tileSize));
                } else if  ((j > 9 && j < 50) && (i > 23 && i < 26) ||
                		j == 29 || j == 30 || (i == 12 || i == 13) && (j == 28 || j == 31) ||
                		(i == 37 || i == 38) && (j == 28 || j == 31)) {
                	// sentieri
                	map[i][j] = 7;
            	} else  if ((j == 28 || j == 31) && (i != 12 && i != 13 && i != 37 && i != 38)) {
            		// staccionate
            		map[i][j] = 8;
            		this.objectHitbox.add(new Fence(i * this.tileSize, j * this.tileSize, this.tileSize, this.tileSize));
            	} else {
            		// erba
                    map[i][j] = 0;
                }
            }
        }

        // posiziono nemici, alberi e cespugli
        this.putObject(5, new Enemy(0, "Skeleton Boss", 100, 5, 2 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(1, "Knight Boss", 100, 5, 45 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(2, "Skeleton Boss", 100, 5, 45 * this.tileSize, 45 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(3, "Skeleton Boss", 100, 5, 2 * this.tileSize, 45 * this.tileSize, this.tileSize, this.tileSize));
        
        this.putObject(6, new Shop(20 * this.tileSize, 20 * this.tileSize, this.tileSize * 3, this.tileSize * 4, this.player));
        
        for (int i = 0; i < 10; i++)
            if (!this.putObject(2, new Tree(this.rnd.nextInt(2, 48) * this.tileSize, this.rnd.nextInt(11, 48) * this.tileSize, this.tileSize, this.tileSize))) i--;
        for (int i = 0; i < 30; i++)
            if (!this.putObject(4, new Bush(this.rnd.nextInt(2, 48) * this.tileSize + 10, this.rnd.nextInt(11, 48) * this.tileSize + 10, this.tileSize - 20, this.tileSize - 20))) i--;
        
        for(int i = 0; i < 4; i++) this.checkEnemy(i);
    }
    
    // controllo quanti nemici ci sono, se un nemico è stato sconfitto ne aggiungo uno nuovo
    private void checkEnemy(int i) {
    	while(this.enemyCountZone[i] < 5) {
	    	switch(i) {
	    		case 0:
	    			if (!this.putObject(3, new Enemy(this.enemyId, "Skeleton", 10, Math.max(1, this.player.getLevel()+this.rnd.nextInt(-1,+2)), (this.rnd.nextInt(0, 20)) * this.tileSize, (10 + this.rnd.nextInt(0, 20)) * this.tileSize, this.tileSize, this.tileSize))) this.enemyCountZone[i]--;
	    			break;
	    		case 1:
	    			if (!this.putObject(3, new Enemy(this.enemyId, "Skeleton", 10, Math.max(1, this.player.getLevel()+this.rnd.nextInt(-1,+2)), (this.rnd.nextInt(0, 20)) * this.tileSize, (30 + this.rnd.nextInt(0, 20)) * this.tileSize, this.tileSize, this.tileSize))) this.enemyCountZone[i]--;
	    			break;
	    		case 2:
	    			if (!this.putObject(3, new Enemy(this.enemyId, "Skeleton", 10, Math.max(1, this.player.getLevel()+this.rnd.nextInt(-1,+2)), (30 + this.rnd.nextInt(0, 20)) * this.tileSize, (10 + this.rnd.nextInt(0, 20)) * this.tileSize, this.tileSize, this.tileSize))) this.enemyCountZone[i]--;
	    			break;
	    		case 3:
	    			if (!this.putObject(3, new Enemy(this.enemyId, "Skeleton", 10, Math.max(1, this.player.getLevel()+this.rnd.nextInt(-1,+2)), (30 + this.rnd.nextInt(0, 20)) * this.tileSize, (30 + this.rnd.nextInt(0, 20)) * this.tileSize, this.tileSize, this.tileSize))) this.enemyCountZone[i]--;
	    			break;
	    	}
	    	this.enemyCountZone[i]++;
	        this.enemyId++;
    	}
    }

    /** Calpesta i cespugli che si trovano nel rettangolo. */
    public void trampleBushes(Box area) {
        for (WorldObject object : this.objectHitbox) {
            if (object instanceof Bush && area.intersects(object.getHitbox())) ((Bush) object).trample();
        }
    }

    public void removeEnemy(Enemy enemy) {
        this.objectHitbox.remove(enemy);
        // i boss non vengono contati tra i nemici da rimpiazzare
        if (!enemy.isBoss()) removeEnemyCount(enemy);
    }
    
    private void removeEnemyCount(Enemy enemy) {
        Box spawn = new Box(enemy.getSpawnX(), enemy.getSpawnY(), 1, 1);
        if(spawn.intersects(new Box(0, 500, 1000, 1000))) this.enemyCountZone[0]--;
        else if(spawn.intersects(new Box(0, 1500, 1000, 1000))) this.enemyCountZone[1]--;
        else if(spawn.intersects(new Box(1500, 500, 1000, 1000))) this.enemyCountZone[2]--;
        else if(spawn.intersects(new Box(1500, 1500, 1000, 1000))) this.enemyCountZone[3]--;
    }

    /** True se la casella (x, y) è coperta d'erba (cioè non è un muro). */
    public boolean isGrassTile(int x, int y) {
        return this.map[x][y] != 1;
    }
    
    public boolean isPathTile(int x, int y) {
        return this.map[x][y] == 7;
    }
}
