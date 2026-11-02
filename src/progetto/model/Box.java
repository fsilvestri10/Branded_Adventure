package progetto.model;

/**
 * MODEL: rettangolo nel mondo di gioco (posizione e dimensioni in pixel).
 * Sostituisce Rectangle, così il model non dipende da nessuna libreria grafica.
 */
public class Box {
    private final int x, y, width, height;

    public Box(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Box other) {
        return this.x < other.x + other.width && this.x + this.width > other.x
            && this.y < other.y + other.height && this.y + this.height > other.y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
}
