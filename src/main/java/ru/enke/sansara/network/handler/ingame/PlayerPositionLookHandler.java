package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.position.ClientPlayerPositionLook;
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
    private static final Logger logger = LogManager.getLogger();

    public PlayerPositionLookHandler(final Server server) {
        this.server = server;
    }

    //TODO: Add more things
    @Override
    public void handle(Session session, ClientPlayerPositionLook msg) {
        if (session.getPlayer() == null) {
            return;
        }
        //TODO: fix xyz values, cuz it moves entity in every possible direction.
        this.p = session.getPlayer();
        this.id = p.getId();
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.pitch = msg.getPitch();
        this.yaw = msg.getYaw();
        this.isOnGround = msg.getGround();
        logger.info("x: " + x + " y: " + y + " z: " + z);
        updatePlayerLocation();
    }

    private void updatePlayerLocation() {
        //server.sendGlobalPacketWop(p, new EntityLookAndRelativeMove(id, x, y, z, yaw, pitch, isOnGround)); //0x27
    }
}
