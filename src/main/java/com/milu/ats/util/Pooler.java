package com.milu.ats.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * max.chen
 * 并发异步任务封装库，解决 ForkJoinPool 线程均为 daemon 的问题
 * 对 CompletableFuture 做一些简单封装
 */
final public class Pooler {
    private static Logger log = LoggerFactory.getLogger(Pooler.class);
    private static ExecutorService executor;
    private static ItTaskThreadFactory itTaskThreadFactory;
    private static String ERROR_MSG = "Pool同步失败";

    static {
        itTaskThreadFactory = new ItTaskThreadFactory();
        int cores = Runtime.getRuntime().availableProcessors();
        cores = cores < 1 ? 1 : cores;
        executor = Executors.newFixedThreadPool(cores, itTaskThreadFactory);
        log.info("Task Pooler init finished, available core are {}", cores);
    }

    public static <T> CompletableFuture<T> async(Callable<T> callable) {
        CompletableFuture<T> cf = new CompletableFuture<>();

        executor.submit(() -> {
            try {
                cf.complete(callable.call());
            } catch (Exception ex) {
                cf.completeExceptionally(ex);
            }
        });

        return cf;
    }

    public static CompletableFuture<Void> async(Runnable task) {
        Callable<Void> callable = () -> {
            task.run();
            return null;
        };

        return async(callable);
    }

    public static <T> CompletableFuture<T> sync(Supplier<T> s) {
        CompletableFuture<T> ret = new CompletableFuture<>();
        try {
            ret.complete(s.get());
        } catch (Exception ex) {
            ret.completeExceptionally(ex);
        }
        return ret;
    }

    public static CompletableFuture<Void> sync(Runnable r) {
        return sync(() -> {
            r.run();
            return null;
        });
    }

    /**
     * 简单的同步方法，假设传递进来的 cf 应该不会被客户端代码取消或者打断
     */
    public static void combineNow(CompletableFuture<Void>... cfs) {
        try {
            CompletableFuture
                    .allOf(cfs)
                    .get();
        } catch (ExecutionException | InterruptedException ex) {
            Assert.isTrue(false, ERROR_MSG);
        }
    }

    public static void combineNow(Runnable... tasks) {
        CompletableFuture<Void>[] cfs = new CompletableFuture[tasks.length];

        IntStream.range(0, tasks.length).forEach(i -> cfs[i] = async(tasks[i]));

        combineNow(cfs);
    }

    /**
     * 简单的同步方法，假设传递进来的 cf 应该不会被客户端代码取消或者打断
     * 返回结果的顺序与传递顺序相同
     */
    public static List<Object> combineNowRet(CompletableFuture<Object>... cfs) {
        List<Object> ret = Lists.newArrayList(cfs.length);
        try {
            for (CompletableFuture<Object> cf : cfs) {
                ret.add(cf.get());
            }
        } catch (ExecutionException | InterruptedException ex) {
            Assert.isTrue(false, ERROR_MSG);
        }
        return ret;
    }

    static class ItTaskThreadFactory implements ThreadFactory {
        AtomicInteger cnt = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int order = cnt.addAndGet(1);
            return new Thread(r, "Pooler-Worker-" + order);
        }
    }
}
