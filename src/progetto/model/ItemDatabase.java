package progetto.model;

import progetto.model.Item.ItemId;
import progetto.view.Assets;

public class ItemDatabase {

    public static Item getSword() {
        return new Item(ItemId.SWORD, "Sword", 1, 200, 0);
    }

    public static Item getArmor() {
        return new Item(ItemId.ARMOR, "Armor", 1, 200, 1);
    }
    public static Item getBFSword() {
        return new Item(ItemId.BF_SWORD, "BF Sword", 3, 500, 0);
    }

    public static Item getChainmail() {
        return new Item(ItemId.CHAINMAIL, "Chainmail", 3, 500, 1);
    }
    
    public static Item getStormrazor() {
        return new Item(ItemId.STORMRAZOR, "Stormrazor", 5, 1000, 0);
    }
    
    public static Item getWintersApproach() {
        return new Item(ItemId.WINTERS_APPROACH, "Winters Approach", 5, 1000, 1);
    }
    
    public static Item getBlackCleaver() {
        return new Item(ItemId.BLACK_CLEAVER, "Black Cleaver", 7, 2000, 0);
    }
    
    public static Item getThornmail() {
        return new Item(ItemId.THORNMAIL, "Thornmail", 7, 2000, 1);
    }
    
    public static Item getInfinityEdge() {
        return new Item(ItemId.INFINITY_EDGE, "Infinity Edge", 10, 4000, 0);
    }
    
    public static Item getGargoyleStoneplate() {
        return new Item(ItemId.GARGOYLE_STONEPLATE, "Gargoyle Stoneplate", 10, 4000, 1);
    }
}
