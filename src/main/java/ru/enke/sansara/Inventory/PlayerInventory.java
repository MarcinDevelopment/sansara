package ru.enke.sansara.Inventory;

import ru.enke.minecraft.protocol.packet.data.game.ItemStack;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.sansara.Block.Material;
import ru.enke.sansara.player.Player;

public class PlayerInventory extends objInventory {

    private Player p;
    private int HOT_BAR_INDEX = 36;
    private int NORMAL_SLOT_INDEX = 9;
    private int slot;

    public PlayerInventory(Player p) {
        super(p, InventoryType.PLAYER_INVENTORY, "default");
        this.p = p;
    }

    public void addItem(ItemStack itemStack) {
        if (!addHotbarItem(itemStack)) {
            addToMainInventory(itemStack);
        }
    }

    public void setHotbarItem(int i /* 0-8 */, ItemStack itemStack) {
        if (i > 8) throw new IndexOutOfBoundsException("integer out of range");
        this.pinvStorage[i] = itemStack == null ? AIR : itemStack;
        setItem(i + HOT_BAR_INDEX, itemStack);
    }

    public boolean addHotbarItem(ItemStack itemStack) {
        for (int i = HOT_BAR_INDEX; i < InventoryType.PLAYER_INVENTORY.getCapacity(); i++) {
            if (this.pinvStorage[i] == null || this.pinvStorage[i].getId() == Material.AIR.getId()) {
                setItem(i, itemStack);
                p.sendMessage(new Message("addHotbarItem slot index " + i + ", material " + Material.fromId(itemStack.getId()), MessageColor.LIGHT_PURPLE));
                return true;
            }
        }
        return false; //is full
    }

    public void addToMainInventory(ItemStack itemStack) {
        for (int i = NORMAL_SLOT_INDEX; i < (HOT_BAR_INDEX - 1); i++) {
            if (this.pinvStorage[i] == null || this.pinvStorage[i].getId() == Material.AIR.getId()) {
                setItem(i, itemStack);
                p.sendMessage(new Message("addToMainInventory slot index " + i + ", material " + Material.fromId(itemStack.getId()), MessageColor.DARK_AQUA));
                return;
            }
        }
    }

    public void setCurrentItemSlot(int slot) {
        this.slot = slot;
    }

    public int getHeldItemSlot() {
        return slot;
    }

    public int getItemInHandIndex() {
        return slot + HOT_BAR_INDEX;
    }

    public ItemStack getItemInHand() {
        return getItem(getHeldItemSlot() + HOT_BAR_INDEX);
    }

    public void setItemInHand(ItemStack item) {
        setItem(getItemInHandIndex(), item);
    }

    private void loadFromFile() {
        //TODO: NBT
    }

    public void setItemInOffHand(ItemStack item) {
        setItem(45, item);
    }
}
