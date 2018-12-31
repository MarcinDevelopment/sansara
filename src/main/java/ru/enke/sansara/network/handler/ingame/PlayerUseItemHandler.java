package ru.enke.sansara.network.handler.ingame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enke.minecraft.protocol.packet.client.game.UseItem;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerUseItemHandler implements MessageHandler<UseItem> {

    private static final Logger logger = LogManager.getLogger();
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
        logger.info(msg);
        //server.sendGlobalPacket(new Animation(p.getId(), *); //temp
    }
}
