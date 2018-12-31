package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.InteractEntity;
import ru.enke.minecraft.protocol.packet.data.game.AnimationType;
import ru.enke.minecraft.protocol.packet.server.game.Animation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class PlayerInteractEntityHandler implements MessageHandler<InteractEntity> {

    private static final Logger logger = LogManager.getLogger();
    private final Server server;

    public PlayerInteractEntityHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, InteractEntity msg) {
        switch (msg.getType()) {
            case ATTACK:
                server.sendGlobalPacket(new Animation(msg.getEntityId(), AnimationType.DAMAGE));
                if (session.getPlayer() != null) {
                    logger.debug("entity hurt: " + session.getPlayer().getWorld().getEntityBySpawnId(msg.getEntityId()).toString());
                }
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
