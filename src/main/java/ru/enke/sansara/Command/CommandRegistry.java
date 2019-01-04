package ru.enke.sansara.Command;

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
    }

    public HashMap<String, RawCommand> getCommands() {
        return commands;
    }
}
