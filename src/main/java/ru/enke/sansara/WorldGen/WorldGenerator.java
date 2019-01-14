package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;

public interface WorldGenerator {
    Chunk generate(int chunkX, int chunkZ, long seed);
}