package se.uu.it.runestone.teamone.scheduler;

import java.util.PriorityQueue;

/**
 * Represents a scheduler that keeps a job queue for warehouse
 * goods storage.
 *
 * The queue works in FIFO mode.
 *
 * @author Åke Lagercrantz
 */
public class Scheduler {

    private PriorityQueue<Job> queue;
    private JobComparator jobComparator;

    /**
     * The designated initializer. Creates a new scheduler.
     */
    public Scheduler() {
        this.queue = new PriorityQueue<>(100, new JobComparator());
    }

    /**
     * Whether there are jobs waiting.
     *
     * @return Whether there are jobs waiting.
     */
    public Boolean hasJobsWaiting() {
        return !this.queue.isEmpty();
    }

    /**
     * Fetches the next job in the queue based on job priority.
     *
     * @return The next Job in the queue.
     */
    public Job nextJob() {
        return this.queue.poll();
    }

    /**
     * Adds a job to the queue.
     *
     * @param job The job to add.
     */
    public void scheduleJob(Job job) {
        this.queue.add(job);
    }
}
