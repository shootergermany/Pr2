package io.dama.ffi.concurrency.pi;

/**
 * Konstanten
 *
 * @author Sandro Leuchter
 *
 */
public class Const {

	/**
	 * erwünschte Anzahl von Threads für die Varianten mit Nebenläufigkeit
	 */
	final static int PARALLELISM = Runtime.getRuntime().availableProcessors();

	/**
	 * erwünschte Anzahl von zu ziehenden Punkten
	 */
	final static int TOTAL_CYCLES = 10000000;

	/**
	 * Anzahl von innerhalb eines Threads zu ziehenden Punkten
	 */
	final static int CYCLES = Const.TOTAL_CYCLES / Const.PARALLELISM;

}
