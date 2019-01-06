package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

public class FlatWorldGenerator implements WorldGenerator {

    @Override
    public Chunk generate(int chunkX, int chunkZ) {
        Chunk chunk = new Chunk(new ChunkCoordinates(chunkX, chunkZ));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 61; y++) {
                    int id = 0;

                    if (y == 60) {
                        id = 2;
                    } else if (y >= 55) {
                        id = 3;
                    } else if (y == 0) {
                        id = 7;
                    } else {
                        id = 1;
                    }
                    //chunk.setBlockIdAt(x, y, z, id);
                }
            }
        }
        return chunk;
    }
}