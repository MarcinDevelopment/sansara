package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

public class FlatWorldGenerator implements WorldGenerator {

    @Override
    public Chunk generate(int chunkX, int chunkZ) {
        return new Chunk(new ChunkCoordinates(chunkX, chunkZ));
    }
}