package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.player.Player;

import static ru.enke.sansara.Utils.Constants.NO_PERMISSIONS;

public class memoryCommand implements RawCommand {

    private Server server;

    memoryCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (!sender.isOperator()) {
            sender.sendMessage(new Message(NO_PERMISSIONS, MessageColor.RED));
            return;
        }

        int megabyte = 1024 * 1024;

        Runtime runtime = Runtime.getRuntime();
        String sb = ChatColor.AQUA + "Used Memory: " + ChatColor.GRAY + (runtime.totalMemory() - runtime.freeMemory()) / megabyte + " MB\n" +
                ChatColor.GREEN + "Free Memory: " + ChatColor.GRAY + runtime.freeMemory() / megabyte + " MB\n" +
                ChatColor.RED + "Total Memory: " + ChatColor.GRAY + runtime.totalMemory() / megabyte + " MB";
        sender.sendMessage(new Message(sb));
    }
}
