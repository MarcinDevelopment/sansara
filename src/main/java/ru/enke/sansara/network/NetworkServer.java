package ru.enke.sansara.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.enke.minecraft.protocol.codec.LengthCodec;
import ru.enke.minecraft.protocol.codec.PacketCodec;
import ru.enke.minecraft.protocol.packet.client.game.*;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDigging;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlace;
import ru.enke.minecraft.protocol.packet.client.game.inventory.InventoryClick;
import ru.enke.minecraft.protocol.packet.client.game.position.ClientPlayerPositionLook;
import ru.enke.minecraft.protocol.packet.client.game.position.PlayerGround;
import ru.enke.minecraft.protocol.packet.client.game.position.PlayerLook;
import ru.enke.minecraft.protocol.packet.client.game.position.PlayerPosition;
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake;
import ru.enke.minecraft.protocol.packet.client.login.LoginStart;
import ru.enke.minecraft.protocol.packet.client.status.PingRequest;
import ru.enke.minecraft.protocol.packet.client.status.StatusRequest;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandlerRegistry;
import ru.enke.sansara.network.handler.ingame.*;
import ru.enke.sansara.network.handler.ingame.inventory.PlayerInventoryClick;
import ru.enke.sansara.network.handler.ingame.inventory.PlayerItemHeldChangeHandler;
import ru.enke.sansara.network.handler.login.HandshakeHandler;
import ru.enke.sansara.network.handler.login.LoginStartHandler;
import ru.enke.sansara.network.handler.status.PingRequestHandler;
import ru.enke.sansara.network.handler.status.StatusRequestHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.network.session.SessionRegistry;

import static ru.enke.minecraft.protocol.ProtocolSide.SERVER;

public class NetworkServer {

    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
    private final MessageHandlerRegistry messageHandlerRegistry = new MessageHandlerRegistry();
    private final SessionRegistry sessionRegistry;
    private final Server server;

    public NetworkServer(final Server server, final SessionRegistry sessionRegistry) {
        this.server = server;
        this.sessionRegistry = sessionRegistry;

        messageHandlerRegistry.registerHandler(StatusRequest.class, new StatusRequestHandler(server));
        messageHandlerRegistry.registerHandler(PingRequest.class, new PingRequestHandler());
        messageHandlerRegistry.registerHandler(LoginStart.class, new LoginStartHandler(server));
        messageHandlerRegistry.registerHandler(ClientChat.class, new PlayerChatHandler(server));
        messageHandlerRegistry.registerHandler(BlockDigging.class, new PlayerBlockDiggingHandler(server));
        messageHandlerRegistry.registerHandler(InteractEntity.class, new PlayerInteractEntityHandler(server));
        messageHandlerRegistry.registerHandler(PlayerPosition.class, new PlayerPositionChangeHandler(server));
        messageHandlerRegistry.registerHandler(PlayerLook.class, new PositionLookHandler(server));
        messageHandlerRegistry.registerHandler(Handshake.class, new HandshakeHandler());
        messageHandlerRegistry.registerHandler(ClientStatus.class, new ClientStatusHandler());
        messageHandlerRegistry.registerHandler(ClientSettings.class, new ClientSettingsHandler());
        messageHandlerRegistry.registerHandler(ClientPlayerPositionLook.class, new PlayerPositionLookHandler(server));
        messageHandlerRegistry.registerHandler(SwingArm.class, new ClientSwingArmHandler(server));
        messageHandlerRegistry.registerHandler(UseItem.class, new PlayerUseItemHandler());
        messageHandlerRegistry.registerHandler(PlayerAction.class, new PlayerActionHandler(server));
        messageHandlerRegistry.registerHandler(BlockPlace.class, new PlayerBlockPlaceHandler(server));
        messageHandlerRegistry.registerHandler(PlayerLook.class, new PlayerLookHandler(server));
        messageHandlerRegistry.registerHandler(TabCompleteRequest.class, new PlayerTabCompleteRequestHandler(server));
        messageHandlerRegistry.registerHandler(ClientItemHeldChange.class, new PlayerItemHeldChangeHandler(server));
        messageHandlerRegistry.registerHandler(PlayerGround.class, new PlayerGroundHandler(server));
        messageHandlerRegistry.registerHandler(InventoryClick.class, new PlayerInventoryClick());
    }

    public boolean bind(final int port) {
        return new ServerBootstrap()
                .group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(final Channel channel) {
                        final ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast(Session.LENGTH_CODEC_NAME, new LengthCodec());
                        pipeline.addLast(Session.PACKET_CODEC_NAME, new PacketCodec(SERVER, false, false, null));
                        pipeline.addLast(Session.SESSION_HANDLER_NAME, new Session(channel, server, sessionRegistry, messageHandlerRegistry));
                    }
                })
                .bind(port)
                .awaitUninterruptibly().isSuccess();
    }

    public void stop() {
        eventLoopGroup.shutdownGracefully();
    }

}
