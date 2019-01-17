package ru.enke.sansara.Inventory;

import ru.enke.minecraft.protocol.packet.data.game.ItemStack;
import ru.enke.minecraft.protocol.packet.server.game.inventory.InventoryItems;
import ru.enke.minecraft.protocol.packet.server.game.inventory.InventorySetSlot;
import ru.enke.sansara.Block.Material;
import ru.enke.sansara.player.Player;

import java.util.Arrays;
import java.util.List;

public class objInventory {

    ItemStack[] pinvStorage;
    ItemStack AIR = new ItemStack(Material.AIR.getId(), 0, 0, new byte[]{0});
    private Player p;
    private int INVENTORY_WINDOW;
    private String INVENTORY_NAME;
    private int INVENTORY_CAPACITY;
    private String INVENTORY_TYPE;

    public objInventory(Player p, InventoryType invType, String customName) {
        this.pinvStorage = new ItemStack[invType.getCapacity()];
        this.p = p;
        this.INVENTORY_WINDOW = invType.getTypeid();
        if (customName == null) customName = invType.getDefaultName();
        this.INVENTORY_NAME = customName;
        this.INVENTORY_CAPACITY = invType.getCapacity();
        this.INVENTORY_TYPE = invType.getWindowType();
    }

    public ItemStack getItem(int slot) {
        return this.pinvStorage[slot] == null ? AIR : this.pinvStorage[slot];
    }

    public ItemStack[] getContents() {
        return this.pinvStorage;
    }

    public void clear() {
        Arrays.fill(this.pinvStorage, null);
    }

    public void setItem(int i, ItemStack itemstack) {
        this.pinvStorage[i] = itemstack == null ? AIR : itemstack;
        p.sendPacket(new InventorySetSlot(INVENTORY_WINDOW, i, itemstack));
    }

    public void setItems(List<ItemStack> items) {
        for (int i = 0; i < items.size(); ++i) {
            this.pinvStorage[i] = items.get(i) == null ? AIR : items.get(i);
        }
        p.sendPacket(new InventoryItems(INVENTORY_WINDOW, items));
    }

    public void removeItem(int pos, int q) {
        ItemStack it = this.pinvStorage[pos] == null ? AIR : this.pinvStorage[pos];
        if (it.getQuantity() <= 0) {
            this.pinvStorage[pos] = AIR;
            return;
        }
        ItemStack it_f = new ItemStack(it.getId(), (it.getQuantity() - q), it.getMetadata(), it.getState());
        this.pinvStorage[pos] = it_f;
        p.sendPacket(new InventorySetSlot(INVENTORY_WINDOW, pos, it_f));
    }

    public int findEmptySlot() {
        for (int i = 0; i < this.pinvStorage.length; ++i) {
            if (this.pinvStorage[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(ItemStack itemStack) {
        for (ItemStack aPinvStorage : this.pinvStorage) {
            if (aPinvStorage != null && aPinvStorage == itemStack) {
                return true;
            }
        }
        return false;
    }

    public String getInventoryCustomName() {
        return INVENTORY_NAME;
    }

    public String getInventoryWindowType() {
        return INVENTORY_TYPE;
    }

    public int getInventoryWindowId() {
        return INVENTORY_WINDOW;
    }

    public int getInventoryCapacity() {
        return INVENTORY_CAPACITY;
    }
}
