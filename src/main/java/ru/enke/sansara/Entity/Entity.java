package ru.enke.sansara.Entity;

import ru.enke.minecraft.protocol.packet.data.game.Position;

public class Entity {
    private final EntityType entityType;

    private Position location;

    public Entity(EntityType entityType, Position location) {
        this.entityType = entityType;
        this.location = location;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return entityType.toString();
    }
}