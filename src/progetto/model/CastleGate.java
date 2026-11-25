package progetto.model;

public class CastleGate extends WorldObject {
	
	private boolean open = false;
	
    public CastleGate(int tileWorldX, int tileWorldY, int width, int height) {
        super(tileWorldX, tileWorldY, width, height);
    }
    
    public void openGate() {
    	this.open = true;
    }
    public boolean isOpen() {
    	return this.open;
    }
}
