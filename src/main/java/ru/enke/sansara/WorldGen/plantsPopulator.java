package ru.enke.sansara.WorldGen;

import ru.enke.sansara.Block.Material;
import ru.enke.sansara.WorldGen.Chunk.Chunk;

import java.util.Random;

public class plantsPopulator implements ObjectPopulator {

    @Override
    public void populate(Chunk chunk, long seed) {
        Random random = new Random(seed);
        int a;
        int b;
        int height;

        for (int tries = 0; tries < 8; tries++) {
            a = random.nextInt(16);
            b = random.nextInt(16);
            height = chunk.getHeightAt(a, b);
            if (chunk.getBlockAt(a, height, b).getState().getId() == Material.GRASS.getId()) {
                chunk.setBlockAt(a, height + 1, b, random.nextInt(39 - 37) + 37, (byte) 0);
            }
        }
    }
}
