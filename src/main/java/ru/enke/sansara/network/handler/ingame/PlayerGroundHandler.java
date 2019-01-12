package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.position.PlayerGround;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerGroundHandler implements MessageHandler<PlayerGround> {

    private final Server server;

    public PlayerGroundHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, PlayerGround msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        Logger.info(p.getName() + " | " + msg);
        server.broadcast(new Message(ChatColor.RED + "ON GROUND : " + msg.getGround()));

        //TODO: Fall damage
    }
}