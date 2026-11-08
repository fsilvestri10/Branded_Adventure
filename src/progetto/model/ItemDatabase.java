package progetto.model;

public class ItemDatabase {

    public static Item getSword() {
        return new Item("Sword", 1, 200);
    }

    public static Item getArmor() {
        return new Item("Armor", 1, 200);
    }
    public static Item getBFSword() {
        return new Item("BFSword", 3, 500);
    }
    public static Item getDirk() {
        return new Item("dirk", 5, 1000);
    }
    public static Item getChainmail() {
        return new Item("Chainmail", 3, 500);
    }
}
