	# Aufgabe: `Map<T>` verwenden

## Lernziel

Das Interface `Map<T>` und seine Implementierungen verwenden, um Daten effizient zu verarbeiten.


## Umgebung

  * Eclipse


## Aufgabe

In dieser Aufgabe geht es darum, die Häufigkeit von Wörtern in einem Text zu analysieren. Ein grundlegendes Gerüst für das Programm ist auch bereits vorgegeben, damit Sie sich auf das Wesentliche konzentrieren können.

Betrachten Sie die Klasse `WordCount`. In dieser fehlen noch einige Elemente, damit das Programm korrekt funktionieren kann.

Die Klasse `WordFrequency` muss das Sortieren unterstützen. Implementieren Sie daher das Interface `Comparable`.

Fügen Sie in der Klasse `WordCount` an den durch TODO-Kommentare markierten Stellen Code hinzu, der mit Hilfe einer Map die Worthäufigkeiten bestimmt.

Lassen Sie das Programm laufen. Es sollte eine Ausgabe wie folgt erzeugen:

    er: 80
    sie: 66
    und: 64
    die: 59
    der: 51
    zu: 46
    es: 45
    nicht: 41
    das: 38
    in: 35
    ...

## Lösungsskizze

### Vergleichbar machen von `WordFrequency`

* Die Klasse `WordFrequency` muss das Interface `Comparable` implementieren, damit Datenstrukturen, die `WordFrequency` als Elemente speichern, Sortierbar werden. 
* Das Interface  `Comparable` ist ein generischer Typ. Der Typ-Parameter gibt an, welche Typen verglichen werden sollen. In unserem Fall bedeutet dies, dass das Interface `Comparable<WordFrequency>` heißen muss.
* Durch die eingebaute Hilfe *Add unimplemented methods* in Eclipse (s. Musterlösung von `pr2.06.02-IteratorSchreiben_solution`), kann schnell der Rumpf für die fehlende Methode generiert werden:

	@Override
	public int compareTo(WordFrequency o) {
	    // TODO Auto-generated method stub
	    return 0;
	}
* Dabei soll das "andere" `WordFrequency`-Objekt, das als Parameter `o` (wie "*other*") übergeben wird, mit dem `WordFrequency`-Objekt, an dem die Methode `compareTo` aufgerufen wird (also `this`), verglichen werden. 
* Worin der Vergleich besteht, ist immer anwendungsabhängig. Im Fall der Tupel aus Word (`this.word`) und numerische Worthäufigkeit (`this.frequency`) soll ausschließlich nach der Worthäufigkeit verglichen und sortiert werden, also fließt nur `this.frequency` im Vergleich zu `o.frequency` ein.
* Das Ergebnis von `compareTo` ist zwar ein `int`, es wird beim Rückgabewert aber nur einer von drei Fällen berücksichtigt:  

1. Ergebis ist `0`: `this` und `o` sind im Sinne der implementierten Rangfolge gleich. Es macht also keinen Unterschied, welches von beiden Elementen vor oder nach dem anderen kommt. Im Fall der Worthäufigkeit trifft dies zu, wenn `this.frequency == o.frequency` ist.
2. Ergebnis ist positiv: `this` ist größer im Sinne der Sortierreihenfolge und soll hinter `o` einsortiert werden. 
3. Ergebnis ist negativ: `this` ist kleiner im Sinne der Sortierreihenfolge und soll vor `o` einsortiert werden. 

* Im Fall der Reihenfolge von `WordFrequency` soll die Worthäufigkeit absteigend sortiert werden. Daher ist das Ergebnis von `compareTo(o) == o.frequency - this.frequency`.

### Map deklarieren in `WordCount`

* Generell ist es bei der Verwendung des ollection Frameworks sinnvoll als statischen Typ mit dem jeweiligen Interface von der konkreten Implementierung zu abstrahieren, so dass später die Implementierung leicht ausgetauscht werden kann. 
* Die `Map` soll den Typ `String` (als Wort) als Schlüssel verwenden um `WordFrequency`-Tupel zu speichern. 
* Als Implementierung wird `HashMap` verwendet.
* Anfangs ist die `Map` leer.

	Map<String, WordFrequency> frequencyMap = new HashMap<>();

* Die Redundanz, dass die Wörter sowohl Schlüssel in der `Map` sind, als auch zusätzlich im `WordFrequency`-Tupel gespeichert sind, macht später einige Schritte leichter.;
### Worthäufigkeiten aus der Map extrahieren und sortieren in `WordCount`

* Die Schleife, mit der jeweils über die in Wörter zerlegte Zeile iteriert wird, ist bereits in der Aufgabe vorgegeben: 

	for (final var word : words) { /* ... */ }
* Im Rumpf dieser Schleife wird Wort für Wort gearbeitet.
* Zuerst wird geprüft, ob das Wort (`word`) schon früher gelesen wurde. In dem Fall gibt es einen Eintrag ( `entry`) in der `Map` zum Schlüssel `word`, der nicht `null` ist. In dem Fall wird in dem abgerufenen `WordFrequency`-Tupel die Häufigkeit mit der Methode `incrementFrequency()` um `1` hochgezählt.  

	var entry = frequencyMap.get(word);
	if (entry == null) {/* ... */}
	entry.incrementFrequency();
* Falls es aber noch keinen Eintrag zu dem Wort gibt, muss er erst angelegt werden. Das neue Tupel muss in der Map zum Schlüssel `word` eingetragen werden, damit es später dort gefunden werden kann, wenn das Wort erneut auftaucht. Ein neues Tupel muss die Worthäufigkeit auf `1` gesetzt bekommen, da das Wort gerade gelesen wurde. Dies wird hier dadurch erreicht, dass das neue `WordFrequency`-Tupel die Frequenz `0` bekommt, die sofort nach Verlassen der Fallunterscheidung (wie ein bereits existierendes `WordFrequqncy`-Tupel) um `1` hochgezählt wird. 

	entry = new WordFrequency(word, 0);
	frequencyMap.put(word, entry);

### Worthäufigkeiten zurückgeben in `WordCount`

* Insgesamt soll eine `List<WordFrequency>` zurückgegeben werden.
* Dazu werden alle `WordFrequency`-Tupel in der `Map` mit der Methode `values()` als `Collection<WordFrequency>` extrahiert. 
* Daraus wird mit dem Konstruktor von `ArrayList`, der eine `Collection` als Parameter erwartet, eine `List<WordFrequency>`.
* Da `WordFrequency` das `Comparable<WordFrequency>`-Interface implementiert, kann die `ArrayList` in der lokalen Variable `list` mit `Collections.sort(liste)` nach dem vorgegebenen und implementierten Kriterium (absteigend nach numerischer Worthäufigkeit) sortiert werden.;* Die so sortierte `List<WordFrequency>` wird zurückgegeben.
