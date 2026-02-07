package fr.olympus.prometheus.entity;

/**
 * Represents a drop that an entity can have upon defeat.
 */
public class EntityDrop {

    /**
     * The minimal quantity of the drop.
     */
    private final int minimalQuantity;
    /**
     * The maximal quantity of the drop.
     */
    private final int maximalQuantity;
    /**
     * The chance of the drop occurring (between 0.0 and 1.0).
     */
    private final double dropChance;

    /**
     * Constructs an EntityDrop with specified minimal and maximal quantities and drop chance.
     *
     * @param minimalQuantity the minimal quantity of the drop
     * @param maximalQuantity the maximal quantity of the drop
     * @param dropChance      the chance of the drop occurring (between 0.0 and 1.0)
     * @throws IllegalArgumentException if minimalQuantity is greater than maximalQuantity
     *                                  or if dropChance is not between 0.0 and 1.0
     */
    public EntityDrop(int minimalQuantity, int maximalQuantity, double dropChance) {
        if (minimalQuantity > maximalQuantity) {
            throw new IllegalArgumentException("Minimal quantity cannot be greater than maximal quantity.");
        }
        if (dropChance > 1 || dropChance < 0) {
            throw new IllegalArgumentException("Drop chance must be between 0.0 and 1.0.");
        }
        this.minimalQuantity = minimalQuantity;
        this.maximalQuantity = maximalQuantity;
        this.dropChance = dropChance;
    }

    /**
     * Constructs an EntityDrop with a fixed quantity and drop chance.
     *
     * @param quantity   the fixed quantity of the drop
     * @param dropChance the chance of the drop occurring (between 0.0 and 1.0)
     */
    public EntityDrop(int quantity, double dropChance) {
        this(quantity, quantity, dropChance);
    }

    /**
     * Constructs an EntityDrop with a fixed quantity and a drop chance of 1.0.
     *
     * @param quantity the fixed quantity of the drop
     */
    public EntityDrop(int quantity) {
        this(quantity, quantity, 1.0);
    }

    /**
     * Gets the minimal quantity of the drop.
     *
     * @return the minimal quantity
     */
    public int getMinimalQuantity() {
        return minimalQuantity;
    }

    /**
     * Gets the maximal quantity of the drop.
     *
     * @return the maximal quantity
     */
    public int getMaximalQuantity() {
        return maximalQuantity;
    }

    /**
     * Gets the chance of the drop occurring.
     *
     * @return the drop chance
     */
    public double getDropChance() {
        return dropChance;
    }


}
