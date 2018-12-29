package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.ClientChat;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class PlayerChatHandler implements MessageHandler<ClientChat> {

    private final Server server;

    public PlayerChatHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, ClientChat msg) {
        if (session.getPlayer() != null) {
            server.sendGlobalPacket(new ServerChat(new Message(session.getPlayer().getProfile().getName() + ": " + msg.getText(), MessageColor.GRAY), MessageType.CHAT));
        }
    }
}
