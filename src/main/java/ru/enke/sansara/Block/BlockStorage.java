package ru.enke.sansara.Block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class BlockStorage {
    private static final Logger logger = LogManager.getLogger();
    private final HashMap<Integer, Block> blockStorage = new HashMap<>();

    public BlockStorage() {
        registerBlock(1, new BlockStone());
        registerBlock(2, new BlockGrass());
    }

    @Nullable
    public Block getBlockById(int id) {
        return blockStorage.get(id);
    }

    private void registerBlock(int id, Block block) {
        logger.info("registering block (" + block.getName() + "[id=" + id + "])");
        blockStorage.put(id, block);
    }
}
