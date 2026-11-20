package progetto.model;

import java.awt.image.BufferedImage;

public class Item {
	private ItemId id;
    private String name;
    private int value;
    private int cost;
    private int equipmentSlot;

    public Item(ItemId id, String name, int value, int cost, int equipmentSlot) {
        this.id = id;
    	this.name = name;
        this.value = value;
        this.cost = cost;
        this.equipmentSlot = equipmentSlot;
    }
    public enum ItemId {
        SWORD, ARMOR, BF_SWORD, CHAINMAIL, STORMRAZOR, WINTERS_APPROACH,
        BLACK_CLEAVER, THORNMAIL, INFINITY_EDGE, GARGOYLE_STONEPLATE
    }
    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public int getEquipmentSlot() {
    	return this.equipmentSlot;
    }
    
    public ItemId getId() {
    	return this.id; 
    }
}
