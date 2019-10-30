# Aufgabe: Mit Hilfe eines Iterators über Daten iterieren

## Lernziel

Den Iterator einer vorhandenen Klasse verwenden, um Daten auszulesen.


## Umgebung

  * Eclipse


## Aufgabe

Bei dieser Aufgabe werden Sie mit einem `Iterator` über eine Menge von Objekten iterieren. Die Besonderheit besteht darin, dass der Iterator keine feste Menge von Objekten liefert, sondern diese zufällig generiert. Dies ist zwar ungewöhnlich, soll aber zeigen, dass beim Iterieren nicht zwangsläufig bei jedem Durchlauf dieselben Daten kommen.

Betrachten Sie die Klasse `CoinGenerator` und versuchen Sie deren Funktionsweise zu verstehen. Schauen Sie sich insbesondere an, wie der Iterator in dieser Klasse implementiert ist.

Modifizieren Sie nun die Klasse `GameBoard` so, dass sie den `CoinGenerator` verwendet, um Münzen zu erzeugen und diese dann auf dem Bildschirm anzuzeigen. Überlegen Sie sich, ob Sie die erzeugten Münzen zwischenspeichern müssen oder ob Sie den Iterator direkt in der `drawGame`-Methode verwenden können.

Implementieren Sie Ihre Lösung und lassen Sie diese laufen, um sie zu testen.

## Lösungsskizze

* Die Klasse `GameBoard` muss geändert werden. In den anderen beiden Klassen brauchen keine Änderungen vorgenommen werden.

### Zwischenspeichern (Instanzvariable in `GameBoard`)

* Die Münzen müssen zwischengespeichert werden, da in jeder `Iterator` eine andere (zufällige= Sequenz von Münzen generiert.
* Die Zwischenspeicherung erfolgt in einer Instanzvariable in `GameBoard` als Array vom Typ `Sprite`, da dies der Typ der Objekte ist, die der `Iterator` beim Aufruf von `next()` liefert. Die Instanzvariable heißt in der Musterlösung `this.coins`:

	private Sprite[] coins = new Sprite[NUM_COINS];

* Alternativ kann auch eine `Collection` zur Speicherung verwendet werden. Da die Reihenfolge der Münzen entscheidend ist und sich nicht ändern darf, muss eine Implementierung des Interface' `List` verwendet werden, bspw. `ArrayList`:

	private List<Sprite> coins = new ArrayList<>();

### Münzen generieren (im Konstruktor von `GameBoard)

* Von der Instanzvariable `this.generator` muss der `Iterator`geholt werden. Da deren Typ `CoinGenerator` ist und deren Klasse das Interface `Iterable<Sprite>` implementiert, kann an `this.generator` die Methode `iterator()` aufgerufen werden. Der resultirende `Iterator<Sprite>` liefert `Sprite`-Objekte, wenn die `next()`-Methode an ihm aufgerufen wird. Das kann man solange machen, wie seine `hasNext()`-Methode `true` liefert. 
* Da die so generierten Münzen dem Array `this.coins` hinzugefügt werden soll, wird eine Indexvariable benötigt, die mit jedem Schritt um 1 hochgezählt wird.

	var count = 0;
	Iterator<Sprite> it = this.generator.iterator();
	while (it.hasNext()) {
        this.coins[count++] = it.next();
    } 

* Eine `for`-Schleife, die von `0` bis `NUM_COINS - 1` läuft, wäre keine passende Lösung: Die Verantwortung für die Anzahl der Münzen, die generiert werden soll, liegt bei der Klasse CoinGenerator. In deren Konstruktor wird die Konstante `NUM_COINS` zwar als obere Grenze für die Anzhal der zu generierenden Münzen gesetzt, aber innerhalb der Implementierung könnte auch ein anderer Mechnsimus ablaufen, der eine andere Zahl von Münzen generiert und der sich auch in zukünftigen Implementierungen ändern könnte.
* Falls eine `List` zur Speicherung der Münzen verwendet wurde, sieht es etwas einfacher aus:

	Iterator<Sprite> it = this.generator.iterator();
	while (it.hasNext()) {
        this.coins.add(it.next());
    } 

### Münzen zeichnen (in der Methode `drawGame`)

* Am besten kann man mit einer *for-each*-Schleife über alle Elemente des Arrays oder der `List` iterieren, das die Münzen zwischenspeichert:

	for (final Sprite sprite : this.coins) {
    	    sprite.draw(g);
	}

* Seit Java 10 gibt es das `var`-Schlüsselwort, mit dem der Typ der Laufvariable aus der rechten Seite geschlossen wird:

	for (final var sprite : this.coins) {
    	    sprite.draw(g);
	}
	
* Intuitiv ist vielleicht nicht klar, warum die lokale Variable sprite `final` ist, da ihr Wert sich ja in jedem Schleifendurchlauf ändert. Da die *for-each*-Schleife vom Compiler aber in das folgende Konstrukt übersetzt wird (abgesehen vom Namen der lokalen Variable `i`, der ein garantiert neuer und noch nicht benutzter temporärer interner Name gegeben wird), sieht man, dass `sprite` tatsächlich `final` ist. Der Grund ist, dass der Scope der lokalen Variable `sprite` nur der Rumpf jeweils eines Schleifenduchlaufs ist. 

	for (final var i = 0; i < this.coins.length; i++) {
	    final var sprite = array[i];
	    sprite.draw(g);
	}

* Dasselbe gilt, falls `this.coins` eine `List`-Implementierung ist:

	var it = this.coins.iterator();
	while (it.hasNext()) {
		final var sprite = it.next();
	    sprite.draw(g);
	}

* Die Notation als *for-each*-Schleife ist der `for`-Schleife vorzuziehen, da es weniger Fehlermöglichkeiten beim Schreiben und Verstehen gibt. Während man bei der `for`-Schleife über Start- und Endindex sowie über die Bedingung für den Endindex nachdenken muss, ist die Semantik der *for-each*-Schleife sofort klar: Es soll über alle Elemente im Array iteriert werden.  


