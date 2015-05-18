package se.uu.it.runestone.teamone.scheduler;

import se.uu.it.runestone.teamone.robotcontrol.Robot;

import java.util.ArrayList;
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

    private ArrayList<Robot> availableRobots;

    /**
     * The designated initializer. Creates a new scheduler.
     */
    public Scheduler() {
        this.queue = new PriorityQueue<Job>(100, new JobComparator());
    }

    /**
     * Fetches the next job in the queue based on job priority.
     *
     * @return The next Job in the queue.
     */
    public synchronized Job nextJob() {
        if (this.queue.isEmpty()) {
            return null;
        }

        return this.queue.poll();
    }

    /**
     * Adds a job to the queue.
     *
     * @param job The job to add.
     */
    public synchronized void scheduleJob(Job job) {
        this.queue.add(job);
    }
}
