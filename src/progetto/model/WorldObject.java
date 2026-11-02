package progetto.model;

/** MODEL: qualunque cosa abbia una posizione nel mondo (muri, alberi, cespugli, personaggi). */
public abstract class WorldObject {

    protected int tileWorldX, tileWorldY;
    protected int width, height;

    public WorldObject(int tileWorldX, int tileWorldY, int width, int height) {
        this.tileWorldX = tileWorldX;
        this.tileWorldY = tileWorldY;
        this.width = width;
        this.height = height;
    }

    // rettangolo che rappresenta lo spazio occupato nel mondo dall'oggetto
    public Box getHitbox() {
        return new Box(this.tileWorldX, this.tileWorldY, this.width, this.height);
    }

    public int getX() {
        return this.tileWorldX;
    }

    public int getY() {
        return this.tileWorldY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
