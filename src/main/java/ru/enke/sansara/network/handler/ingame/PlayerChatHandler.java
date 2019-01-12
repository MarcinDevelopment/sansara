package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.ClientChat;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.sansara.Command.CommandRegistry;
import ru.enke.sansara.Command.RawCommand;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerChatHandler implements MessageHandler<ClientChat> {

    private final Server server;
    private String message;
    private CommandRegistry commandRegistry;
    private Player p;

    public PlayerChatHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, ClientChat msg) {
        if (session.getPlayer() == null) {
            return;
        }
        commandRegistry = server.getCClass();
        p = session.getPlayer();
        message = msg.getText();
        if (message.length() > 100) {
            message = message.substring(0, 100); //vanilla
        } else if (message.startsWith("/")) {
            executeCommand();
            return;
        }

        server.sendGlobalPacket(new ServerChat(new Message(ChatColor.GRAY + p.getName() + ": " + ChatColor.WHITE + message), MessageType.CHAT));
    }

    private void executeCommand() {
        message = message.substring(1);
        String[] cmd = message.trim().split("\\s+");
        RawCommand command;
        if (commandRegistry.getCommands().containsKey(cmd[0])) {
            command = commandRegistry.getCommands().get(cmd[0]);
            command.execute(p, cmd);
        } else {
            p.sendMessage(new Message("Unknown command", MessageColor.RED));
        }
    }
}
