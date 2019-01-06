package ru.enke.sansara.Block;

import org.jetbrains.annotations.Nullable;
import org.pmw.tinylog.Logger;

import java.util.HashMap;

public class BlockStorage {
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
        Logger.info("registering block (" + block.getName() + "[id=" + id + "])");
        blockStorage.put(id, block);
    }
}
