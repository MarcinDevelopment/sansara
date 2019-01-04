package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.player.Player;

import static ru.enke.sansara.Utils.Constants.NO_ONLINE;
import static ru.enke.sansara.Utils.Constants.NO_PERMISSIONS;

public class kickCommand implements RawCommand {

    private Server server;

    kickCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (!sender.isOperator()) {
            sender.sendMessage(new Message(NO_PERMISSIONS, MessageColor.RED));
            return;
        } else if (args.length <= 2) {
            sender.sendMessage(new Message("/kick <playername> <reason>", MessageColor.GRAY));
            return;
        }
        Player target = server.getPlayerByName(args[1]);
        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        if (target == null) {
            sender.sendMessage(new Message(NO_ONLINE, MessageColor.RED));
            return;
        }
        target.kick(reason.toString());
        sender.sendMessage(new Message("Kicked " + args[1] + " for reason '" + reason + "'"));
    }
}
