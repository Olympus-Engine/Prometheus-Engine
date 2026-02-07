package fr.olympus.prometheus.entity;

import java.util.Map;

/**
 * Interface for make AbstractEntity droppable
 */
public interface DroppableEntity{

    /**
     * Get the drops of the entity
     * String is the material identifier, EntityDrop is the drop information (chance, quantity, etc.)
     * @return Map of item identifiers to their corresponding EntityDrop objects
     */
    Map<String, EntityDrop> getDrops();

    /**
     * Method to handle the dropping of items when the entity is killed. This method should be called when the entity dies to process and drop the items defined in the getDrops() method.
     */
    void drop();
}
