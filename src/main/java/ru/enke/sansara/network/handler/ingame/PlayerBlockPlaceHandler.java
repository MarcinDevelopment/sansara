package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlace;
import ru.enke.minecraft.protocol.packet.data.game.BlockState;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class PlayerBlockPlaceHandler implements MessageHandler<BlockPlace> {

    private static final Logger logger = LogManager.getLogger();

    private final Server server;

    public PlayerBlockPlaceHandler(final Server server) {
        this.server = server;
    }

    //TODO: inventory
    @Override
    public void handle(Session session, BlockPlace msg) {
        logger.info(msg);
        int x = msg.getPosition().getX();
        int y = msg.getPosition().getY();
        int z = msg.getPosition().getZ();
        server.sendGlobalPacket(new BlockChange(new Position(x, y, z), new BlockState(1 /* stone */, 0)));
    }
}
