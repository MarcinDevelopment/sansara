package ru.enke.sansara.player;

import ru.enke.minecraft.protocol.packet.PacketMessage;
import ru.enke.minecraft.protocol.packet.data.game.GameMode;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.Disconnect;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.sansara.World;
import ru.enke.sansara.login.LoginProfile;
import ru.enke.sansara.network.session.Session;

import java.net.SocketAddress;

public class Player {

    private final int id;
    private final World world;
    private final LoginProfile profile;
    private final Session session;
    private Position location;
    private GameMode gameMode;
    private int renderDistance;
    private String locale;
    private boolean operator;
    private float experienceBar = 0.0F;
    private int experienceLevel = 0;

    public Player(final int id, final Session session, final World world, final LoginProfile profile) {
        this.id = id;
        this.session = session;
        this.world = world;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }

    public LoginProfile getProfile() {
        return profile;
    }

    public void sendPacket(final PacketMessage msg) {
        session.sendPacket(msg);
    }

    public SocketAddress getAddress() {
        return session.getAddress();
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void sendMessage(Message message) {
        sendPacket(new ServerChat(message, MessageType.CHAT));
    }

    public int getRenderDistance() {
        return renderDistance;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isOperator() {
        return operator;
    }

    public void setOperator(boolean operator) {
        this.operator = operator;
    }

    public void kick(String reason) {
        sendPacket(new Disconnect(new Message(reason)));
    }

    public float getExperienceBar() {
        return experienceBar;
    }

    public void setExperienceBar(float experienceBar) {
        this.experienceBar = experienceBar;
    }

    public void addToExperienceBar(float experienceBar) {
        this.experienceBar += experienceBar;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void addExperienceLevel(int experienceLevel) {
        this.experienceLevel += experienceLevel;
    }

}
