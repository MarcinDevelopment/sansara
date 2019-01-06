package ru.enke.sansara.WorldGen.Chunk;

public class Chunk {

    private int x, z;

    public Chunk(ChunkCoordinates chunkCoordinates) {
        this.x = chunkCoordinates.getChunkX();
        this.z = chunkCoordinates.getChunkZ();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
