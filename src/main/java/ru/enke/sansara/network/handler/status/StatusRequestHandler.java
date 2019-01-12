package ru.enke.sansara.network.handler.status;

import ru.enke.minecraft.protocol.Protocol;
import ru.enke.minecraft.protocol.packet.client.status.StatusRequest;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.data.status.Players;
import ru.enke.minecraft.protocol.packet.data.status.ServerStatusInfo;
import ru.enke.minecraft.protocol.packet.data.status.Version;
import ru.enke.minecraft.protocol.packet.server.status.StatusResponse;
import ru.enke.sansara.Server;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

public class StatusRequestHandler implements MessageHandler<StatusRequest> {

    private final Server server;

    public StatusRequestHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(final Session session, final StatusRequest statusRequest) {
        final Version version = new Version(Protocol.VERSION_NAME, Protocol.VERSION);

        final Players players = new Players(server.getPlayers().size(), 20);
        final Message description = new Message("Sansara server", MessageColor.YELLOW);
        final String favicon = server.getFavicon();

        final ServerStatusInfo statusInfo = new ServerStatusInfo(version, description, players, favicon);
        session.sendPacket(new StatusResponse(statusInfo));
    }

}
