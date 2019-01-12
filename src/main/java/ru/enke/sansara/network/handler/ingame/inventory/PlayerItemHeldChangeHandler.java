package ru.enke.sansara.network.handler.ingame.inventory;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.ClientItemHeldChange;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerItemHeldChangeHandler implements MessageHandler<ClientItemHeldChange> {

    private final Server server;

    public PlayerItemHeldChangeHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, ClientItemHeldChange msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        p.getInventory().setCurrentItemSlot(msg.getSlot());
        Logger.warn(p.getName() + " | " + msg);
    }
}
