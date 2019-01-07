package ru.enke.sansara.player;

import ru.enke.minecraft.protocol.packet.PacketMessage;
import ru.enke.minecraft.protocol.packet.data.game.GameMode;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.game.Slot;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageType;
import ru.enke.minecraft.protocol.packet.server.game.Disconnect;
import ru.enke.minecraft.protocol.packet.server.game.ServerChat;
import ru.enke.minecraft.protocol.packet.server.game.ServerItemHeldChange;
import ru.enke.minecraft.protocol.packet.server.game.entity.UpdateHealth;
import ru.enke.minecraft.protocol.packet.server.game.inventory.InventorySetSlot;
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
    private boolean dead;
    private float health = 20.0F;
    private int foodLevel = 10;
    private float saturation = 5.0F;

    public Player(final int id, final Session session, final World world, final LoginProfile profile) {
        this.id = id;
        this.session = session;
        this.world = world;
        this.profile = profile;
    }

    public void tick() {
        if (isDead()) {
            sendPacket(new UpdateHealth(0.0F, getFoodLevel(), getSaturation()));
        }
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        if (health <= 0.0F) {
            setDead(true);
        }
        this.health = health;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    /* TODO: MOVE THIS TO inventory class */
    public void setHeldItemSlot(int slot) {
        sendPacket(new ServerItemHeldChange(slot));
    }

    public void setItem(int slotIndex, int window, int materialid, int quantity) {
        sendPacket(new InventorySetSlot(window, slotIndex, new Slot(materialid, quantity, 0, new byte[]{0})));
    }
}
