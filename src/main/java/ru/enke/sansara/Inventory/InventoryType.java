package ru.enke.sansara.Inventory;

public enum InventoryType {
    PLAYER_INVENTORY(0, 45, "minecraft:container", "Inventory"),
    CHEST(1, 27, "minecraft:chest", "Chest"),
    LARGE_CHEST(2, 54, "minecraft:chest", "Large Chest"),
    ENDER_CHEST(2, 27, "minecraft:container", "Ender Chest"),
    FURNANCE(3, 3, "minecraft:furnace", "Furnace"),
    CRAFTING_TABLE(4, 0, "minecraft:crafting_table", "Crafting");
    //ENCHANTMENT_TABLE();
    /*DISPENSER(),
    BREWING_STAND(),
    BEACON(),
    HOPPER(); */
    //TODO: Entity inv.

    private int typeid;
    private int capacity;
    private String type;
    private String defaultName;

    InventoryType(int typeid, int capacity, String type, String defaultName) {
        this.typeid = typeid;
        this.capacity = capacity;
        this.type = type;
        this.defaultName = defaultName;
    }

    public int getTypeid() {
        return typeid;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getWindowType() {
        return type;
    }

    public String getDefaultName() {
        return defaultName;
    }
}
