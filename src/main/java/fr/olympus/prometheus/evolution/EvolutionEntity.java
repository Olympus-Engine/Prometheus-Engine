package fr.olympus.prometheus.evolution;

import fr.olympus.prometheus.Prometheus;
import fr.olympus.prometheus.entity.IEntity;
import fr.olympus.prometheus.entity.StatableEntity;

import java.util.List;

/**
 * Interface for representing an entity that can evolve.
 */
public interface EvolutionEntity extends IEntity {

    /**
     * Get the list of evolution conditions that can be applied to this entity.
     *
     * @return the list of evolution conditions that can be applied to this entity.
     */
    List<String> getNextEvolutions();

    /**
     * Get the list of evolution conditions that can be applied to this entity.
     *
     * @return the list of evolution conditions that can be applied to this entity.
     */
    List<String> getPreviousEvolutions();

    /**
     * Checks if this entity can evolve to the given evolution condition.
     *
     * @param evolutionCondition the evolution condition to check.
     * @return true if this entity can evolve to the given evolution condition, false otherwise.
     */
    default boolean canEvolveTo(EvolutionCondition evolutionCondition) {
        if (evolutionCondition == null) return false;
        return evolutionCondition.canEvolve(this);
    }

    /**
     * Gets the evolution condition associated with the given evolution condition ID.
     *
     * @param evolutionConditionID the unique identifier of the evolution condition to retrieve.
     * @return the evolution condition associated with the given evolution condition ID, or null if no such evolution condition exists.
     */
    default EvolutionCondition getEvolutionCondition(String evolutionConditionID) {
        String condition = getNextEvolutions().stream().filter(id -> id.equals(evolutionConditionID))
                .findFirst()
                .orElse(null);
        if (condition == null) return null;
        return Prometheus.getData().getEvolutionCondition(condition);
    }

    /**
     * Evolves this entity to the given evolution condition ID if possible.
     *
     * @param evolutionConditionID the unique identifier of the evolution condition to evolve to.
     * @return true if this entity was successfully evolved to the given evolution condition ID, false otherwise.
     */
    default boolean evolveTo(String evolutionConditionID) {
        EvolutionCondition evolutionCondition = getEvolutionCondition(evolutionConditionID);
        if (canEvolveTo(evolutionCondition)) {
            String registryId = evolutionCondition.getEntityId();
            if (registryId == null) return false;
            IEntity newEntity = Prometheus.getData().createEntity(registryId);
            if (this instanceof StatableEntity statableEntity && newEntity instanceof StatableEntity newStatableEntity) {
                newStatableEntity.mergeStats(statableEntity.getStatsValues());
            }
            mergeData(newEntity);
            getPreviousEvolutions().add(evolutionConditionID);

            return true;
        }
        return false;
    }

    /**
     * Merges the data of this entity with the given entity. This method is called when evolving to a new entity, and allows to transfer any relevant data from the old entity to the new one.
     * Can be overridden by implementing classes to provide custom merging logic. By default, this method does nothing.
     *
     * @param other the entity to merge data with. This is the new entity that is being evolved to.
     */
    default void mergeData(IEntity other) {

    }


}
