package progetto.model;

import progetto.view.Assets;

public class ItemDatabase {

    public static Item getSword() {
        return new Item("Sword", 1, 200, 0, Assets.SWORD);
    }

    public static Item getArmor() {
        return new Item("Armor", 1, 200, 1, Assets.ARMOR);
    }
    public static Item getBFSword() {
        return new Item("BF Sword", 3, 500, 0, Assets.BF_SWORD);
    }

    public static Item getChainmail() {
        return new Item("Chainmail", 3, 500, 1, Assets.CHAINMAIL);
    }
    
    public static Item getStormrazor() {
        return new Item("Stormrazor", 5, 1000, 0, Assets.STORMRAZOR);
    }
    
    public static Item getWintersApproach() {
        return new Item("Winters Approach", 5, 1000, 1, Assets.WINTERS_APPROACH);
    }
    
    public static Item getBlackCleaver() {
        return new Item("Black Cleaver", 7, 2000, 0, Assets.BLACK_CLEAVER);
    }
    
    public static Item getThornmail() {
        return new Item("Thornmail", 7, 2000, 1, Assets.THORNMAIL);
    }
    
    public static Item getInfinityEdge() {
        return new Item("Infinity Edge", 10, 4000, 0, Assets.INFINITY_EDGE);
    }
    
    public static Item getGargoyleStoneplate() {
        return new Item("Gargoyle Stoneplate", 10, 4000, 1, Assets.GARGOYLE_STONEPLATE);
    }
}
