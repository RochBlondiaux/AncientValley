package me.rochblondiaux.ancientvalley.utils;

import java.util.concurrent.*;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public class CompletablePromise<V> extends CompletableFuture<V> {
    private final Future<V> future;

    public CompletablePromise(Future<V> future) {
        this.future = future;
        CompletablePromiseContext.schedule(this::tryToComplete);
    }

    private void tryToComplete() {
        if (future.isDone()) {
            try {
                complete(future.get());
            } catch (InterruptedException e) {
                completeExceptionally(e);
            } catch (ExecutionException e) {
                completeExceptionally(e.getCause());
            }
            return;
        }

        if (future.isCancelled()) {
            cancel(true);
            return;
        }

        CompletablePromiseContext.schedule(this::tryToComplete);
    }

    public static class CompletablePromiseContext {
        private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

        public static void schedule(Runnable r) {
            SERVICE.schedule(r, 1, TimeUnit.MILLISECONDS);
        }
    }
}