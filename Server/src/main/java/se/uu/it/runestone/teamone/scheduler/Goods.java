package se.uu.it.runestone.teamone.scheduler;

import se.uu.it.runestone.teamone.pathfinding.frontier.RequirementChecker;

/**
 * Represents some kind of goods that needs to be stored in the warehouse.
 *
 * @author Åke Lagercrantz
 */
public class Goods {

    private Requirements requirements;
    /**
     * The requirements needed to store these goods in
     * a warehouse.
     */
    public Requirements getRequirements() {
       return this.requirements;
    }

    /**
     * The designated initializer. Will create a new goods object with the
     * given requirements.
     *
     * @param size              The required storage space of the goods.
     * @param maxTemperature    The maximum temperature the goods can be
     *                          stored in before going bad.
     * @param maxLightIntensity The maximum light intensity the goods
     *                          can be stored in before going bad.
     */
    public Goods(Integer size, Float maxTemperature, Float maxLightIntensity) {
        this.requirements = new Requirements(size, maxTemperature, maxLightIntensity);
    }
}
