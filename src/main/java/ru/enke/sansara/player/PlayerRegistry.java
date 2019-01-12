package ru.enke.sansara.player;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerRegistry {

    protected final Map<String, Player> players = new HashMap<>();

    @Nullable
    public Player getPlayerByName(final String name) {
        return players.get(name);
    }

    public Collection<Player> getPlayers() {
        return Collections.unmodifiableCollection(players.values());
    }

    public void addPlayer(final Player player) {
        players.put(player.getName(), player);
    }

    public void removePlayer(final Player player) {
        players.remove(player.getName(), player);
    }

}
