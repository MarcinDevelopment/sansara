package ru.enke.sansara.network.handler.login;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class HandshakeHandler implements MessageHandler<Handshake> {

    @Override
    public void handle(Session session, Handshake msg) {
        Logger.debug(msg.toString());
        //TODO: Ym...
    }
}
