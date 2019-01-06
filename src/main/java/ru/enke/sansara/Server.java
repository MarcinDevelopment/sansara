package ru.enke.sansara;

import org.jetbrains.annotations.Nullable;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.Protocol;
import ru.enke.minecraft.protocol.packet.PacketMessage;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.sansara.Command.CommandRegistry;
import ru.enke.sansara.WorldGen.FlatWorldGenerator;
import ru.enke.sansara.network.NetworkServer;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.network.session.SessionRegistry;
import ru.enke.sansara.player.Player;
import ru.enke.sansara.player.PlayerRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Server extends PlayerRegistry implements Runnable {

    public static final String GAME_VERSION = "1.12.2";
    private static final SessionRegistry sessionRegistry = new SessionRegistry();
    private static CommandRegistry commandRegistry;
    private static NetworkServer networkServer;
    private static boolean DEBUG = true;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "Game Thread"));
    private final Map<String, World> worlds;
    private final boolean onlineMode;
    private final String favicon;

    private Server(final String favicon, final boolean onlineMode) {
        this.favicon = favicon;
        this.onlineMode = onlineMode;
        this.worlds = Collections.singletonMap("world", new World("world", 0, 0, new FlatWorldGenerator()));
    }

    public static void main(final String[] args) throws IOException {
        Configurator.currentConfig().formatPattern("[{level} {date:HH:mm:ss}] {message}").activate();
        Logger.info("Starting server | version {} ({})", GAME_VERSION, Protocol.VERSION);
        final String favicon = readServerIcon();
        final boolean onlineMode = false;
        final Server server = new Server(favicon, onlineMode);
        commandRegistry = new CommandRegistry(server);
        commandRegistry.register();
        networkServer = new NetworkServer(server, sessionRegistry);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> Logger.info("Stopping server...")));

        server.start();
    }

    private static String readServerIcon() throws IOException {
        final Path path = Paths.get("server-icon.png");

        if (!Files.exists(path)) {
            return null;
        }

        final byte[] bytes = Files.readAllBytes(path);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean debug() {
        return DEBUG;
    }

    public CommandRegistry getCClass() {
        return commandRegistry;
    }

    private void start() {
        final int port = 25565;

        if (networkServer.bind(port)) {
            Logger.info("Successfully bind server on port {}", port);
        } else {
            Logger.warn("Failed bind server on port {}", port);
            return;
        }

        executor.scheduleAtFixedRate(this, 0, 50, MILLISECONDS);
    }

    @Override
    public void run() {
        for (final Session session : sessionRegistry.getSessions()) {
            session.handleIncomingPackets();
        }

        for (final World world : getWorlds()) {
            world.run();
        }
    }

    public String getFavicon() {
        return favicon;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    @Nullable
    public World getWorldByName(final String name) {
        return worlds.get(name);
    }

    public Collection<World> getWorlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public void sendGlobalPacket(PacketMessage packet) {
        for (Player p : getPlayers()) {
            p.sendPacket(packet);
        }
    }

    public void sendGlobalPacketWop(Player except, PacketMessage packet) {
        for (Player p : getPlayers()) {
            if (p.equals(except)) {
                return;
            }
            p.sendPacket(packet);
        }
    }

    private double distanceTo(Position p, Position p2) {
        return Math.sqrt(Math.pow(p2.getX() - p.getX(), 2) + Math.pow(p2.getY() - p.getY(), 2) + Math.pow(p2.getZ() - p.getZ(), 2));
    }

    public void sendPacketToNearbyPlayers(Player you, PacketMessage packet, boolean wo) {
        double dist;
        for (Player p : getPlayers()) {
            dist = distanceTo(p.getLocation(), you.getLocation());
            if (dist <= 32.0D) {
                if (wo && p.equals(you)) {
                    return;
                }
                p.sendPacket(packet);
            }
        }
    }

    public void broadcast(Message message) {
        sendGlobalPacket(new ServerChat(message, MessageType.CHAT));
    }

    public int generateRandomEID() {
        return new Random().nextInt(999) + 1;
    }

    public void stop() {
        networkServer.stop();
    }
}
