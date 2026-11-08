package progetto.model;

public class Item {
    private String name;
    private int value;
    private int cost;

    public Item(String name, int value, int cost) {
        this.name = name;
        this.value = value;
        this.cost = cost;
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
}
