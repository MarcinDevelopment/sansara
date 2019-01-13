package ru.enke.sansara.WorldGen;

import ru.enke.sansara.Utils.MathUtils;
import ru.enke.sansara.Utils.SimplexNoise;
import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;

public class NormalWorldGenerator implements WorldGenerator {

    private final int CHUNK_SIZE = 16;
    private final int SEED = 786410974;

    @Override
    public Chunk generate(int chunkX, int chunkZ) {
        float[][] noise = getHeightmap();
        Chunk chunk = new Chunk(new ChunkCoordinates(chunkX, chunkZ));

        for (int x1 = 0; x1 < noise.length; x1++) {
            for (int z1 = 0; z1 < noise[0].length; z1++) {
                int y = 119 + (int) (noise[x1][z1] * 16);
                chunk.setBlocks(x1, y--, z1, 2);
                for (int d = 0; d < 16; d++) {
                    if (d == 0) {
                        chunk.setBlocks(x1, y--, z1, 3);
                    } else if (d == 15) {
                        chunk.setBlocks(x1, y - 1, z1, 7);
                    }
                    chunk.setBlocks(x1, y--, z1, 1);
                }
            }
        }
        return chunk;
    }

    private float[][] getHeightmap() {
        SimplexNoise simplexNoise = new SimplexNoise(SEED);
        float[][] noise = new float[CHUNK_SIZE][CHUNK_SIZE];
        float freq = 0.0023f;
        float weight = 1;

        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                for (int y = 0; y < CHUNK_SIZE; y++) {
                    noise[x][y] += (float) simplexNoise.noise(x * freq, y * freq) * weight;
                    noise[x][y] = MathUtils.clamp(noise[x][y], -1.0f, 1.0f);
                }
            }
            freq *= 3.5f;
            weight *= 0.5f;
        }

        return noise;
    }
}
