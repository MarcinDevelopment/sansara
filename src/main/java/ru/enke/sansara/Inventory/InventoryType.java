package ru.enke.sansara.Inventory;

public enum InventoryType {
    //NAME | ID | CAPACITY
    PLAYER_INVENTORY(0, 45);
    /*CHEST(),
    LARGE_CHEST(),
    CRAFTING_TABLE(),
    FURNACE(),
    DISPENSER(),
    ENCHANTMENT_TABLE(),
    BREWING_STAND(),
    BEACON(),
    ANVIL(),
    HOPPER(); */
    //TODO: Entity inv.

    private int typeid;
    private int capacity;

    InventoryType(int typeid, int capacity) {
        this.typeid = typeid;
        this.capacity = capacity;
    }

    public int getTypeid() {
        return typeid;
    }

    public int getCapacity() {
        return capacity;
    }
}
