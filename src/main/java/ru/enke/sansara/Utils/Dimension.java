package ru.enke.sansara.Utils;

public enum Dimension {
    NETHER(-1),
    OVERWORLD(0),
    END(1);

    private int dimension;

    Dimension(int dimension) {
        this.dimension = dimension;
    }

    public int getId() {
        return dimension;
    }
}
