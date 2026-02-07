package fr.olympus.prometheus.register;

import fr.olympus.prometheus.Prometheus;
import fr.olympus.prometheus.entity.EntityAnnotation;
import fr.olympus.prometheus.entity.IEntity;
import fr.olympus.prometheus.evolution.EvolutionAnnotation;
import fr.olympus.prometheus.evolution.EvolutionCondition;
import fr.olympus.prometheus.resources.PrometheusData;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Constructor;

/**
 * Utility class for automatically registering materials, factories, and process recipes
 * by scanning specified base packages for annotated classes.
 */
public final class AutoRegistrar {

    // Prevent instantiation
    private AutoRegistrar() {
    }


    /**
     * Registers components based on the specified type and base packages.
     *
     * @param type         The type of components to register. see {@link RegisterType}.
     * @param basePackages The base packages to scan for components.
     * @throws IllegalArgumentException if type is null or basePackages is null/empty.
     * @throws IllegalStateException    if any annotated class is invalid or cannot be instantiated.
     */
    public static void register(RegisterType type, String... basePackages) {
        if (type == null) throw new IllegalArgumentException("type cannot be null.");
        if (basePackages == null || basePackages.length == 0)
            throw new IllegalArgumentException("basePackages required.");

        PrometheusData data = Prometheus.getData();

        try (ScanResult scan = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(basePackages)
                .scan()) {

            if (type == RegisterType.ALL || type == RegisterType.ENTITY) {

                for (ClassInfo ci : scan.getClassesWithAnnotation(EntityAnnotation.class.getName())) {
                    Class<?> raw = ci.loadClass();
                    if (!IEntity.class.isAssignableFrom(raw)) {
                        throw new IllegalStateException("@EntityAnnotation on non-Entity: " + raw.getName());
                    }
                    @SuppressWarnings("unchecked")
                    Class<? extends IEntity> clazz = (Class<? extends IEntity>) raw;

                    EntityAnnotation ann = clazz.getAnnotation(EntityAnnotation.class);

                    EntityRegistryEntry entry = new EntityRegistryEntry(
                            ann.id(),
                            () -> newInstance(clazz)
                    );
                    data.registerEntity(entry);
                }
            }

            if (type == RegisterType.ALL || type == RegisterType.EVOLUTION) {
                for (ClassInfo ci : scan.getClassesWithAnnotation(EvolutionAnnotation.class.getName())) {
                    Class<?> raw = ci.loadClass();
                    if (!EvolutionCondition.class.isAssignableFrom(raw)) {
                        throw new IllegalStateException("@EvolutionAnnotation on non-EvolutionCondition: " + raw.getName());
                    }
                    @SuppressWarnings("unchecked")
                    Class<? extends EvolutionCondition> clazz = (Class<? extends EvolutionCondition>) raw;

                    EvolutionAnnotation ann = clazz.getAnnotation(EvolutionAnnotation.class);

                    EvolutionRegistryEntry entry = new EvolutionRegistryEntry(
                            ann.id(),
                            () -> newInstance(clazz)
                    );
                    data.registerEvolution(entry);
                }
            }
        }
    }

    /**
     * Creates a new instance of the specified class using its no-argument constructor.
     *
     * @param clazz The class to instantiate.
     * @param <T>   The type of the class.
     * @return A new instance of the specified class.
     * @throws IllegalStateException if the class cannot be instantiated.
     */
    private static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> c = clazz.getDeclaredConstructor();
            c.setAccessible(true);
            return c.newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No-arg constructor required for auto-register: " + clazz.getName(), e);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot instantiate: " + clazz.getName(), e);
        }
    }
}
