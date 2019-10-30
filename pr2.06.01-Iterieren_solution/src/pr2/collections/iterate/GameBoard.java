package pr2.collections.iterate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.sprite.Sprite;

/**
 * Spielfeld.
 */
@SuppressWarnings("serial")
public class GameBoard extends Board {

    /** Anzahl der Münzen. */
    private static final int NUM_COINS = 1000;

    /** Münzgenerator. */
    private CoinGenerator generator;

    /** Münzen auf dem Spielfeld. */
    private Sprite[] coins = new Sprite[NUM_COINS];

    /**
     * Erzeugt ein neues Board.
     */
    public GameBoard() {
        // neues Spielfeld anlegen
        super(10, new Dimension(600, 600), Color.BLACK);

        this.generator = new CoinGenerator(this, NUM_COINS);

        var count = 0;
        Iterator<Sprite> it = this.generator.iterator();

        while (it.hasNext()) {
            this.coins[count++] = it.next();
        }
    }

    /**
     * Spielfeld neu zeichnen. Wird vom Framework aufgerufen.
     */
    @Override
    public synchronized void drawGame(Graphics g) {
        for (final var sprite : this.coins) {
            sprite.draw(g);
        }
    }

    /**
     * Spielsituation updaten. Wird vom Framework aufgerufen.
     */
    @Override
    public boolean updateGame() {
        return true;
    }
}
