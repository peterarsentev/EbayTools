package com.ebaytools.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkerPool {
    private static Log log = LogFactory.getLog(Pool.class);
    private final Pool pool;
    private final static Set<Long> workers = Collections.synchronizedSet(new TreeSet<Long>());

    public WorkerPool(int itemMax, int threadMin, int threadMax, long secKeepAlive) {
        pool = new Pool(itemMax, threadMin, threadMax, secKeepAlive);
    }

    private static class Pool extends ThreadPoolExecutor {

        private Pool(int itemMax, int threadMin, int threadMax, long secKeepAlive) {
            super(threadMin, threadMax, secKeepAlive, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(itemMax, true));
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            workers.remove(((Worker) r).getId());
        }
    }

    public synchronized int process(final Worker w) {
        try {
            if (!workers.contains(w.getId())) {
                workers.add(w.getId());
                pool.execute(w);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            workers.remove(w.getId());
            return -1;
        }
    }

    public static abstract class Worker implements Runnable {

        private final Long id;

        public Worker(final Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
