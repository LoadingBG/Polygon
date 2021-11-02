package polygon.utils;

/**
 * A utility class for executing code on a
 * separate thread after a timeout, which can
 * be canceled.
 *
 * <p>Example:
 * <pre><code>
 * ScheduledThreadTask task = new ScheduledThreadTask(1000, () -> System.out.println("hi"));
 * // prints "hi" after 1 second.
 * ScheduledThreadTask task = new ScheduledThreadTask(1000, () -> System.out.println("hi"));
 * task.cancel();
 * // doesn't print anything - task is canceled.
 * </code></pre></p>
 */
public final class ScheduledThreadTask {
    private boolean stop = false;

    /**
     * Starts a new scheduled task.
     *
     * @param timeout The time in milliseconds to wait before
     *                executing the code.
     * @param code The code to execute after the timeout.
     */
    public ScheduledThreadTask(long timeout, Runnable code) {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timeout) {
                if (isCanceled()) {
                    return;
                }
            }
            code.run();
        }).start();
    }

    private boolean isCanceled() {
        // Make function work properly in the thread
        // The compiler won't inline the function because of this line
        try { Thread.sleep(0); } catch (InterruptedException e) {}
        return stop;
    }

    /**
     * Cancels this task.
     */
    public void cancel() {
        stop = true;
    }
}
