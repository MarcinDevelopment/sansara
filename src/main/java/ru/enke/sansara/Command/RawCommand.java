package ru.enke.sansara.Command;

import ru.enke.sansara.player.Player;

public interface RawCommand {
    void execute(Player sender, String[] args);
}
