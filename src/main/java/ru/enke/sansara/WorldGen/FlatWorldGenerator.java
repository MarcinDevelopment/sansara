package ru.enke.sansara.WorldGen;

import ru.enke.sansara.Block.Material;
import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

import static ru.enke.sansara.Utils.Constants.CHUNK_SIZE;

public class FlatWorldGenerator implements WorldGenerator {

    @Override
    public Chunk generate(int chunkX, int chunkZ, long seed) {
        Chunk chunk = new Chunk(new ChunkCoordinates(chunkX, chunkZ));

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
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