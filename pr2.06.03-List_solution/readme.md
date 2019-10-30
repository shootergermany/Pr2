# Aufgabe: `List<T>` und dessen Implementierungen

## Lernziel

Das Interface `List<T>` und eine passende Implementierung einsetzen.


## Umgebung

  * Eclipse


## Aufgabe

Im Projekt finden Sie die Klasse `WordCollector` mit deren Hilfe man die Wörter in einem Text analysieren kann. Nach Übergabe eines Dateinamens, wird die Datei eingelesen und die vorhandenen Wörter werden gesammelt. Am Ende wird die Liste der Wörter alphabetisch sortiert ausgegeben.

Leider wurde `WordCollector` von einem Entwickler geschrieben, der nichts von dem Collection-Framework wusste und daher alles mit Array realisiert hat. Hierdurch sind einige sehr komplexe Konstruktionen entstanden.

Lassen Sie das Programm laufen und betrachten Sie die Ausgabe. Schauen Sie sich die Klasse `WordCollector` an und versuchen Sie die Funktionsweise und die Implementierung genau zu verstehen. Dieser Schritt ist wichtig, um die im folgenden beschriebenen Änderungen an der Klasse vornehmen zu können, lassen Sie sich also Zeit für diesen Schritt.

Ändern Sie die Klasse nun so, dass anstatt Arrays `List<T>` und deren Implementierungen verwendet werden. Ändern Sie die Schnittstellen und Parameter der Methoden von `String[]` auf `List<String>`. Modifizieren Sie auch die Implementierung der Methoden und versuchen Sie Arrays wo immer möglich durch Listen zu ersetzen. Schauen Sie sich auch die JavaDoc der Klassen `java.util.Arrays` und `java.util.Collections` an, um möglicherweise sehr hilfreiche Methoden zu finden.

__Hinweis:__ Die Konstruktion `String[] words = line.toLowerCase().split("[,. ]");` können Sie nicht durch Listen ersetzen, da die `split`-Methode Arrays verwendet.

## Lösungsskizze
* Statt `String`-Arrays zwischen den Methoden `main`, `listWords`, `readFileAndSplitIntoWords` und `removeDuplicates` auszutauschen, ist es sinnvoll eine `List<String>`-Implementierung zu benutzen, da sie bereits eingebaute Funktionen hat, die bei der `String`-Array-Variante selbst programmiert werden müssen.
* In `readFileAndSplitIntoWords` wird eine Liste erzeugt. Konkret ist es die Implementierung `LinkedList<String>`, die mit dem Default-Konstruktor erzeugt wird. Als statischer Typ wird die lokale Variable `wordList` aber mit dem Typ des Interfaces `List<String>` deklariert. Der grund dafür ist, dass zwischen den Methoden `main`, `listWords`, `readFileAndSplitIntoWords` und `removeDuplicates` in der Lösung statt `String`-Arrays `List<String>` ausgetaischt werden. Vom Design her ist das sinnvoll, da man sich hier nicht auf die Implementierung festlegt, die so später leichter geändert werden kann.
* Der Aufruf von `line.toLowerCase().split("[,. ]")` liefert ein Array von `String`. Zeile für Zeile erhält man also ein Array. In der ursprünglichen Variante werden diese Arrays mit einer Schleife in einen Puffer übertragen, der dann zum Resultat-Array kopiert wird. Dafür sind ca. 10 Zeilen Java-Code erforderlich, den man erstmal verstehen muss. Wird mit `List` gearbeitet, liefert die Implementierung des Interface'  bereits eine Funktion, die genau dies leistet, so dass nur noch das `String`-Array nur noch durch die Methode `Arrays.asList` in eine Liste konvertiert werden muss, die durch die Methode `addAll` sehr einfach an die Ergebnisliste angehangen werden kann.   
* Das Entfernen von Duplikaten fällt bei `List` viel leichter als in der ursprünglichen Variante: Beim Array werden Doubletten zuerst dadurch markiert, dass die Elemente des Arrays auf `null` gesetzt werden und dann in einem zweiten Schritt ein neues Array erzeugt wird, in das alle Elemente kopiert werden, die nicht `null` sind. Bei der `List` braucht dafür nur die Methode `remove` aufgerufen werden, der der Index des zu entfernenen Elements übergeben wird. 
* Bei der Implementierung `LinkedList` ist das Entfernen von Elementen sehr effizient und hat einen viel geringeren Aufwand. Das Anhängen der Teil-Listen, die aus dem *Splitting*  der Zeilen resultieren, ist bei `LinkedList` auch wenig aufwändig.
  