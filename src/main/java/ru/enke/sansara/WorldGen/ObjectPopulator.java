package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;

public interface ObjectPopulator {
    void populate(Chunk chunk, long seed);
}
