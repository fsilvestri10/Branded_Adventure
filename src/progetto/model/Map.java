package progetto.model;

import java.util.ArrayList;

public abstract class Map {

	protected int tileSize;
    protected int[][] map;
    protected Player player;
    protected ArrayList<WorldObject> objectHitbox;
    private int columns, rows;
    
    public Map(int tileSize, Player player, int mapX, int mapY) {
    	this.tileSize = tileSize;
    	this.player = player;
    	this.map = new int[mapX][mapY];
    	this.objectHitbox = new ArrayList<>();
    	this.columns = mapX;
    	this.rows = mapY;
    }
	
	protected abstract void generateMap();
	
	// funzione usata per mettere tutti gli oggetti
    protected boolean putObject(int objectNum, WorldObject object) {
        if (this.map[object.getX() / this.tileSize][object.getY() / this.tileSize] == 0) {
            this.map[object.getX() / this.tileSize][object.getY() / this.tileSize] = objectNum;
            this.objectHitbox.add(object);
            return true;
        } else {
            return false;
        }
    }
    /** True se nel rettangolo c'è un ostacolo (nemico, albero o muro). */
    public boolean isBlocked(Box area) {
        for (WorldObject object : this.objectHitbox) {
            if ((object instanceof Enemy || object instanceof Tree || object instanceof Wall || object instanceof Shop) && area.intersects(object.getHitbox())) return true;
        }
        return false;
    }

    /** Il nemico a portata di combattimento rispetto al giocatore, oppure null. */
    public Object findEnemyInRange(Player player) {
        Object found = null;
        for (WorldObject object : this.objectHitbox) {
            if (object instanceof Enemy && ((Enemy) object).getInteractRange().intersects(player.getHitbox())) {
                found = (Enemy) object;
            } else if(object instanceof Shop && ((Shop) object).getInteractRange().intersects(player.getHitbox())) {
            	found = (Shop) object;
            }
        }
        return found;
    }
    public ArrayList<WorldObject> getObjects() {
        return this.objectHitbox;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getRows() {
        return this.rows;
    }
}
