package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDigging;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockBreakAnimation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerBlockDiggingHandler implements MessageHandler<BlockDigging> {

    private static final Logger logger = LogManager.getLogger();
    private final Server server;

    public PlayerBlockDiggingHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, BlockDigging msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        logger.info(msg);

        /* TODO send more packets */
        switch (msg.getAction()) {
            case DROP_ITEM:
                break;
            case START_DIGGING:
                server.sendGlobalPacket(new BlockBreakAnimation(p.getId(), msg.getPosition(), 5 /* TODO use correct values  */));
                break;
            case CANCEL_DIGGING:
                server.sendGlobalPacket(new BlockBreakAnimation(p.getId(), msg.getPosition(), -1));
                break;
            case FINISH_DIGGING:
                server.sendGlobalPacket(new BlockBreakAnimation(p.getId(), msg.getPosition(), -1));
                break;
            case SWAP_HANDS:
                break;
            case DROP_ITEM_STACK:
                break;
            case RELEASE_USE_ITEM:
                break;
        }
    }
}
