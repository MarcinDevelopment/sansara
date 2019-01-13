package ru.enke.sansara.Inventory;

import ru.enke.minecraft.protocol.packet.data.game.ItemStack;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.server.game.ServerItemHeldChange;
import ru.enke.minecraft.protocol.packet.server.game.inventory.InventorySetSlot;
import ru.enke.sansara.player.Player;

import java.util.Arrays;

public class Inventory {
    /* TODO NBT */
    private ItemStack[] pinvStorage = new ItemStack[44];
    private int DEFAULT_PLAYER_INVENTORY_ID = 0;
    private ItemStack AIR = new ItemStack(0, 0, 0, new byte[]{0});
    private int slot;
    private int HBAR = 36;
    private Player p;

    public Inventory(Player player) {
        this.p = player;
    }

    public ItemStack[] getContents() {
        return this.pinvStorage;
    }

    public void clear() {
        Arrays.fill(this.pinvStorage, null);
    }

    public void setItem(int i, ItemStack itemstack) {
        this.pinvStorage[i] = itemstack;
        p.sendPacket(new InventorySetSlot(DEFAULT_PLAYER_INVENTORY_ID, i, itemstack));
    }

    public void addItem(ItemStack itemStack) {
        this.pinvStorage[findEmptySlot()] = itemStack;
        p.sendPacket(new InventorySetSlot(DEFAULT_PLAYER_INVENTORY_ID, findEmptySlot(), itemStack));
    }

    public ItemStack getItem(int i) {
        if (this.pinvStorage[i] == null) {
            return AIR;
        }
        return this.pinvStorage[i];
    }

    public void removeItem(int pos, int q) {
        ItemStack it = this.pinvStorage[pos];
        if (it == null) {
            return;
        }
        if (it.getQuantity() <= 0) {
            this.pinvStorage[pos] = AIR;
            return;
        }
        ItemStack it_f = new ItemStack(it.getId(), (it.getQuantity() - q), it.getMetadata(), new byte[]{0});
        this.pinvStorage[pos] = it_f;
        p.sendPacket(new InventorySetSlot(DEFAULT_PLAYER_INVENTORY_ID, pos, it_f));
    }

    public int getItemInHandIndex() {
        return slot + HBAR;
    }

    public ItemStack getItemInHand() {
        return getItem(getHeldItemSlot() + HBAR);
    }

    public int findEmptySlot() {
        for (int i = 0; i < this.pinvStorage.length; ++i) {
            if (this.pinvStorage[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void setCurrentItemSlot(int slot) {
        this.slot = slot;
    }

    public int getHeldItemSlot() {
        return slot;
    }

    public void setHeldItemSlot(int slot) {
        p.sendPacket(new ServerItemHeldChange(slot));
    }

    private boolean contains(ItemStack itemStack) {
        for (ItemStack aPinvStorage : this.pinvStorage) {
            if (aPinvStorage != null && aPinvStorage == itemStack) {
                return true;
            }
        }
        return false;
    }

    public void checkInv() {
        if (!contains(getItemInHand())) {
            p.sendPacket(new InventorySetSlot(DEFAULT_PLAYER_INVENTORY_ID, getItemInHandIndex(), AIR));
            p.sendMessage(new Message("This item doesn't exist in your inventory!", MessageColor.RED));
        }
    }
}
