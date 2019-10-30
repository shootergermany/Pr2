# Aufgabe: `Comparator<T>` verwenden und Objekte sortieren

## Lernziel

Verschiedene Sortierreihenfolgen durch die Implementierung von `Comparator<T>` realisieren.


## Umgebung

  * Eclipse


## Aufgabe

Sie finden in der Aufgabe bereits den Aufzählungstyp `SortOrder`, die Klasse `Sorter` und den JUnit-Test `TestSorter` vor. Schauen Sie sich die Klasse `Sorter` an und versuchen Sie anhand der JavaDoc und des bereits vorhandenen Codes zu verstehen, was die Klasse machen soll.

Lassen Sie den Test laufen. Sie werden feststellen, dass ein Testfall grün ist, obwohl in `Sorter` gar keine Implementierung vorhanden ist. Versuchen Sie herauszufinden, warum dies so ist.

Fügen Sie nun den fehlenden Code in die Klasse `Sorter` ein, und geben Sie entsprechende `Comparator`-Objekte zurück, sodass die Daten entsprechend dem Parameter `order` sortiert werden können. Dazu müssen Sie eine neue Klasse programmieren, die das Interface `Comparator` implementiert.

## Lösungsskizze

Die Klasse `Sorter` muss geändert werden und eine Klasse muss neu programmiert werden. Sie hat in der Musterlösung den Namen `MyComparator`, kann aber auch jeden anderen Namen erhalten. 

### `Sorter`

* Die Methode `stringComparator` soll als Factory-Methode arbeiten. Das bedeutet, dass sie ein neues Objekt zurückliefert. Im Fall hier ist das Objekt eine Instanz der neu zu programmierenden Klasse, die das `Comparator`-Interface implementiert.  
* Wie die meisten Factory-Methoden ist `stringComparator` static, also eine Klassenmethode, die nicht an einer Instanz, sondern an der Klasse `Sorter` direkt aufgerufen wird.
* Die Sortierrichtung, die als Parameter in die Methode `stringComparator` hineingeht, wird über den Konstruktor der neuen Klasse weitergegeben, damit der `Comparator` die richtige Sortierrichtung verwendet.

### `Comparator` 

* Das Interface `Comparator` ist ein generischer Typ. Der Typ-Parameter gibt an, welche Typen verglichen werden sollen. In unserem Fall bedeutet dies, dass das Interface `Comparator<String>` heißen muss.
* Durch die eingebaute Hilfe *Add unimplemented methods* in Eclipse (s. Musterlösung von `pr2.06.02-IteratorSchreiben_solution`), kann schnell der Rumpf für die fehlende Methode generiert werden:

	@Override
	public int compare(String o1, String o2) {
	    // TODO Auto-generated method stub
	    return 0;
	}
	
* Dabei sollen die beiden `String`-Objekte, die als Parameter `o1` und `o2` (wie "*object*") übergeben werden, miteinander verglichen werden. 
* Worin der Vergleich besteht, ist immer anwendungsabhängig. Im Fall von `String`-Objekten  soll die lexikalische Sortierung nach dem Alphabet verwendet werden. Im Gegensatz zu Aufgabe `pr2.06.05-Map_solution` ist nun aber die Richtung änderbar. Außerdem kann beeinflusst werden, ob sich Groß-/Kleinschreibung auf die Sortierung auswirken soll oder nicht.
* Damit die Sortierrichtung verwendet werden kann, wird ein Wert des Typs `SortOrder` benötigt. Da die Signatur der Methode `compare` durch das Interface `Comparator` festgelegt ist, kann dieser Wert hier nicht als Parameter übergeben werden. Das API ist so designt, da der Typ des Sortierrichtungswertes immer anwendungsspezifisch ist und hier nciht festgelegt werden könnte.
* Stattdessen wird die Sortierrichtung als Instanzvariable im `Comparator`-Objekt gespeichert, an dem die Methode `compare` aufgerufen wird.
* Die Richtung wird ausschließlich im Konstruktor gesetzt und kann danach nicht mehr geändert werden. Das `Comparator`-Objekt ist also *immutable*. Soll später in eine andere Richtung sortiert werden, muss eine neue Instanz dieser Klasse erzeugt werden und dabei im Konstruktor ein entsprechender Wert des Aufzählungstyps `SortOrder` übergeben werden. 
* Das Ergebnis von `compare` ist zwar ein `int`, es wird beim Rückgabewert aber nur einer von drei Fällen berücksichtigt:  

1. Ergebis ist `0`: `o1` und `o2` sind im Sinne der implementierten Rangfolge gleich. Es macht also keinen Unterschied, welches von beiden Elementen vor oder nach dem anderen kommt. 
2. Ergebnis ist positiv: `o1` ist größer als `o2` im Sinne der Sortierreihenfolge und soll hinter `o2` einsortiert werden. 
3. Ergebnis ist negativ: `o1` ist kleiner als `o2` im Sinne der Sortierreihenfolge und soll vor `o2` einsortiert werden. 

* Je nach Sortierkriterium wird die Reihenfolge des Vergleichs von `o1` und `o2` variiert. Außerdem werden die zu vergleichenden `String`-Objekte mit der Methode `toUpperCase()` vor dem Vergleich in Großbuchstaben "normalisiert", damit sich Groß-/Kleinschreibung nicht auswirkt oder nicht.
