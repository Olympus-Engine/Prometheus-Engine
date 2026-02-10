package fr.olympus.prometheus.entity;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark classes related to entity mechanics.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityAnnotation {

    /**
     * Gets the unique identifier for the entity.
     * @return A string representing the unique identifier for the entity.
     */
    String id();

    /**
     * Gets the groups that the entity belongs to.
     * @return An array of strings representing the groups that the entity belongs to.
     */
    String[] groups() default {};

}
