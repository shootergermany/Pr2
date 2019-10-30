/**
 *
 */
package io.dama.ffi.concurrency.pi;

import java.util.Random;

/**
 * Berechnung von pi duch Monte Carlo Verfahren: Vergleich der Anzahl von
 * zufällig gewähten Punkten innerhalb eines Viertelkreises (Radius r = 1) bzw.
 * innerhalb eines Quadrates (Kantenlänge 1) analog zu Fläche eines
 * Viertelkreises (pi * r^2 / 4) mit der Fläche des Quadrates (1 = 1 * 1).
 *
 * Nebenläufige Berechnung mit PARALLELISM x Runnable mit Instanzvariablen, auf
 * die während des Laufs jeweils nur aus einem Thread zugegriffen wird
 *
 * @author Sandro Leuchter
 *
 */
class MonteCarloPiRunnable implements Runnable {

	private int in = 0;
	private int out = 0;
	private final Random r = new Random();

	@Override
	public void run() {
		for (var i = 0; i < Const.CYCLES; i++) {
			final var x = this.r.nextDouble();
			final var y = this.r.nextDouble();
			if (Math.sqrt(x * x + y * y) <= 1.0) {
				this.in++;
			} else {
				this.out++;
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
		final var mcs = new MonteCarloPiRunnable[Const.PARALLELISM];
		final var threads = new Thread[Const.PARALLELISM];
		var in = 0;
		var out = 0;
		for (var i = 0; i < Const.PARALLELISM; i++) {
			mcs[i] = new MonteCarloPiRunnable();
			threads[i] = new Thread(mcs[i]);
			threads[i].start();
		}
		for (final var t : threads) {
			t.join();
		}
		for (final var mc : mcs) {
			in += mc.in;
			out += mc.out;
		}
		System.out.printf("%f (%d ms, %d Threads, %,d Punkte)\n", //
				4.0 * in / (in + out), //
				System.currentTimeMillis() - now, //
				Const.PARALLELISM, //
				in + out);
	}

}
