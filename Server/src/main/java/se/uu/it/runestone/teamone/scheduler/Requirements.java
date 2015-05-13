package se.uu.it.runestone.teamone.scheduler;

import se.uu.it.runestone.teamone.pathfinding.PathFindingRequirements;

/**
 * Represents a set of requirements for goods to be stored.
 *
 * @author Åke Lagercrantz
 */
public class Requirements implements PathFindingRequirements {

    private Integer minSize;
    /**
     * The available size needed of the storage space.
     *
     * A hard requirement for the storage space.
     */
    public Integer minSize() {
        return this.minSize;
    }

    private Float maxTemperature;
    /**
     * The maximum temperature this goods can be stored in
     * before going bad.
     *
     * A hard requirement for the storage space.
     */
    public Float getMaxTemperature() {
        return this.maxTemperature;
    }

    private Float maxLightIntensity;
    /**
     * The maximum light intensity this goods can be stored
     * in before going bad.
     *
     * A hard requirement for the storage space.
     */
    public Float getMaxLightIntensity() {
        return this.maxLightIntensity;
    }

    /**
     * The designated initializer. Creates new requirements with the given
     * parameters.
     *
     * @param minSize           The minimum available size needed in the storage space.
     * @param maxTemperature    The maximum temperature allowed in the storage space.
     * @param maxLightIntensity The maximum light intensity allowed in the storage space.
     */
    public Requirements(Integer minSize, Float maxTemperature, Float maxLightIntensity) {
        this.minSize = minSize;
        this.maxTemperature = maxTemperature;
        this.maxLightIntensity = maxLightIntensity;
    }

}
