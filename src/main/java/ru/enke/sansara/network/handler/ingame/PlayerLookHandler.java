package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.position.PlayerLook;
import ru.enke.minecraft.protocol.packet.server.game.location.EntityLook;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerLookHandler implements MessageHandler<PlayerLook> {

    private final Server server;

    public PlayerLookHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, PlayerLook msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        Logger.info(msg);
        //there's nothing to do here, I think. It works !
        server.sendPacketToNearbyPlayers(p, new EntityLook(p.getId(), msg.getYaw(), msg.getPitch(), msg.getGround()), true); //0x28
    }
}
