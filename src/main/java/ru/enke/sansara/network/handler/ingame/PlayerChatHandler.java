package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.ClientChat;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerChatHandler implements MessageHandler<ClientChat> {

    private final Server server;

    public PlayerChatHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, ClientChat msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        String message = msg.getText();
        if (message.length() > 100) {
            message = message.substring(0, 100); //vanilla
        }

        if (message.startsWith("/")) {
            p.sendMessage(new Message("There are no commands defined yet", MessageColor.RED));
            return; //TODO: commands
        }

        server.sendGlobalPacket(new ServerChat(new Message(p.getProfile().getName() + ": " + message, MessageColor.GRAY), MessageType.CHAT));
    }
}
