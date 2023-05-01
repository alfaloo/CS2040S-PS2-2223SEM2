/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        int p_count = 0;
        int job = 0;

        if(jobSizes.length == 0) {
            return false;
        }

        while (p_count < p) {
            int current_p = 0;
            while (current_p + jobSizes[job] <= queryLoad) {
                current_p += jobSizes[job];
                job++;
                if(job == jobSizes.length) {
                    return true;
                }
            }
            p_count++;
        }
        return false;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        if(jobSizes.length == 0)
            return -1;
        int sum = 0;
        int max = 0;
        for (int i = 0; i < jobSizes.length; i++) {
            sum += jobSizes[i];
            if(jobSizes[i] > max) {
                max = jobSizes[i];
            }
        }

        int start = max;
        int end = sum;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if(isFeasibleLoad(jobSizes, mid, p)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;

    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
