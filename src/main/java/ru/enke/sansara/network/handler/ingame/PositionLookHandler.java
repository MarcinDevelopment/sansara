package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.position.PlayerLook;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class PositionLookHandler implements MessageHandler<PlayerLook> {

    private final Server server;

    public PositionLookHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, PlayerLook msg) {
        //logger.info(msg.toString());
        //TODO: Finish it
    }
}
