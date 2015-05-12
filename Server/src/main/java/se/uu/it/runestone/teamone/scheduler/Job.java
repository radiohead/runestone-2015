package se.uu.it.runestone.teamone.scheduler;

/**
 * Represents a warehouse goods storage job.
 *
 * @author Åke Lagercrantz
 */
public class Job {

    /**
     * Gives the priority of a job. Jobs will be executed
     * by order of priority. HIGH > MEDIUM > LOW.
     */
    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    /**
     * The goods to be stored by this job.
     */
    public Goods goods;

    /**
     * The priority of this job.
     */
    public Priority priority;

    /**
     * The designated initializer. Creates a new job with the given goods.
     *
     * @param goods     The goods to be stored.
     * @param Priority  The priority of this job.
     */
    public Job(Goods goods, Priority priority) {
        this.goods = goods;
        this.priority = priority;
    }
}
