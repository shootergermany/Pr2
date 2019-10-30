package io.dama.ffi.concurrency.pi;

import java.util.Random;

/**
 * Berechnung von pi duch Monte Carlo Verfahren: Vergleich der Anzahl von
 * zufällig gewähten Punkten innerhalb eines Viertelkreises (Radius r = 1) bzw.
 * innerhalb eines Quadrates (Kantenlänge 1) analog zu Fläche eines
 * Viertelkreises (pi * r^2 / 4) mit der Fläche des Quadrates (1 = 1 * 1).
 *
 * Nebenläufige Berechnung mit PARALLELISM x Thread mit globalen Variablen und
 * getrennten Lock-Objekten
 *
 * @author Sandro Leuchter
 *
 */
class MonteCarloPiThread1 extends Thread {

	static int in = 0;
	static int out = 0;

	final Random r = new Random();

	/*
	 * synchronized auf einem Integer-Objekt oder einem Wert eines primitiven Typs
	 * klappt nicht, daher zwei neue Objekte, die nur als Lock dienen:
	 */
	static Object lockIn = new Object();
	static Object lockOut = new Object();

	static double getPi() {
		return 4.0 * in / (in + out);
	}

	@Override
	public void run() {
		for (var i = 0; i < Const.CYCLES; i++) {
			final var x = this.r.nextDouble();
			final var y = this.r.nextDouble();
			if (Math.sqrt(x * x + y * y) <= 1.0) {
				synchronized (MonteCarloPiThread1.lockIn) {
					MonteCarloPiThread1.in++;
				}
			} else {
				synchronized (MonteCarloPiThread1.lockOut) {
					MonteCarloPiThread1.out++;
				}
			}
		}
	}

	/**
	 * main-Methode
	 *
	 * @param args Kommandozeilenparameter (nicht benutzt)
	 * @throws InterruptedException
	 */

	public static void main(final String[] args) throws InterruptedException {
		final var now = System.currentTimeMillis();
		final var threads = new MonteCarloPiThread1[Const.PARALLELISM];
		for (var i = 0; i < Const.PARALLELISM; i++) {
			threads[i] = new MonteCarloPiThread1();
			threads[i].start();
		}
		for (final var t : threads) {
			t.join();
		}
		System.out.printf("%f (%d ms, %d Threads, %,d Punkte)\n", //
				getPi(), //
				System.currentTimeMillis() - now, //
				Const.PARALLELISM, //
				MonteCarloPiThread1.in + MonteCarloPiThread1.out);
	}

}
