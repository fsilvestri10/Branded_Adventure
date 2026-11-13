package progetto.model;

import java.util.ArrayList;

public class Castle extends Map{
	private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    public Castle(int tileSize, Player player, int mapX, int mapY, ArrayList<WorldObject> objectHitbox) {
    	super(tileSize, player, COLUMNS, ROWS);
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
    
}
