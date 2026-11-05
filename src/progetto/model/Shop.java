package progetto.model;

import java.util.ArrayList;

public class Shop extends WorldObject{
	
	private Player player;
	private ArrayList<Item> items;
	private Box interactRange;
	
	public Shop(int tileWorldX, int tileWorldY, int width, int height, Player player) {
		super(tileWorldX, tileWorldY, width, height);
		this.player = player;
		this.items = new ArrayList<>(2);
		this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
	}
	private void updateShop() {
		if(player.getLevel() <= 5) {
			this.items.add(ItemDatabase.getSword());
			this.items.add(ItemDatabase.getArmor());
		}
	}
	public Item getItem(int i) {
		return this.items.get(i);
	}
	public Box getInteractRange() {
        return this.interactRange;
    }
}
