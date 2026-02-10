package fr.olympus.prometheus.entity;

import fr.olympus.prometheus.Prometheus;

import java.util.UUID;

/**
 * Interface representing an entity in the Prometheus system.
 */
public interface IEntity {

    /**
     * Gets the unique identifier for the entity registry.
     * @return A string representing the unique identifier for the entity registry.
     */
    String getRegistryId();

    /**
     * Gets the name of the entity.
     * @return A string representing the name of the entity.
     */
    String getName();

    /**
     * Sets the metadata for the entity registry based on the provided registry ID.
     * @param registryId A string representing the unique identifier for the entity registry.
     * @param groups An array of strings representing the groups to which the entity belongs.
     */
    void setRegistryMeta(String registryId, String[] groups);

    /**
     * Checks if the entity is alive.
     * @return true if the entity is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Sets the alive status of the entity.
     * @param alive A boolean value indicating whether the entity is alive (true) or dead (false).
     */
    void setAlive(boolean alive);

    /**
     * Gets the current UUID of the entity.
     * @return A UUID representing the current unique identifier of the entity.
     */
    UUID currentUUID();

    /**
     * Kills the entity, marking it as dead and performing any necessary cleanup operations.
     */
    default void kill() {
        if (!isAlive()) throw new IllegalStateException("Entity is already dead");
        setAlive(false);
        if (this instanceof DroppableEntity droppable) {
            droppable.drop();
        }
        Prometheus.getData().destroyEntity(this);
    }

    /**
     * Gets the entity group to which this entity belongs.
     * @return An array of strings representing the entity group(s) to which this entity belongs.
     */
    String[] entityGroup();

}
