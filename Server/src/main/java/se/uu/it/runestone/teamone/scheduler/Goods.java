package se.uu.it.runestone.teamone.scheduler;

/**
 * Represents some kind of goods that needs to be stored in the warehouse.
 *
 * @author Åke Lagercrantz
 */
public class Goods {

    private Integer size;
    /**
     * The size of the goods to be storde.
     *
     * A hard requirement for the storage space.
     */
    public Integer getSize() {
       return this.size;
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
        this.size = size;
        this.maxTemperature = maxTemperature;
        this.maxLightIntensity = maxLightIntensity;
    }
}
