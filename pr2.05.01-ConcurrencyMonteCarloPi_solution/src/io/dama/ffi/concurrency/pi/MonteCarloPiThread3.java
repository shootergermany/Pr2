package io.dama.ffi.concurrency.pi;

import java.util.Random;

/**
 * Berechnung von pi duch Monte Carlo Verfahren: Vergleich der Anzahl von
 * zufällig gewähten Punkten innerhalb eines Viertelkreises (Radius r = 1) bzw.
 * innerhalb eines Quadrates (Kantenlänge 1) analog zu Fläche eines
 * Viertelkreises (pi * r^2 / 4) mit der Fläche des Quadrates (1 = 1 * 1).
 *
 * Nebenläufige Berechnung mit PARALLELISM x Thread mit Instanzvariablen, auf
 * die nur getrennt zugegriffen wird, weshalb der Zugriff nicht synchronisiert
 * werden braucht
 *
 * @author Sandro Leuchter
 *
 */
class MonteCarloPiThread3 extends Thread {

	int in = 0;
	int out = 0;
	final Random r = new Random();

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
		final var threads = new MonteCarloPiThread3[Const.PARALLELISM];
		for (var i = 0; i < Const.PARALLELISM; i++) {
			threads[i] = new MonteCarloPiThread3();
			threads[i].start();
		}
		var in = 0;
		var out = 0;
		for (final var t : threads) {
			t.join();
			in += t.in;
			out += t.out;
		}
		System.out.printf("%f (%d ms, %d Threads, %,d Punkte)\n", //
				4.0 * in / (in + out), //
				System.currentTimeMillis() - now, //
				Const.PARALLELISM, //
				in + out);
	}

}
