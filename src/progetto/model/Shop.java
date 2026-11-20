package progetto.model;

import java.util.ArrayList;

public class Shop extends WorldObject{
	
	private ArrayList<Item> items;
	private Box interactRange;
	
	public Shop(int tileWorldX, int tileWorldY, int width, int height, Player player) {
		super(tileWorldX, tileWorldY, width, height);
		this.items = new ArrayList<>();
		this.interactRange = new Box(this.tileWorldX - 10, this.tileWorldY - 10, this.width + 20, this.height + 20);
		this.items.add(ItemDatabase.getBFSword());
		this.items.add(ItemDatabase.getStormrazor());
		this.items.add(ItemDatabase.getBlackCleaver());
		this.items.add(ItemDatabase.getInfinityEdge());
		this.items.add(ItemDatabase.getChainmail());
		this.items.add(ItemDatabase.getWintersApproach());
		this.items.add(ItemDatabase.getThornmail());
		this.items.add(ItemDatabase.getGargoyleStoneplate());
	}
	
	public boolean buy(int index, Player player) {
	    Item item = items.get(index);
	    int slot = item.getEquipmentSlot();
	    
	    if (item == player.getEquipment(slot) || player.getGold() < item.getCost()) return false;
	    player.changeEquipment(slot, item);
	    player.giveGold(-item.getCost());

	    return true;
	}
	
	public ArrayList<Item> getItems() {
	    return new ArrayList<>(this.items);
	}
	
	public Item getItem(int i) {
		return this.items.get(i);
	}
	
	public Box getInteractRange() {
        return this.interactRange;
    }
}
