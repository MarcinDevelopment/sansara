package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.ClientSettings;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class ClientSettingsHandler implements MessageHandler<ClientSettings> {

    public ClientSettingsHandler() {
    }

    @Override
    public void handle(Session session, ClientSettings msg) {
        Logger.debug("render distance: " + msg.getRenderDistance());
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        p.setRenderDistance(msg.getRenderDistance());
        p.setLocale(msg.getLocale());
    }
}
