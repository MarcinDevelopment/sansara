package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlace;
import ru.enke.minecraft.protocol.packet.data.game.*;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;
import ru.enke.sansara.Block.Material;
import ru.enke.sansara.Server;
import ru.enke.sansara.Utils.ChatColor;
import ru.enke.sansara.network.handler.MessageHandler;
import ru.enke.sansara.network.session.Session;
import ru.enke.sansara.player.Player;

public class PlayerBlockPlaceHandler implements MessageHandler<BlockPlace> {

    private final Server server;
    private Player p;

    public PlayerBlockPlaceHandler(final Server server) {
        this.server = server;
    }

    @Override
    public void handle(Session session, BlockPlace msg) {
        if (session.getPlayer() == null) {
            return;
        }
        this.p = session.getPlayer();
        ItemStack hand = p.getInventory().getItemInHand();

        int blockId = hand.getId();
        int blockData = hand.getMetadata();
        int blockq = hand.getQuantity();

        Logger.info(msg);
        if (blockId == Material.AIR.getId() || blockq == 0) {
            return;
        }

        int x = msg.getPosition().getX();
        int y = msg.getPosition().getY();
        int z = msg.getPosition().getZ();

        Direction direction = msg.getDirection();
        //TODO make it more elegant
        switch (direction) {
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                z++;
                break;
            case NORTH:
                z--;
                break;
            case WEST:
                x--;
                break;
        }

        Material material = Material.fromId(blockId);
        if (!canPlace(material)) {
            Logger.error("[{}] there's no material by that id (id={})", p.getName(), blockId + ":" + blockData);
            server.sendPacketToNearbyPlayers(p, new BlockChange(new Position(x, y, z), new BlockState(Material.AIR.getId(), 0)), false);
            return;
        }

        if (p.getGameMode() != GameMode.CREATIVE) {
            p.getInventory().removeItem(p.getInventory().getItemInHandIndex(), 1);
        }

        server.broadcast(new Message(ChatColor.GOLD + "Material: " + ChatColor.AQUA + material + ":" + blockData + " q: " + p.getInventory().getItemInHand().getQuantity()));
        server.sendPacketToNearbyPlayers(p, new BlockChange(new Position(x, y, z), new BlockState(blockId /* item in hand */, blockData)), false);
    }

    private boolean canPlace(Material material) {
        return material != null;
    }
}
