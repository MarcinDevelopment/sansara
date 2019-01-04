package ru.enke.sansara;

import ru.enke.minecraft.protocol.packet.data.game.GameState;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.server.game.ChangeGameState;
import ru.enke.minecraft.protocol.packet.server.game.TimeUpdate;
import ru.enke.sansara.Entity.Entity;
import ru.enke.sansara.Entity.EntityType;
import ru.enke.sansara.player.Player;
import ru.enke.sansara.player.PlayerRegistry;

import java.util.HashMap;

public class World extends PlayerRegistry implements Runnable {

    private static final int TIME_UPDATE_INTERVAL = 20;

    private final Position spawnPosition = new Position(8, 125, 8);
    private final String name;
    private long age;
    private long time;
    private HashMap<Integer, Entity> worldEntities = new HashMap<>();
    //private Map<ChunkPosition, Chunk> worldChunks = new HashMap<>();
    private boolean storm;


    World(final String name, final long time, final long age) {
        this.name = name;
        this.age = age;
        this.time = time;
    }

    /*public Collection<Chunk> getAllChunks() {
        return this.worldChunks.values();
    }*/

    @Override
    public void run() {
        age++;
        time++;

        if (age % TIME_UPDATE_INTERVAL == 0) {
            for (final Player player : getPlayers()) {
                player.sendPacket(new TimeUpdate(age, time));
            }
        }
    }

    @Override
    public void addPlayer(final Player player) {
        players.put(player.getProfile().getName(), player);
        if (isStorm()) {
            player.sendPacket(new ChangeGameState(GameState.BEGIN_RAINING, 0));
        }
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

    public Position getSpawnPosition() {
        return spawnPosition;
    }

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
}
