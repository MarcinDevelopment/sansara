package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.SwingArm;
import ru.enke.minecraft.protocol.packet.data.game.AnimationType;
import ru.enke.minecraft.protocol.packet.server.game.Animation;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class ClientSwingArmHandler implements MessageHandler<SwingArm> {

    private final Server server;

    public ClientSwingArmHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, SwingArm msg) {
        if (session.getPlayer() == null) {
            return;
        }
        Player p = session.getPlayer();
        Logger.info(msg);
        server.sendPacketToNearbyPlayers(p, new Animation(p.getId(), AnimationType.SWING_ARM), true);
    }
}
