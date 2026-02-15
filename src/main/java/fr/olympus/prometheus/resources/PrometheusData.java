package fr.olympus.prometheus.resources;

import fr.olympus.prometheus.entity.Entity;
import fr.olympus.prometheus.entity.IEntity;
import fr.olympus.prometheus.evolution.EvolutionCondition;
import fr.olympus.prometheus.register.EntityRegistryEntry;
import fr.olympus.prometheus.register.EvolutionRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing the main data structure for managing entities and evolutions in the Prometheus system.
 * It contains registries for entities and evolutions, as well as a list of currently loaded entities.
 */
public class PrometheusData {

    /**
     * Registry of entities by their unique identifiers.
     */
    private Map<String, EntityRegistryEntry> entitiesRegistry;

    /**
     * Registry of evolutions by their unique identifiers.
     */
    private Map<String, EvolutionRegistryEntry> evolutionRegistry;

    /**
     * List of currently loaded entities.
     */
    private List<IEntity> loadedEntities;

    /**
     * Constructs a PrometheusData instance with an empty entities registry.
     */
    public PrometheusData() {
        this.entitiesRegistry = new ConcurrentHashMap<>();
        this.loadedEntities = new ArrayList<>();
    }

    /**
     * Registers a new entity in the entities registry.
     * @param entry The EntityRegistryEntry containing the entity's unique identifier and supplier.
     */
    public void registerEntity(EntityRegistryEntry entry) {
        entitiesRegistry.put(entry.id(), entry);
    }

    /**
     * Registers a new evolution in the evolution registry.
     * @param entry The EvolutionRegistryEntry containing the evolution's unique identifier and supplier.
     */
    public void registerEvolution(EvolutionRegistryEntry entry) {
        evolutionRegistry.put(entry.id(), entry);
    }

    /**
     * Retrieves an evolution condition from the evolution registry by its unique identifier.
     * @param conditionId The unique identifier of the evolution condition to retrieve.
     * @return An instance of EvolutionCondition corresponding to the provided unique identifier.
     */
    public EvolutionCondition getEvolutionCondition(String conditionId) {
        EvolutionRegistryEntry entry = evolutionRegistry.get(conditionId);
        if (entry == null) {
            throw new IllegalArgumentException("No evolution condition found for id: " + conditionId);
        }
        return entry.createInstance();
    }

    /**
     * Creates a new entity instance based on the provided registry identifier.
     * @param registryId The unique identifier of the entity in the registry to create.
     * @return An instance of Entity corresponding to the provided registry identifier.
     */
    public IEntity createEntity(String registryId) {
        EntityRegistryEntry entry = entitiesRegistry.get(registryId);
        if (entry == null) {
            throw new IllegalArgumentException("No entity found for registry id: " + registryId);
        }
        IEntity entity = entry.createInstance();
        entity.setRegistryMeta(registryId, entry.groups());
        loadedEntities.add(entity);
        return entity;
    }

    /**
     * Destroys an entity by removing it from the list of loaded entities.
     * @param iEntity The entity instance to destroy.
     */
    public void destroyEntity(IEntity iEntity) {
        loadedEntities.remove(iEntity);
    }

    /**
     * Checks which entities in the registry belong to any of the specified groups and returns their unique identifiers.
     * @param groupsId The unique identifiers of the groups to check against.
     * @return A list of unique identifiers of entities that belong to any of the specified groups.
     */
    public List<String> isInGroups(String... groupsId){
        List<String> groups = new ArrayList<>();
        for (EntityRegistryEntry entry : entitiesRegistry.values()) {
            if (entry.hasGroups(groupsId)) {
                groups.add(entry.id());
            }
        }
        return groups;
    }


    /**
     * Retrieves the list of currently loaded entities.
     * @return A list of IEntity instances representing the currently loaded entities.
     */

    public List<IEntity> getLoadedEntities() {
        return loadedEntities;
    }

    /**
     * Retrieves a list of currently loaded entities that belong to any of the specified groups.
     * @param groupsId The unique identifiers of the groups to check against.
     * @return A list of IEntity instances representing the currently loaded entities that belong to any of the specified groups.
     */
    public List<IEntity> getLoadedEntitiesInGroups(String... groupsId) {
        List<IEntity> entities = new ArrayList<>();
        for (IEntity entity : loadedEntities) {
            if (entity.hasGroups(groupsId)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    /**
     * Retrieves a list of currently loaded entities that have the specified registry identifier.
     * @param registryId The unique identifier of the registry to check against.
     * @return A list of IEntity instances representing the currently loaded entities that have the specified registry identifier.
     */
    public List<IEntity> getLoadedEntitiesWithId(String registryId) {
        List<IEntity> entities = new ArrayList<>();
        for (IEntity entity : loadedEntities) {
            if (entity.getRegistryId().equals(registryId)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    /**
     * Retrieves a list of currently loaded entities that have any of the specified registry identifiers.
     * @param registryIds The unique identifiers of the registries to check against.
     * @return A list of IEntity instances representing the currently loaded entities that have any of the specified registry identifiers.
     */
    public List<IEntity> getLoadedEntitiesWithIds(String... registryIds) {
        List<IEntity> entities = new ArrayList<>();
        for (String registryId : registryIds) {
            entities.addAll(getLoadedEntitiesWithId(registryId));
        }
        return entities;
    }

    /**
     * Retrieves a currently loaded entity that has the specified unique identifier (UUID).
     * @param uuid The unique identifier (UUID) of the entity to retrieve.
     * @return An instance of IEntity representing the currently loaded entity with the specified UUID, or null if no such entity is found.
     */
    public IEntity getLoadedEntityWithUUID(UUID uuid) {
        for (IEntity entity : loadedEntities) {
            if (entity.currentUUID().equals(uuid)) {
                return entity;
            }
        }
        return null;
    }


}
