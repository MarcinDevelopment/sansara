package ru.enke.sansara.network.handler.login;

import ru.enke.minecraft.protocol.packet.client.login.LoginStart;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Server;
import ru.enke.sansara.login.LoginProfile;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;

import java.util.UUID;

public class LoginStartHandler implements MessageHandler<LoginStart> {

    private final Server server;

    public LoginStartHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(final Session session, final LoginStart loginStart) {
        if (server.isOnlineMode()) {
            // TODO: Add online login.
        } else {
            session.joinGame(new LoginProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + loginStart.getUsername()).getBytes()), loginStart.getUsername()));
            server.broadcast(new Message(loginStart.getUsername() + " joined server", MessageColor.YELLOW));
        }
    }
}
