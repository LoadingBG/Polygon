package polygon.utils;

public final class ScheduledThreadTask {
    private boolean stop = false;

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
        try { Thread.sleep(0); } catch (InterruptedException e) {} // Make function work properly in the thread
        return stop;
    }

    public void cancel() {
        stop = true;
    }
}
