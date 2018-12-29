package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDigging;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockBreakAnimation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class PlayerBlockDiggingHandler implements MessageHandler<BlockDigging> {

    private static final Logger logger = LogManager.getLogger();
    private final Server server;

    public PlayerBlockDiggingHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, BlockDigging msg) {
        if (session.getPlayer() != null) {
            logger.info(session.getPlayer().getProfile().getName() + ": " + msg.toString());
            server.sendGlobalPacket(new BlockBreakAnimation(session.getPlayer().getId(), msg.getPosition(), 1 /* TODO use correct values  */));
        }
    }
}
