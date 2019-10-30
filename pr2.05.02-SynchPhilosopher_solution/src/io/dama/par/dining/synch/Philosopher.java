package io.dama.par.dining.synch;

import java.util.Random;

public class Philosopher extends Thread {
    private final Chopstick left;
    private final Chopstick right;
    private final Random    random;
    private int             eaten;

    private volatile boolean stop;

    public void stopPhilosopher() {
        System.out.println(getId() + " stopping");
        this.stop = true;
        interrupt();
    }

    public Philosopher(final Chopstick left, final Chopstick right) {
        this.stop = false;
        this.left = left;
        this.right = right;
        this.random = new Random();
        this.eaten = 0;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() + " starting");
        try {
            while (!this.stop) {
                think();
                eat();
            }
        } catch (final InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getId() + " stopped; eaten=" + this.eaten);
    }

    private void think() throws InterruptedException {
        Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_THINKING_DURATION_MS));
    }

    private void eat() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + " try taking left");
        synchronized (this.left) {
            System.out.println(Thread.currentThread().getId() + " left acquired");
            Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_TAKING_TIME_MS));
            System.out.println(Thread.currentThread().getId() + " try taking right");
            synchronized (this.right) {
                System.out.println(Thread.currentThread().getId() + " right acquired");
                System.out.println(Thread.currentThread().getId() + " eating");
                this.eaten++;
                Thread.sleep(PhilosopherExperiment.MAX_EATING_DURATION_MS);
            }
            System.out.println(Thread.currentThread().getId() + " right released");
        }
        System.out.println(Thread.currentThread().getId() + " left released");
    }
}
