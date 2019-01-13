package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;

import java.util.Random;

public class plantsPopulator implements ObjectPopulator {

    @Override
    public void populate(Chunk chunk) {
        Random random = new Random();
        int a;
        int b;
        int height;

        for (int tries = 0; tries < 8; tries++) {
            a = random.nextInt(16);
            b = random.nextInt(16);
            height = chunk.getHeightAt(a, b);
            if (chunk.getBlockAt(a, height, b).getState().getId() == 2 /* grass */) {
                chunk.setBlocks(a, height + 1, b, random.nextInt(39 - 37) + 37);
            }
        }
    }
}
