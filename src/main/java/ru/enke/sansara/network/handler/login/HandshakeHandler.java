package ru.enke.sansara.network.handler.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class HandshakeHandler implements MessageHandler<Handshake> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void handle(Session session, Handshake msg) {
        logger.debug(msg.toString());
        //TODO: Ym...
    }
}
