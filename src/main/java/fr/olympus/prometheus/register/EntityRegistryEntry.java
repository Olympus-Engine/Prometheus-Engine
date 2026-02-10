package fr.olympus.prometheus.register;

import fr.olympus.prometheus.entity.IEntity;

import java.util.function.Supplier;

/**
 * Represents an entry in the entity registry, containing the entity's unique identifier and a supplier for creating instances of the entity.
 *
 * @param id The unique identifier for the entity.
 * @param supplier A supplier that provides instances of the entity when requested.
 * @param groups An array of group identifiers that this entity belongs to, used for categorization and retrieval purposes.
 */
public record EntityRegistryEntry(String id, Supplier<? extends IEntity> supplier, String[] groups) {

    /**
     * Constructs a new EntityRegistryEntry with the specified unique identifier and supplier.
     * @param id The unique identifier for the entity.
     * @param supplier A supplier that provides instances of the entity when requested.
      * @throws IllegalArgumentException if id is null or if supplier is null.
     */
    public EntityRegistryEntry {
        if (id == null) throw new IllegalArgumentException("Entity id cannot be null");
        if (supplier == null) throw new IllegalArgumentException("Entity supplier cannot be null");
        if (groups == null) throw new IllegalArgumentException("Entity groups cannot be null");
    }

    /**
     * Creates a new instance of the entity using the supplier.
     * @return A new instance of the entity provided by the supplier.
      * @throws RuntimeException if the supplier fails to create an instance.
      * @throws IllegalStateException if the created instance is null or not an instance of IEntity
     */
    public IEntity createInstance() {
        return supplier.get();
    }

    /**
     * Checks if the entity belongs to a specific group.
     * @param group The group identifier to check against the entity's groups.
     * @return true if the entity belongs to the specified group, false otherwise.
     */
    public boolean hasGroup(String group) {
        for (String g : groups) {
            if (g.equals(group)) return true;
        }
        return false;
    }

    /**
     * Checks if the entity belongs to all specified groups.
     * @param groups An array of group identifiers to check against the entity's groups.
     * @return true if the entity belongs to all specified groups, false otherwise.
     */
    public boolean hasGroups(String... groups) {
        for (String group : groups) {
            if (!hasGroup(group)) return false;
        }
        return true;
    }



}
