package fr.olympus.prometheus.entity;

import java.util.List;
import java.util.UUID;

/**
 * Interface for representing a family of entities.
 */
public interface FamilyEntity{

    /**
     * Gets the UUIDs of the parent entities in this family.
     * @return A list of UUIDs representing the parent entities.
     */

    /**
     * Gets the UUIDs of the child entities in this family.
     * @return A list of UUIDs representing the child entities.
     */
    List<UUID> getChildUUIDs();

}
