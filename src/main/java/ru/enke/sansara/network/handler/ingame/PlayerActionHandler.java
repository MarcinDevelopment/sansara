package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.PlayerAction;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerActionHandler implements MessageHandler<PlayerAction> {

    private final Server server;

    public PlayerActionHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, PlayerAction msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        Logger.info(msg);
        switch (msg.getType()) {
            case LEAVE_BED:
                break;
            case STOP_SNEAKING:
                break;
            case START_SNEAKING:
                break;
            case STOP_SPRINTING:
                break;
            case START_SPRINTING:
                break;
            case STOP_HORSE_JUMP:
                break;
            case START_HORSE_JUMP:
                break;
            case START_ELYTRA_FLYING:
                break;
            case OPEN_HORSE_INVENTORY:
        }
    }
}
