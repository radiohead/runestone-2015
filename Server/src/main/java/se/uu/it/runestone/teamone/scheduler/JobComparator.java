package se.uu.it.runestone.teamone.scheduler;

import java.util.Comparator;

/**
 * Compares two jobs based on priority
 *
 * @author Åke Lagercrantz
 */
public class JobComparator implements Comparator<Job> {
    @Override
    public int compare(Job job, Job other) {
        return job.priority.compareTo(other.priority);
    }
}
