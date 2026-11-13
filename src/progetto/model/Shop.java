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
		if(player.getLevel() <= 5) {
			this.items.add(ItemDatabase.getBFSword());
			this.items.add(ItemDatabase.getChainmail());
		}
	}
	public boolean buy(int index, Player player) {
	    Item item = items.get(index);
	    if (item == player.getEquipment(index) || player.getGold() < item.getCost()) return false;
	    player.changeEquipment(index, item);
	    player.giveGold(-item.getCost());
	    return true;
	}
	public Item getItem(int i) {
		return this.items.get(i);
	}
	public Box getInteractRange() {
        return this.interactRange;
    }
}
