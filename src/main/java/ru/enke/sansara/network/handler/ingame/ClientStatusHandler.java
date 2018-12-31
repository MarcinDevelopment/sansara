package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.ClientStatus;
import ru.enke.minecraft.protocol.packet.data.game.Difficulty;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.game.WorldType;
import ru.enke.minecraft.protocol.packet.server.game.Respawn;
import ru.enke.minecraft.protocol.packet.server.game.SpawnPosition;
import ru.enke.minecraft.protocol.packet.server.game.player.ServerPlayerPositionLook;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class ClientStatusHandler implements MessageHandler<ClientStatus> {

    private static final Logger logger = LogManager.getLogger();
    //private final Server server;

    /*public ClientStatusHandler(final Server server) {
        this.server = server;
    }*/

    @Override
    public void handle(Session session, ClientStatus msg) {
        logger.debug(msg);
        if (session.getPlayer() != null) {
            Player p = session.getPlayer();
            Position spawnPos = p.getWorld().getSpawnPosition();
            switch (msg.getStatus()) {
                case RESPAWN:
                    p.sendPacket(new Respawn(0, Difficulty.NORMAL, p.getGameMode(), WorldType.DEFAULT));
                    p.sendPacket(new SpawnPosition(spawnPos));
                    p.sendPacket(new ServerPlayerPositionLook(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0, 0, 1));
                    break;
                case STATS:
                    break;
            }
        }
    }
}
