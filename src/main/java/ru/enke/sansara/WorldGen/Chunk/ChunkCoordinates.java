package ru.enke.sansara.WorldGen.Chunk;

public class ChunkCoordinates {

    private final int chunkX;
    private final int chunkZ;

    public ChunkCoordinates(final int chunkX, final int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }
}
