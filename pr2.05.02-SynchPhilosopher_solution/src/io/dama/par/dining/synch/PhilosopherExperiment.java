package io.dama.par.dining.synch;

public class PhilosopherExperiment {
    static final int     MAX_THINKING_DURATION_MS = 3000;
    static final int     MAX_EATING_DURATION_MS   = 3000;
    static final int     MAX_TAKING_TIME_MS       = 100;
    static final int     PHILOSOPHER_NUM          = 5;
    static final int     EXP_DURATION_MS          = 20000;
    static Chopstick[]   chopsticks               = new Chopstick[PhilosopherExperiment.PHILOSOPHER_NUM];
    static Philosopher[] philosophers             = new Philosopher[PhilosopherExperiment.PHILOSOPHER_NUM];

    public static void main(final String[] args) throws InterruptedException {
        for (int i = 0; i < PhilosopherExperiment.PHILOSOPHER_NUM; i++) {
            PhilosopherExperiment.chopsticks[i] = new Chopstick();
        }
        // first n-1 philosophers
        for (int i = 0; i < (PhilosopherExperiment.PHILOSOPHER_NUM - 1); i++) {
            PhilosopherExperiment.philosophers[i] = new Philosopher(PhilosopherExperiment.chopsticks[i],
                    PhilosopherExperiment.chopsticks[(i + 1) % PhilosopherExperiment.PHILOSOPHER_NUM]);
        }
        // nth philosophers: initialize with right+left instead of left+right
        PhilosopherExperiment.philosophers[PhilosopherExperiment.PHILOSOPHER_NUM - 1] = new Philosopher(
                PhilosopherExperiment.chopsticks[0],
                PhilosopherExperiment.chopsticks[PhilosopherExperiment.PHILOSOPHER_NUM - 1]);
        for (int i = 0; i < PhilosopherExperiment.PHILOSOPHER_NUM; i++) {
            PhilosopherExperiment.philosophers[i].start();
        }
        Thread.sleep(PhilosopherExperiment.EXP_DURATION_MS);
        for (int i = 0; i < PhilosopherExperiment.PHILOSOPHER_NUM; i++) {
            PhilosopherExperiment.philosophers[i].stopPhilosopher();
        }
    }
}
