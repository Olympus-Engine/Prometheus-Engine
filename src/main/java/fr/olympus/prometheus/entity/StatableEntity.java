package fr.olympus.prometheus.entity;

import java.util.Map;

public interface StatableEntity {

    /**
     * Map of statistic names to their corresponding numeric values.
     *
     * @warning For use this, use Hercules-Engine library
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

    default void replaceStats(Map<String, Number> newStats) {
        Map<String, Number> currentStats = getStatsValues();
        currentStats.clear();
        currentStats.putAll(newStats);
    }

}
