package ru.enke.sansara.WorldGen.Chunk;

import ru.enke.minecraft.protocol.packet.data.game.BlockState;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {

    private int x, z;
    private ChunkCoordinates ch_coords;
    private Map<Position, BlockChange> tempBlockArr = new HashMap<>();

    public Chunk(ChunkCoordinates chunkCoordinates) {
        this.ch_coords = chunkCoordinates;
        this.x = chunkCoordinates.getChunkX();
        this.z = chunkCoordinates.getChunkZ();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public ChunkCoordinates getPosition() {
        return ch_coords;
    }

    /* I know... */
    //TODO: Chunk system
    /* everything below is... bad, really bad */
    public void setBlocks(int x, int y, int z, int id) {
        tempBlockArr.put(new Position(x, y, z), new BlockChange(new Position(x, y, z), new BlockState(id, 0)));
    }

    public List<BlockChange> getBlocks() {
        return (List) new ArrayList(tempBlockArr.values());
    }

    private List<Position> getPositions() {
        return (List) new ArrayList(tempBlockArr.keySet());
    }

    public int getHeightAt(int x, int z) {
        //that's really heavy...
        int y = 0;
        for (Position pos : getPositions()) {
            if (pos.getX() == x && pos.getZ() == z) {
                y = pos.getY();
            }
        }
        return y;
    }

    public BlockChange getBlockAt(int x, int y, int z) {
        return tempBlockArr.get(new Position(x, y, z));
    }
}
