package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.World;
import ru.enke.sansara.player.Player;

import static ru.enke.sansara.Utils.Constants.NO_PERMISSIONS;

public class timeCommand implements RawCommand {

    private Server server;

    timeCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (!sender.isOperator()) {
            sender.sendMessage(new Message(NO_PERMISSIONS, MessageColor.RED));
            return;
        } else if (args.length <= 1) {
            sender.sendMessage(new Message("/time <value or day|night|noon|sunset>", MessageColor.GRAY));
            return;
        }

        World world = sender.getWorld();
        String arg = args[1].toLowerCase();

        int tm;
        switch (arg) {
            case "day":
                tm = 1000;
                break;
            case "night":
                tm = 13000;
                break;
            case "noon":
                tm = 6000;
                break;
            case "sunset":
                tm = 84000;
                break;
            default:
                try {
                    tm = Integer.valueOf(arg);
                } catch (NumberFormatException e) {
                    sender.sendMessage(new Message(ChatColor.RED + arg + " is not a valid number"));
                    return;
                }
        }
        sender.sendMessage(new Message("Set the time to " + tm));
        world.setTime(tm);
    }
}
