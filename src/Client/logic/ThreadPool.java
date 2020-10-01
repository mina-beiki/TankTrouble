package Client.logic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class holds a global thread-pool for executing our threads.
 *
 */
public class ThreadPool {

    private static ExecutorService executor;

    /**
     * initializes the thread pool .
     */
    public static void init() {
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Runs the given runnable in the executorService .
     * @param r
     */
    public static void execute(Runnable r) {
        if (executor == null)
            init();
        executor.execute(r);
    }

    /**
     * Shuts down the thread pool .
     */
    public static void shutdown() {
        executor.shutdown();
    }

}
