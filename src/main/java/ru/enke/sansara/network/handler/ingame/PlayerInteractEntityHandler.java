package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.InteractEntity;
import ru.enke.minecraft.protocol.packet.data.game.AnimationType;
import ru.enke.minecraft.protocol.packet.server.game.Animation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerInteractEntityHandler implements MessageHandler<InteractEntity> {

    private final Server server;

    public PlayerInteractEntityHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, InteractEntity msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        switch (msg.getType()) {
            case ATTACK:
                Logger.info("entity hurt: {}", p.getWorld().getEntityBySpawnId(msg.getEntityId()).toString());
                server.sendPacketToNearbyPlayers(p, new Animation(msg.getEntityId(), AnimationType.DAMAGE), false);
                break;
            case INTERACT:
                //TODO: idk. Too much to do to think about it
                break;
            case INTERACT_AT:
                //TODO: same as above
                break;
        }
    }
}
