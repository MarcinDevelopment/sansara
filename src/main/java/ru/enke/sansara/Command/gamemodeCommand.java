package ru.enke.sansara.Command;

import ru.enke.minecraft.protocol.packet.data.game.GameMode;
import ru.enke.minecraft.protocol.packet.data.game.GameState;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.server.game.ChangeGameState;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.player.Player;

import static ru.enke.sansara.Utils.Constants.NO_PERMISSIONS;

public class gamemodeCommand implements RawCommand {

    private Server server;

    gamemodeCommand(Server server, String desc) {
        this.server = server;
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (!sender.isOperator()) {
            sender.sendMessage(new Message(NO_PERMISSIONS, MessageColor.RED));
            return;
        } else if (args.length <= 1) {
            sender.sendMessage(new Message("/gamemode <c | a | s | *>", MessageColor.GRAY));
            return;
        }

        String modeArg = args[1];
        GameMode mode;
        int t_mode;

        if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
            mode = GameMode.CREATIVE;
            t_mode = 1;
        } else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
            mode = GameMode.ADVENTURE;
            t_mode = 2;
        } else if (modeArg.equalsIgnoreCase("spectator") || modeArg.equalsIgnoreCase("s")) {
            mode = GameMode.SPECTATOR;
            t_mode = 3;
        } else {
            mode = GameMode.SURVIVAL;
            t_mode = 0;
        }
        sender.setGameMode(mode);
        sender.sendPacket(new ChangeGameState(GameState.CHANGE_GAMEMODE, t_mode));
        sender.sendMessage(new Message(ChatColor.GOLD + "Your gamemode has changed to " + ChatColor.WHITE + mode.toString()));
    }
}
