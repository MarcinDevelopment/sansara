package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.player.Player;

public class playersCommand implements RawCommand {

    private Server server;

    playersCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (Player p : server.getPlayers()) {
            sb.append(p.getName()).append(", ");
        }
        sender.sendMessage(new Message(ChatColor.GOLD + "Online players [" + server.getPlayers().size() + "]: " + ChatColor.GRAY + sb.toString()));
    }
}
