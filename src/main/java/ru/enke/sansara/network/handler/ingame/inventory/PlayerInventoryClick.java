package ru.enke.sansara.network.handler.ingame.inventory;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.inventory.InventoryClick;
import ru.enke.minecraft.protocol.packet.data.game.ItemStack;
import ru.enke.sansara.Inventory.Inventory;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerInventoryClick implements MessageHandler<InventoryClick> {

    @Override
    public void handle(Session session, InventoryClick msg) {
        Player p = session.getPlayer();
        if (p == null) {
            return;
        }
        int mode = msg.getMode();
        int window = msg.getWindowId();
        ItemStack itemStack = msg.getItemStack();
        Inventory playerInventory = p.getInventory();

        Logger.warn(msg);
    }
}
