package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.InteractEntity;
import ru.enke.minecraft.protocol.packet.data.game.AnimationType;
import ru.enke.minecraft.protocol.packet.server.game.Animation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerInteractEntityHandler implements MessageHandler<InteractEntity> {

    private static final Logger logger = LogManager.getLogger();
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
                server.sendPacketToNearbyPlayers(p, new Animation(msg.getEntityId(), AnimationType.DAMAGE), false);
                logger.debug("entity hurt: " + p.getWorld().getEntityBySpawnId(msg.getEntityId()).toString());
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
