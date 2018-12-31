package ru.enke.sansara.network.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import ru.enke.minecraft.protocol.codec.CompressionCodec;
import ru.enke.minecraft.protocol.packet.PacketMessage;
import ru.enke.minecraft.protocol.packet.data.game.*;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.server.game.JoinGame;
import ru.enke.minecraft.protocol.packet.server.game.PlayerListData;
import ru.enke.minecraft.protocol.packet.server.game.SpawnPosition;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkData;
import ru.enke.minecraft.protocol.packet.server.game.entity.SpawnMob;
import ru.enke.minecraft.protocol.packet.server.game.player.ServerPlayerAbilities;
import ru.enke.minecraft.protocol.packet.server.game.player.ServerPlayerPositionLook;
import ru.enke.minecraft.protocol.packet.server.login.LoginSetCompression;
import ru.enke.minecraft.protocol.packet.server.login.LoginSuccess;
import ru.enke.sansara.Entity.EntityType;
import ru.enke.sansara.Server;
import ru.enke.sansara.World;
import ru.enke.sansara.login.LoginProfile;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.handler.MessageHandlerRegistry;
import ru.enke.sansara.player.Player;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class Session extends SimpleChannelInboundHandler<PacketMessage> {

    public static final String LENGTH_CODEC_NAME = "length";
    public static final String PACKET_CODEC_NAME = "packet";
    public static final String SESSION_HANDLER_NAME = "session";
    private static final String COMPRESSION_CODEC_NAME = "compression";
    private static final Logger logger = LogManager.getLogger();

    private final Queue<PacketMessage> messageQueue = new LinkedBlockingQueue<>();
    private final MessageHandlerRegistry messageHandlerRegistry;
    private final SessionRegistry sessionRegistry;
    private final Channel channel;
    private final Server server;
    private Player player;

    public Session(final Channel channel, final Server server, final SessionRegistry sessionRegistry,
                   final MessageHandlerRegistry messageHandlerRegistry) {
        this.channel = channel;
        this.server = server;
        this.sessionRegistry = sessionRegistry;
        this.messageHandlerRegistry = messageHandlerRegistry;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        logger.debug("New network connection from ip {}", getAddress());
        sessionRegistry.addSession(this);
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        logger.debug("Disconnected {}", getAddress());
        sessionRegistry.removeSession(this);

        if (player != null) {
            final World world = player.getWorld();
            world.removePlayer(player);
            server.removePlayer(player);
        }
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final PacketMessage msg) {
        if (logger.isTraceEnabled()) {
            logger.trace("Received packet {}", msg);
        }

        messageQueue.add(msg);
    }

    @SuppressWarnings("unchecked")
    public void handleIncomingPackets() {
        PacketMessage msg;

        while ((msg = messageQueue.poll()) != null) {
            final MessageHandler handler = messageHandlerRegistry.getMessageHandler(msg);

            if (handler != null) {
                handler.handle(this, msg);
            } else {
                logger.warn("Message {} missing handler", msg);
            }
        }
    }

    public void joinGame(final LoginProfile profile) {
        // Finalize login.
        setCompression(CompressionCodec.DEFAULT_COMPRESSION_THRESHOLD);
        sendPacket(new LoginSuccess(profile.getId().toString(), profile.getName()));

        final World world = server.getWorlds().iterator().next();

        player = new Player(server.generateRandomEID(), this, world, profile);
        server.addPlayer(player);
        world.addPlayer(player);

        player.setGameMode(GameMode.SURVIVAL);

        sendPacket(new JoinGame(player.getId(), player.getGameMode(), 0, Difficulty.NORMAL, 100, WorldType.DEFAULT, true));
        sendPacket(new ServerPlayerAbilities(false, false, true, true, 0.05F, 0.1F));
        /* just a test
           TODO: Chunk system
        */
        for (int x = 0; x <= 16; x++) {
            for (int z = 0; z <= 16; z++) {
                for (int y = 116; y <= 124; y++) {
                    if (!(y >= 124)) {
                        sendPacket(new BlockChange(new Position(x, y, z), new BlockState(1, 0)));
                    } else {
                        sendPacket(new BlockChange(new Position(x, y, z), new BlockState(2, 0)));
                    }
                    sendPacket(new ChunkData(x, z, true, 0, new byte[256]));
                }
            }
        }
        sendPacket(new SpawnPosition(world.getSpawnPosition()));
        sendPacket(new ServerPlayerPositionLook(world.getSpawnPosition().getX(), world.getSpawnPosition().getY(), world.getSpawnPosition().getZ(), 0, 0, 0, 1));
        logger.info("Player {} joined game [entityid={}]", profile.getName(), player.getId());

        // Testing
        //server.sendGlobalPacket(new SpawnExpOrb(0, 8, 125, 8, 8));
        //server.sendGlobalPacket(new SpawnGlobalEntity(1, 1, 125, 8, 1)); //lighting strike
        server.sendGlobalPacket(new SpawnMob(99, UUID.randomUUID(),
                EntityType.ZOMBIE.getId(), world.getSpawnPosition().getX(), world.getSpawnPosition().getY(), world.getSpawnPosition().getZ(),
                0, 0, 0, 0, 0, 0, Collections.emptyList()));

        world.addEntity(99, EntityType.ZOMBIE, world.getSpawnPosition());
        //Another test
        player.sendPacket(new PlayerListData(new Message("Hello, " + profile.getName(), MessageColor.GOLD), new Message("0x4A packet test")));
        //TODO: create 0x2E packet
    }

    private void setCompression(final int threshold) {
        sendPacket(new LoginSetCompression(threshold));
        channel.pipeline().addBefore(PACKET_CODEC_NAME, COMPRESSION_CODEC_NAME, new CompressionCodec(threshold));
        logger.trace("Enable compression with {} threshold", threshold);
    }

    public void sendPacket(final PacketMessage msg) {
        if (logger.isTraceEnabled()) {
            logger.trace("Sending packet {}", msg);
        }

        channel.writeAndFlush(msg);
    }

    public SocketAddress getAddress() {
        return channel.remoteAddress();
    }

    @Nullable
    public Player getPlayer() {
        return player;
    }

}