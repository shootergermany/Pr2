package pr2.collections.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zählen von Worthäfigkeiten.
 */
public class WordCount {

    /**
     * Listet alle Worte in der Datei und deren Häufigkeit auf. Die
     * zurückgegebene Liste ist bereits nach der Häfuigkeit sortiert.
     *
     * @param filename Dateiname
     * @return die Liste der vorhandenen Wort
     * @throws IOException Fehler beim Dateizugriff.
     */
    private static List<WordFrequency> countWords(String filename)
            throws IOException {

        // Map mit dem Wort als Schlüssel und der Häufigkeit als Key
        Map<String, WordFrequency> frequencyMap = new HashMap<>();

        // Datei zum Lesen öffnen
        final var reader = new BufferedReader(new FileReader(filename));

        var line = ""; // aktuelle Zeile

        // Über die Zeilen der Datei iterieren
        while ((line = reader.readLine()) != null) {

            // Sonderzeichen entfernen und die Zeilen in Worte splitten
            line = line.toLowerCase();
            line = line.replaceAll("[\",.:;\\)'\\-\\!?]", "");

            final var words = line.toLowerCase().split("[,. ]");

            for (final var word : words) {
                var entry = frequencyMap.get(word);
                if (entry == null) {
                    entry = new WordFrequency(word, 0);
                    frequencyMap.put(word, entry);
                }
                entry.incrementFrequency();
            }
        }

        reader.close();

        List<WordFrequency> liste = new ArrayList<>(frequencyMap.values());
        Collections.sort(liste);

        return liste;
    }

    /**
     * Hauptmethode.
     *
     * @param args Kommandozeilen-Argumente.
     */
    public static void main(String[] args) {
        try {
            final var words = countWords("assets/kafka.txt");

            for (final var word : words) {
                System.out.println(word);
            }
        } catch (IOException e) {
            System.err.println("Probleme beim Dateizugriff: " + e);
        }
    }
}
