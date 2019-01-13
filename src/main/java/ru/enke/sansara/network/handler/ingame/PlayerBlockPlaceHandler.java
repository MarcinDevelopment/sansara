package ru.enke.sansara.network.handler.ingame;

import org.pmw.tinylog.Logger;
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlace;
import ru.enke.minecraft.protocol.packet.data.game.BlockState;
import ru.enke.minecraft.protocol.packet.data.game.Direction;
import ru.enke.minecraft.protocol.packet.data.game.GameMode;
import ru.enke.minecraft.protocol.packet.data.game.Position;
import ru.enke.minecraft.protocol.packet.data.message.Message;
import ru.enke.minecraft.protocol.packet.data.message.MessageColor;
import ru.enke.minecraft.protocol.packet.server.game.block.BlockChange;
import ru.enke.sansara.Server;
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
        if (!canPlace()) {
            return;
        }
        int blockId = p.getInventory().getItemInHand().getId();
        int blockData = p.getInventory().getItemInHand().getMetadata();
        int blockq = p.getInventory().getItemInHand().getQuantity();
        Logger.info(msg);
        if (blockId == 0 || blockq == 0) {
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

        if (p.getGameMode() != GameMode.CREATIVE) {
            p.getInventory().removeItem(p.getInventory().getItemInHandIndex(), 1);
        }
        server.broadcast(new Message("blockid: " + blockId + ":" + blockData + " q: " + p.getInventory().getItemInHand().getQuantity(), MessageColor.GREEN));
        server.sendPacketToNearbyPlayers(p, new BlockChange(new Position(x, y, z), new BlockState(blockId /* item in hand */, blockData)), false);
    }

    private boolean canPlace() {
        return true; //TODO get loc from chunk
    }
}
