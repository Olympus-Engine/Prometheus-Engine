package fr.olympus.prometheus.evolution;

/**
 * Interface representing a condition for evolution.
 */
public interface EvolutionCondition {
    /**
     * Checks if the given entity can evolve based on this condition.
     * @param entity The entity to check for evolution.
     * @return true if the entity can evolve, false otherwise.
     * @param <T> The type of the entity, which must extend EvolutionEntity.
     */
    <T extends EvolutionEntity> boolean canEvolve(T entity);

    /**
     * Gets the unique identifier of the entity associated with this evolution condition.
     * @return A string representing the unique identifier of the entity associated with this evolution condition.
     */
    String getEntityId();

}
