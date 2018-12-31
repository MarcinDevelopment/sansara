package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.position.ClientPlayerPositionLook;
import ru.enke.minecraft.protocol.packet.server.game.location.EntityLookAndRelativeMove;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerPositionLookHandler implements MessageHandler<ClientPlayerPositionLook> {

    private final Server server;
    private Player p;
    private double x, y, z;
    private float pitch, yaw;
    private boolean isOnGround;
    private int id;

    public PlayerPositionLookHandler(final Server server) {
        this.server = server;
    }

    //TODO: Add more things
    @Override
    public void handle(Session session, ClientPlayerPositionLook msg) {
        if (session.getPlayer() == null) {
            return;
        }
        this.p = session.getPlayer();
        this.id = p.getId();
        this.x = msg.getX();
        this.y = msg.getY();
        this.z = msg.getZ();
        this.pitch = msg.getPitch();
        this.yaw = msg.getYaw();
        this.isOnGround = msg.getGround();
        updatePlayerLocation();
    }

    private void updatePlayerLocation() {
        server.sendGlobalPacketWop(p, new EntityLookAndRelativeMove(id, x, y, z, yaw, pitch, isOnGround)); //0x27
    }
}
