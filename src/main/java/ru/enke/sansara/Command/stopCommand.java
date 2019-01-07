package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.player.Player;

import static ru.enke.sansara.Utils.Constants.NO_PERMISSIONS;

public class stopCommand implements RawCommand {

    private Server server;

    stopCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (!sender.isOperator()) {
            sender.sendMessage(new Message(NO_PERMISSIONS, MessageColor.RED));
            return;
        } else if (args.length <= 1) {
            sender.sendMessage(new Message("/stop <reason>", MessageColor.GRAY));
            return;
        }
        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        sender.sendMessage(new Message("Stopping the server..", MessageColor.RED));
        for (Player p : server.getPlayers()) {
            p.kick(reason.toString());
        }
        server.stop(true);
    }
}
