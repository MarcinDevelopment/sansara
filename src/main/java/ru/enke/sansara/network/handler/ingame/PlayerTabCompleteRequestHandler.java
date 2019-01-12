package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.TabCompleteRequest;
import ru.enke.minecraft.protocol.packet.server.game.TabCompleteResponse;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerTabCompleteRequestHandler implements MessageHandler<TabCompleteRequest> {

    private final Server server;
    private List<String> commands = new ArrayList<>();

    public PlayerTabCompleteRequestHandler(final Server server) {
        this.server = server;
        for (String cmd : server.getCClass().getCommands().keySet()) {
            commands.add("/" + cmd); //a bit dirty
        }
    }

    private List<String> playerList() {
        /* ----------------------------------------------------------------- */
        /* It may cause performance issues later, so... */
        List<String> strList = new ArrayList<>();

        for (Player p : server.getPlayers()) {
            strList.add(p.getName());
        }
        /* ----------------------------------------------------------------- */
        Collections.sort(strList);
        return strList;
    }

    @Override
    public void handle(Session session, TabCompleteRequest msg) {
        if (session.getPlayer() == null) {
            return;
        }

        Player p = session.getPlayer();
        String current = msg.getText();
        String[] args = msg.getText().trim().split("\\s+");

        if (current.startsWith("/")) {
            p.sendPacket(new TabCompleteResponse(find(commands, current)));
        } else {
            p.sendPacket(new TabCompleteResponse(find(playerList(), args[args.length - 1])));
        }
    }

    private List<String> find(List<String> possiblecompletions, String current) {
        List<String> strList = new ArrayList<>();

        for (String word : possiblecompletions) {
            if (word.startsWith(current)) {
                strList.add(current + word.substring(current.length()));
            }
        }
        Collections.sort(strList);
        return strList;
    }
}
