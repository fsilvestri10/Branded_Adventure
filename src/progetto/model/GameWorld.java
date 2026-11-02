package progetto.model;

import java.util.ArrayList;
import java.util.Random;

/** MODEL: il mondo di gioco (mappa, ostacoli, nemici). Nessuna immagine e nessun disegno. */
public class GameWorld {

    private static final int COLUMNS = 50;
    private static final int ROWS = 50;

    private int tileSize;
    private int[][] map = new int[COLUMNS][ROWS];
    private Random rnd = new Random();
    private ArrayList<WorldObject> objectHitbox = new ArrayList<>();
    private int enemyCount = 0;
    private int enemyId = 4;

    public GameWorld(int tileSize) {
        this.tileSize = tileSize;
        this.generateMap();
    }

    /** Da chiamare a ogni aggiornamento: se un nemico è stato sconfitto ne compare uno nuovo. */
    public void update() {
        this.checkEnemy();
    }

    /** Riporta il mondo allo stato iniziale (usato dopo un game over). */
    public void reset() {
        this.objectHitbox.clear();
        this.enemyCount = 0;
        this.enemyId = 4;
        this.generateMap();
    }

    // genero la mappa con i muri
    private void generateMap() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (j == 49 || j == 9 || (j < 9 && (i == 20 || i == 29)) || j == 0 && (i < 29 && i > 20) || (i == 0 || i == 49) && j > 9) {
                    map[i][j] = 1;
                    this.objectHitbox.add(new Wall(i * this.tileSize, j * this.tileSize, this.tileSize, this.tileSize));
                } else {
                    map[i][j] = 0;
                }
            }
        }

        // posiziono nemici, alberi e cespugli
        this.putObject(5, new Enemy(0, "Skeleton Boss", 100, 5, 2 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(1, "Skeleton Boss", 100, 5, 2 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(2, "Skeleton Boss", 100, 5, 2 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        this.putObject(5, new Enemy(3, "Skeleton Boss", 100, 5, 2 * this.tileSize, 13 * this.tileSize, this.tileSize, this.tileSize));
        for (int i = 0; i < 10; i++)
            if (!this.putObject(2, new Tree(this.rnd.nextInt(2, 48) * this.tileSize, this.rnd.nextInt(11, 48) * this.tileSize, this.tileSize, this.tileSize))) i--;
        for (int i = 0; i < 30; i++)
            if (!this.putObject(4, new Bush(this.rnd.nextInt(2, 48) * this.tileSize + 10, this.rnd.nextInt(11, 48) * this.tileSize + 10, this.tileSize - 20, this.tileSize - 20))) i--;
        this.checkEnemy();
    }

    // controllo quanti nemici ci sono, se un nemico è stato sconfitto ne aggiungo uno nuovo
    private void checkEnemy() {
        while (this.enemyCount < 5) {
            if (!this.putObject(3, new Enemy(this.enemyId, "pus", 10, 2, this.rnd.nextInt(14, 36) * this.tileSize, this.rnd.nextInt(23, 36) * this.tileSize, this.tileSize, this.tileSize))) this.enemyCount--;
            this.enemyCount++;
            this.enemyId++;
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

    /** True se nel rettangolo c'è un ostacolo (nemico, albero o muro). */
    public boolean isBlocked(Box area) {
        for (WorldObject object : this.objectHitbox) {
            if ((object instanceof Enemy || object instanceof Tree || object instanceof Wall) && area.intersects(object.getHitbox())) return true;
        }
        return false;
    }

    /** Calpesta i cespugli che si trovano nel rettangolo. */
    public void trampleBushes(Box area) {
        for (WorldObject object : this.objectHitbox) {
            if (object instanceof Bush && area.intersects(object.getHitbox())) ((Bush) object).trample();
        }
    }

    /** Il nemico a portata di combattimento rispetto al giocatore, oppure null. */
    public Enemy findEnemyInRange(Player player) {
        Enemy found = null;
        for (WorldObject object : this.objectHitbox) {
            if (object instanceof Enemy && ((Enemy) object).getInteractRange().intersects(player.getHitbox())) {
                found = (Enemy) object;
            }
        }
        return found;
    }

    public void removeEnemy(Enemy enemy) {
        this.objectHitbox.remove(enemy);
        // i boss non vengono contati tra i nemici da rimpiazzare
        if (!enemy.isBoss()) this.enemyCount--;
    }

    /** True se la casella (x, y) è coperta d'erba (cioè non è un muro). */
    public boolean isGrassTile(int x, int y) {
        return this.map[x][y] != 1;
    }

    public ArrayList<WorldObject> getObjects() {
        return this.objectHitbox;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getRows() {
        return ROWS;
    }
}
