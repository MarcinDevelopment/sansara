package ru.enke.sansara.Command;

import org.pmw.tinylog.Logger;
import ru.enke.sansara.Server;

import java.util.HashMap;

public class CommandRegistry {

    private HashMap<String, RawCommand> commands = new HashMap<>();
    private Server server;

    public CommandRegistry(Server server) {
        this.server = server;
    }

    public void register() {
        commands.put("players", new playersCommand(server, null));
        commands.put("stop", new stopCommand(server, null));
        commands.put("memory", new memoryCommand(server, null));
        commands.put("kick", new kickCommand(server, null));
        commands.put("time", new timeCommand(server, null));
        commands.put("gamemode", new gamemodeCommand(server, null));
        Logger.info("Loaded " + commands.size() + " commands");
    }

    public HashMap<String, RawCommand> getCommands() {
        return commands;
    }
}
