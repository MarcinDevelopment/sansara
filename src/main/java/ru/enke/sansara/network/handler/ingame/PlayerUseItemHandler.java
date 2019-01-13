package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.UseItem;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerUseItemHandler implements MessageHandler<UseItem> {

    //private final Server server;

    public PlayerUseItemHandler(/*final Server server*/) {
        //this.server = server;
    }

    @Override
    public void handle(Session session, UseItem msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        Logger.info(msg);
        p.getInventory().checkInv();
        //server.sendGlobalPacket(new Animation(p.getId(), *); //temp
    }
}
