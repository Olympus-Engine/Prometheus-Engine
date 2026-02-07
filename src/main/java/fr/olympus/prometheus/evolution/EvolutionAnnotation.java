package fr.olympus.prometheus.evolution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark classes related to evolution mechanics.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EvolutionAnnotation {

    /**
     * Gets the unique identifier for the evolution.
     * @return A string representing the unique identifier for the evolution.
     */
    String id();

}
