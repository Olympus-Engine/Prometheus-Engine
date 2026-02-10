package fr.olympus.prometheus.entity;

import java.util.Map;

/**
 * Interface representing an entity that has associated statistics. This interface provides methods to retrieve, merge, and replace statistics values.
 */
public interface StatableEntity {

    /**
     * Map of statistic names to their corresponding numeric values.
     * @return A map containing the current statistics values for this entity, where the key is the statistic name and the value is its numeric value.
     */
    Map<String, Number> getStatsValues();

    /**
     * Merge new statistics into the existing stats values. If a statistic already exists, it will be updated with the new value.
     * @param newStats Map of new statistic names to their corresponding numeric values to be merged into the existing stats.
     */
    default void mergeStats(Map<String, Number> newStats) {
        Map<String, Number> currentStats = getStatsValues();
        for (Map.Entry<String, Number> entry : newStats.entrySet()) {
            String statName = entry.getKey();
            Number newValue = entry.getValue();
            currentStats.merge(statName, newValue, (oldValue, newVal) -> {
                if (oldValue instanceof Double || newVal instanceof Double) {
                    return newVal.doubleValue() + oldValue.doubleValue();
                } else {
                    return newVal.longValue() + oldValue.longValue();
                }
            });
        }
    }

    /**
     * Replace the existing stats values with the new ones provided in the map. This will clear the current stats and set them to the new values.
     * @param newStats Map of new statistic names to their corresponding numeric values that will replace the existing stats.
     */
    default void replaceStats(Map<String, Number> newStats) {
        Map<String, Number> currentStats = getStatsValues();
        currentStats.clear();
        currentStats.putAll(newStats);
    }

}
