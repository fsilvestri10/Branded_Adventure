package progetto.model;

public class Bush extends WorldObject {
    private boolean trampled = false;

    public Bush(int tileWorldX, int tileWorldY, int width, int height) {
        super(tileWorldX, tileWorldY, width, height);
    }

    public void trample() {
        this.trampled = true;
    }

    public void untrample() {
        this.trampled = false;
    }

    public boolean isTrampled() {
        return this.trampled;
    }
}
