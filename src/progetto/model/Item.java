package progetto.model;

import java.awt.image.BufferedImage;

public class Item {
    private String name;
    private int value;
    private int cost;
    private int equipmentSlot;
    private BufferedImage image;

    public Item(String name, int value, int cost, int equipmentSlot, BufferedImage image) {
        this.name = name;
        this.value = value;
        this.cost = cost;
        this.equipmentSlot = equipmentSlot;
        this.image = image;
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
    
    public BufferedImage getItemImage () {
    	return this.image;
    }
}
