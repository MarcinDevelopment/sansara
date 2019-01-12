package ru.enke.sansara.network.handler.login;

import ru.enke.minecraft.protocol.Protocol;
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.server.login.LoginDisconnect;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class HandshakeHandler implements MessageHandler<Handshake> {

    private Session session;
    private Handshake handshake;

    @Override
    public void handle(Session session, Handshake msg) {
        this.session = session;
        this.handshake = msg;

        switch (msg.getState()) {
            case LOGIN:
                disconnect();
                break;
        }
    }

    private void disconnect() {
        if (handshake.getProtocol() > Protocol.VERSION) {
            session.sendPacket(new LoginDisconnect(new Message("Outdated server! (" + Protocol.VERSION_NAME + ")", MessageColor.RED)));
        } else if (handshake.getProtocol() < Protocol.VERSION) {
            session.sendPacket(new LoginDisconnect(new Message("Outdated client! Please use " + Protocol.VERSION_NAME, MessageColor.RED)));
        }
    }
}