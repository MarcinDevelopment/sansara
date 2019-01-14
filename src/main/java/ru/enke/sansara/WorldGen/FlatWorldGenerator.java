package ru.enke.sansara.WorldGen;

import ru.enke.sansara.Block.Material;
import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

public class FlatWorldGenerator implements WorldGenerator {

    @Override
    public Chunk generate(int chunkX, int chunkZ, long seed) {
        Chunk chunk = new Chunk(new ChunkCoordinates(chunkX, chunkZ));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 60; y < 121; y++) {
                    int id;

                    if (y == 120) {
                        id = Material.GRASS.getId();
                    } else if (y >= 113) {
                        id = Material.DIRT.getId();
                    } else if (y == 60) {
                        id = Material.BEDROCK.getId();
                    } else {
                        id = Material.STONE.getId();
                    }
                    chunk.setBlockAt(x, y, z, id, (byte) 0);
                }
            }
        }
        return chunk;
    }
}