package ru.enke.sansara.WorldGen;

import ru.enke.sansara.WorldGen.Chunk.Chunk;

import java.util.Random;

public class coalPopulator implements ObjectPopulator {

    @Override
    public void populate(Chunk chunk) {
        Random random = new Random();
        int a;
        int b;
        int height;

        for (int tries = 0; tries < 64; tries++) {
            a = random.nextInt(16);
            b = random.nextInt(16);
            height = chunk.getHeightAt(a, b) - random.nextInt(15);
            if (chunk.getBlockAt(a, height, b) == null) {
                return;
            } else if (chunk.getBlockAt(a, height, b).getState().getId() == 1 /* stone */) {
                chunk.setBlocks(a, height, b, 16);
            }
        }
    }
}
