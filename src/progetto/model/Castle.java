package progetto.model;

import java.util.ArrayList;

public class Castle extends Map{
	private static final int COLUMNS = 10;
    private static final int ROWS = 10;
    

    private int tileSize;
    private int[][] map = new int[COLUMNS][ROWS];
    private Player player;
    private ArrayList<WorldObject> objectHitbox = new ArrayList<>();

    
    public Castle(int tileSize, Player player) {
    	this.player = player;
    	this.tileSize = tileSize;
    	this.generateMap();
    }
    
    protected void generateMap() {
    	for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if ((j == 0) || (j == 10) || (i == 0) || (i == 10)) {
                    map[i][j] = 1;
                    this.objectHitbox.add(new Wall(i * this.tileSize, j * this.tileSize, this.tileSize, this.tileSize));
                } else {
                    map[i][j] = 0;
                }
            }
        }
    }
    
    // funzione usata per mettere tutti gli oggetti
    private boolean putObject(int objectNum, WorldObject object) {
        if (this.map[object.getX() / this.tileSize][object.getY() / this.tileSize] == 0) {
            this.map[object.getX() / this.tileSize][object.getY() / this.tileSize] = objectNum;
            this.objectHitbox.add(object);
            return true;
        } else {
            return false;
        }
    }
}
