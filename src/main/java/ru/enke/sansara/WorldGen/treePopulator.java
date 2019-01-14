package ru.enke.sansara.WorldGen;

import ru.enke.sansara.Block.Material;
import ru.enke.sansara.WorldGen.Chunk.Chunk;

import java.util.Random;

public class treePopulator implements ObjectPopulator {

    @Override
    public void populate(Chunk chunk, long seed) {
        Random random = new Random(seed);
        int a;
        int b;
        int height;
        //TODO: Finish it
        for (int tries = 0; tries < 2; tries++) {
            a = random.nextInt(16);
            b = random.nextInt(16);
            height = chunk.getHeightAt(a, b);
            if (chunk.getBlockAt(a, height, b).getState().getId() == Material.GRASS.getId() ||
                    chunk.getBlockAt(a, height, b).getState().getId() == Material.DIRT.getId()) {

                for (int y = 0; y < 6; y++) {
                    chunk.setBlockAt(a, height + y, b, Material.LOG.getId(), (byte) 0);
                }
            }
        }
    }
}
