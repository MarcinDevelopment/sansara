package ru.enke.sansara;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.data.game.GameState;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.server.game.ChangeGameState;
import ru.enke.minecraft.protocol.packet.server.game.TimeUpdate;
import ru.enke.minecraft.protocol.packet.server.game.block.MultiBlockChange;
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkData;
import ru.enke.sansara.Entity.Entity;
import ru.enke.sansara.Entity.EntityType;
import ru.enke.sansara.Utils.Dimension;
import ru.enke.sansara.WorldGen.Chunk.Chunk;
import ru.enke.sansara.WorldGen.Chunk.ChunkCoordinates;
import ru.enke.sansara.WorldGen.ObjectPopulator;
import ru.enke.sansara.WorldGen.WorldGenerator;
import ru.enke.sansara.player.Player;
import ru.enke.sansara.player.PlayerRegistry;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class World extends PlayerRegistry implements Runnable {

    private static final int TIME_UPDATE_INTERVAL = 20;
    private Position spawnPosition;
    private final String name;
    private final int SPAWN_SIZE = 1;
    private long age;
    private long time;
    private ConcurrentHashMap<Integer, Entity> worldEntities = new ConcurrentHashMap<>();
    private ConcurrentHashMap<ChunkCoordinates, Chunk> worldChunks = new ConcurrentHashMap<>();
    private List<ObjectPopulator> populators;
    private boolean storm;
    private WorldGenerator generator;
    private int dimension;
    private long seed;

    World(final String name, final long time, final long age, WorldGenerator worldGenerator, Dimension dimension, List<ObjectPopulator> populators, long seed) {
        this.name = name;
        this.age = age;
        this.time = time;
        this.dimension = dimension.getId();
        this.generator = worldGenerator;
        this.populators = populators;
        this.seed = seed;
        generateLevel();
    }

    public WorldGenerator getWorldGenerator() {
        return this.generator;
    }

    private Collection<Chunk> getAllChunks() {
        return this.worldChunks.values();
    }

    public Chunk getChunkAt(int x, int z) {
        return worldChunks.get(new ChunkCoordinates(x, z));
    }

    private void generateChunk(ChunkCoordinates coordinates) {
        this.worldChunks.put(coordinates, this.generator.generate(coordinates.getChunkX(), coordinates.getChunkZ(), seed));
        for (ObjectPopulator populator : this.populators) {
            populator.populate(this.worldChunks.get(coordinates), seed);
        }
    }

    private void generateLevel() {
        Logger.info("Preparing world of size '" + (SPAWN_SIZE * SPAWN_SIZE) + "' ...");
        Logger.info("Seed: " + getSeed()); //-2479449874556904399 <3
        setSpawnPosition(new Position(0, 120, 0));
        long start = System.nanoTime();
        for (int x = 0; x < SPAWN_SIZE; x++) {
            for (int z = 0; z < SPAWN_SIZE; z++) {
                generateChunk(new ChunkCoordinates(x, z));
            }
        }
        long end = System.nanoTime() - start;
        Logger.info("Generated '" + worldChunks.size() + "' chunks in " + String.format("%.3fs", end / 1.0E9D) + "!");
    }

    @Override
    public void run() {
        age++;
        time++;

        if (age % TIME_UPDATE_INTERVAL == 0) {
            for (final Player player : getPlayers()) {
                player.sendPacket(new TimeUpdate(age, time));
                player.tick();
            }
        }
    }

    @Override
    public void addPlayer(final Player player) {
        players.put(player.getName(), player);
        if (isStorm()) {
            player.sendPacket(new ChangeGameState(GameState.BEGIN_RAINING, 0));
        }

        //TEST
        for (Chunk chunk : getAllChunks()) {

            player.sendPacket(new ChunkData(chunk.getX(), chunk.getZ(), true, 0, new byte[256]));
            //TODO: update 'minecraft-protocol' lib to fix 'modern' chunk sending
            player.sendPacket(new MultiBlockChange(chunk.getX(), chunk.getZ(), chunk.getBlocks()));
            //player.sendPacket(new BlockChange(new Position(spawnPosition.getX(), spawnPosition.getY() - 1, spawnPosition.getZ()), new BlockState(2, 0)));
        }

    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getAge() {
        return age;
    }

    /*public Position getSpawnPosition() {
        return spawnPosition;
    }*/

    public void addEntity(int entitySpawnId, EntityType entity, Position entityspawnLocation) {
        worldEntities.put(entitySpawnId, new Entity(entity, entityspawnLocation));
    }

    public Entity getEntityBySpawnId(int spawnid) {
        return worldEntities.get(spawnid);
    }

    public boolean isStorm() {
        return storm;
    }

    public void setStorm(boolean storm) {
        this.storm = storm;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setSpawnPosition(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public long getSeed() {
        return seed;
    }

    public void strikeLightingAt(Position pos) {

    }

    public Position getHighestBlockAt(int x, int z) {
        /*TODO:
         * get chunk at x z (chunk x/z * 16 + x/z)
         * get highest block using seed from world gen.
         */
        return null;
    }

}
