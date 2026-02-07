package fr.olympus.prometheus.register;

import fr.olympus.prometheus.evolution.EvolutionCondition;

import java.util.function.Supplier;

/**
 * Represents an entry in the evolution registry, containing the evolution condition's unique identifier and a supplier for creating instances of the evolution condition.
 * @param id The unique identifier for the evolution condition.
 * @param supplier A supplier that provides instances of the evolution condition when requested.
 */
public record EvolutionRegistryEntry(String id, Supplier<? extends EvolutionCondition> supplier) {

    /**
     * Constructs a new EvolutionRegistryEntry with the specified unique identifier and supplier.
     * @param id The unique identifier for the evolution condition.
     * @param supplier A supplier that provides instances of the evolution condition when requested.
     */
    public EvolutionRegistryEntry {
        if (id == null) throw new IllegalArgumentException("Evolution Condition id cannot be null");
        if (supplier == null) throw new IllegalArgumentException("Evolution Condition supplier cannot be null");
    }

    /**
     * Creates a new instance of the evolution condition using the supplier.
     * @return A new instance of the evolution condition provided by the supplier.
      * @throws RuntimeException if the supplier fails to create an instance.
      * @throws IllegalStateException if the created instance is null or not an instance of EvolutionCondition
     */
    public EvolutionCondition createInstance() {
        return supplier.get();
    }

}
