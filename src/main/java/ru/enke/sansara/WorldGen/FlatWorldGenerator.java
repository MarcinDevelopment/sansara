package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

public class FlatWorldGenerator implements WorldGenerator {

    @Override
    public Chunk generate(int chunkX, int chunkZ) {
        Chunk chunk = new Chunk(new ChunkCoordinates(chunkX, chunkZ));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 60; y < 121; y++) {
                    int id;

                    if (y == 120) {
                        id = 2;
                    } else if (y >= 113) {
                        id = 3;
                    } else if (y == 60) {
                        id = 7;
                    } else {
                        id = 1;
                    }
                    chunk.setBlocks(x, y, z, id);
                }
            }
        }
        return chunk;
    }
}