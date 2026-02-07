package fr.olympus.prometheus.register;

import fr.olympus.prometheus.entity.IEntity;

import java.util.function.Supplier;

/**
 * Represents an entry in the entity registry, containing the entity's unique identifier and a supplier for creating instances of the entity.
 *
 * @param id The unique identifier for the entity.
 * @param supplier A supplier that provides instances of the entity when requested.
 */
public record EntityRegistryEntry(String id, Supplier<? extends IEntity> supplier) {

    /**
     * Constructs a new EntityRegistryEntry with the specified unique identifier and supplier.
     * @param id The unique identifier for the entity.
     * @param supplier A supplier that provides instances of the entity when requested.
      * @throws IllegalArgumentException if id is null or if supplier is null.
     */
    public EntityRegistryEntry {
        if (id == null) throw new IllegalArgumentException("Entity id cannot be null");
        if (supplier == null) throw new IllegalArgumentException("Entity supplier cannot be null");
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



}
