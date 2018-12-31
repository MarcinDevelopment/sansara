package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDigging;
import ru.enke.minecraft.protocol.packet.data.game.BlockState;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockBreakAnimation;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerBlockDiggingHandler implements MessageHandler<BlockDigging> {

    private static final Logger logger = LogManager.getLogger();
    private final Server server;
    private int eId;

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
        int x = msg.getPosition().getX();
        int y = msg.getPosition().getY();
        int z = msg.getPosition().getZ();

        /* TODO send more packets */
        switch (msg.getAction()) {
            case DROP_ITEM:
                //TODO: drop item
                break;
            case START_DIGGING:
                this.eId = server.generateRandomEID();
                server.sendPacketToNearbyPlayers(p, new BlockBreakAnimation(eId, msg.getPosition(), 5 /* TODO use correct values  */), false);
                break;
            case CANCEL_DIGGING:
                server.sendPacketToNearbyPlayers(p, new BlockBreakAnimation(eId, msg.getPosition(), -1), false);
                break;
            case FINISH_DIGGING:
                server.sendPacketToNearbyPlayers(p, new BlockBreakAnimation(eId, msg.getPosition(), -1), false);
                server.sendPacketToNearbyPlayers(p, new BlockChange(new Position(x, y, z), new BlockState(0 /* air */, 0)), false);
                //TODO: drop item
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
