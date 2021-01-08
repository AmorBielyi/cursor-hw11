package com.romanbielyi;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        // note: if you want to compute n member, you should pass n+1,
        // so here we compute 25 member of the fibb seq
        ConcurrentFib concurrentFib = new ConcurrentFib(26);
        ForkJoinPool pool = new ForkJoinPool();
        int m = pool.invoke(concurrentFib);
        System.out.println("expected member: " + m);
    }
}

class ConcurrentFib extends RecursiveTask<Integer> {
    final int n;

    ConcurrentFib(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        ConcurrentFib concurrentFib1 = new ConcurrentFib(n - 1);
        concurrentFib1.fork();
        ConcurrentFib concurrentFib2 = new ConcurrentFib(n - 2);
        int m2 = concurrentFib1.join();
        int m1 = concurrentFib2.compute();
        if (n >= 3) {
            System.out.printf("pair = (%d-%d); m1 = %d; m2 = %d;\n",
                    (n - 3),
                    (n - 2),
                    m1,
                    m2);
        }
        return m2 + m1;
    }
}


