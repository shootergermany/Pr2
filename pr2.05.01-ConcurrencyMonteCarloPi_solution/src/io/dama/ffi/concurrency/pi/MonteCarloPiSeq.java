package io.dama.ffi.concurrency.pi;

import java.util.Random;

/**
 * Berechnung von pi duch Monte Carlo Verfahren: Vergleich der Anzahl von
 * zufällig gewähten Punkten innerhalb eines Viertelkreises (Radius r = 1) bzw.
 * innerhalb eines Quadrates (Kantenlänge 1) analog zu Fläche eines
 * Viertelkreises (pi * r^2 / 4) mit der Fläche des Quadrates (1 = 1 * 1).
 *
 * Sequenzielle Berechnung (im Main-Thread)
 *
 * @author Sandro Leuchter
 *
 */
class MonteCarloPiSeq {

	static int in = 0;
	static int out = 0;
	final static Random r = new Random();

	static double getPi() {
		return 4.0 * in / (in + out);
	}

	public static void run() {
		for (var i = 0; i < Const.TOTAL_CYCLES; i++) {
			final var x = r.nextDouble();
			final var y = r.nextDouble();
			if (Math.sqrt(x * x + y * y) <= 1.0) {
				in++;
			} else {
				out++;
			}
		}
	}

	/**
	 * main-Methode
	 *
	 * @param args Kommandozeilenparameter (nicht benutzt)
	 */
	public static void main(final String[] args) {
		final var now = System.currentTimeMillis();
		run();
		System.out.printf("%f (%d ms, 1 Thread, %,d Punkte)\n", //
				getPi(), //
				System.currentTimeMillis() - now, //
				in + out);
	}

}
