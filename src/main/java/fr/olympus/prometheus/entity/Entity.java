package fr.olympus.prometheus.entity;

import java.util.UUID;

/**
 * Abstract base class for all entities in the system.
 * Provides common properties and methods for managing entity metadata and state.
 */
public abstract class Entity implements IEntity {


    /**
     * Unique identifier for the entity in the registry.
     */
    protected String registryId;
    /**
     * Name of the entity.
     */
    protected final String name;

    /**
     * Current UUID of the entity instance.
     */
    protected UUID currentUUID;
    /**
     * Indicates whether the entity is alive.
     */
    protected boolean isAlive;

    /**
     * Groups that this entity belongs to.
     */
    protected String[] groups;

    /**
     * Constructs a new Entity with the specified name.
     *
     * @param name The name of the entity.
     * @throws IllegalArgumentException if name is null or blank.
     */
    protected Entity(String name) {
        this.name = name;
        this.isAlive = true;
    }

    /**
     * Gets the unique identifier of the entity in the registry.
     *
     * @return The registry ID of the entity.
     */
    public String getRegistryId() {
        return registryId;
    }

    /**
     * Sets the registry metadata for the entity.
     *
     * @param registryId The unique identifier in the registry.
     * @throws IllegalArgumentException if registryId is null or blank.
     */
    public void setRegistryMeta(String registryId, String[] groups) {
        if (registryId == null || registryId.isBlank())
            throw new IllegalArgumentException("Registry ID cannot be null or blank");
        this.registryId = registryId;
        this.currentUUID = UUID.randomUUID();
        this.groups = groups;
    }

    /**
     * Checks if the entity is alive.
     * @return true if the entity is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the alive status of the entity.
     * @param alive A boolean value indicating whether the entity is alive (true) or dead (false).
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }


    /**
     * Sets the alive status of the entity.
     * @return true if the entity is now alive, false if it is now dead.
     */
    public UUID currentUUID() {
        return currentUUID;
    }

    /**
     * Gets the name of the entity.
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the groups that this entity belongs to.
     * @return An array of group names that this entity belongs to.
     */
    public String[] entityGroup() {
        return groups;
    }


}
