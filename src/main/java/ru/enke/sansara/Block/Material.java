package ru.enke.sansara.Block;

public enum Material {
    //TODO add all materials
    AIR(0),
    STONE(1),
    GRASS(2),
    DIRT(3),
    COBBLESTONE(4),
    WOOD(5),
    BEDROCK(7),
    SAND(12),
    GRAVEL(13),
    GOLD_ORE(14),
    IRON_ORE(15),
    COAL_ORE(16),
    LOG(17),
    LEAVES(18),

    WOOL(35),

    YELLOW_FLOWER(37),
    RED_ROSE(38);

    private int id;

    Material(int material) {
        this.id = material;
    }

    public static Material fromId(int id) {
        for (Material mt : Material.values()) {
            if (mt.id == id) {
                return mt;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }
}