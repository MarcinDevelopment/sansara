package ru.enke.sansara.Entity;

public enum EntityType {
    CAVE_SPIDER(52), CHICKEN(93),
    COW(92), CREEPER(50), GUARDIAN(68),
    OCELOT(98), MAGMA_CUBE(62),
    PIG(90), SHEEP(91),
    SKELETON(51), SLIME(55),
    SPIDER(59), WOLF(95),
    ZOMBIE(54), HORSE(100);

    private int mobid;

    EntityType(int mobid) {
        this.mobid = mobid;
    }

    public int getId() {
        return mobid;
    }

    public static EntityType fromId(int mobid) {
        for (EntityType mt : EntityType.values()) {
            if (mt.mobid == mobid) {
                return mt;
            }
        }
        return null;
    }
}