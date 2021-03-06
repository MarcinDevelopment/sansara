package ru.enke.sansara.network.handler.ingame;

import ru.enke.minecraft.protocol.packet.client.game.position.PlayerPosition;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerPositionChangeHandler implements MessageHandler<PlayerPosition> {

    //private static final Logger logger = LogManager.getLogger();
    private final Server server;

    public PlayerPositionChangeHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, PlayerPosition msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        p.setLocation(new Position((int) msg.getX(), (int) msg.getY(), (int) msg.getZ()));
        p.setOnGround(msg.getGround());
        if (msg.getY() <= -20) {
            if (!p.isDead()) {
                server.broadcast(new Message(p.getName() + " fell out of the world", MessageColor.GRAY));
                p.setHealth(0.0F);
            }
        }
    }
}
