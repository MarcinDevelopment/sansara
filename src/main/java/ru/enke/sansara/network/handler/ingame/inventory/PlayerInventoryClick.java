package ru.enke.sansara.network.handler.ingame.inventory;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.inventory.InventoryClick;
import ru.enke.minecraft.protocol.packet.data.game.ItemStack;
import ru.enke.sansara.Inventory.PlayerInventory;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerInventoryClick implements MessageHandler<InventoryClick> {

    @Override
    public void handle(Session session, InventoryClick msg) {
        Player p = session.getPlayer();
        ItemStack itemStack = msg.getItemStack();
        if (p == null) {
            return;
        }
        int mode = msg.getMode();
        int window = msg.getWindowId();
        PlayerInventory playerInventory = p.getInventory();
        int slotIndex = msg.getSlotIndex();

        Logger.warn(msg);
        if (slotIndex == -999) {
            //TODO: throw itemstack
        }

        if (mode == 0) {
            //playerInventory.removeItem(slotIndex, 0, true);
        } else {
            //playerInventory.removeItem(slotIndex, 0, true); //shift
        }
    }
}
