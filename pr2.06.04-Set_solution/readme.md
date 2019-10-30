# Aufgabe: `Set<T>` und dessen Implementierungen 

## Lernziel

Das Interface `Set<T>` und eine passende Implementierung einsetzen.


## Umgebung

  * Eclipse


## Aufgabe

Im Projekt finden Sie die Klasse `WordCollector` mit deren Hilfe man die Wörter in einem Text analysieren kann. Nach Übergabe eines Dateinamens, wird die Datei eingelesen und die vorhandenen Wörter werden gesammelt. Am Ende wird die Liste der Wörter alphabetisch sortiert ausgegeben.

Leider wurde `WordCollector` von einem Entwickler geschrieben, der nichts von dem Collection-Framework wusste und daher alles mit Array realisiert hat. Hierdurch sind einige sehr komplexe Konstruktionen entstanden.

Lassen Sie das Programm laufen und betrachten Sie die Ausgabe. Schauen Sie sich die Klasse `WordCollector` an und versuchen Sie die Funktionsweise und die Implementierung genau zu verstehen. Dieser Schritt ist wichtig, um die im folgenden beschriebenen Änderungen an der Klasse vornehmen zu können, lassen Sie sich also Zeit für diesen Schritt.

Ändern Sie die Klasse nun so, dass anstatt Arrays `Set<T>` und deren Implementierungen verwendet werden. Ändern Sie die Schnittstellen und Parameter der Methoden von `String[]` auf `Set<String>` bzw. `List<String>`. Modifizieren Sie auch die Implementierung der Methoden und versuchen Sie Arrays wo immer möglich durch Sets und Listen zu ersetzen. Schauen Sie sich auch die JavaDoc der Klassen `java.util.Arrays` und `java.util.Collections` an, um möglicherweise sehr hilfreiche Methoden zu finden.

__Hinweis:__ Die Konstruktion `String[] words = line.toLowerCase().split("[,. ]");` können Sie nicht durch Listen ersetzen, dass die `split`-Methode Arrays verwendet.

## Lösungsskizze

* Verglichen mit der Lösungsskizze von `pr2.06.03-List_solution` ist hier die Idee zur Repräsentation der Zwischenergebnisse beim Übergang von `readFileAndSplitIntoWords` zu `listWords` ein `Set<String>` zu benutzen. Eine wichtige Eigenschaft von Mengen, die durch den Typ `Set` im Java Collection Framework umgesetzt werden, ist, dass dasselbe Objekt nicht mehrfach in dieser Datenstruktur gespeichert werden kann. Daher erspart man sich die nachgelagerte Suche nach Doubletten.
* Dabei muss jedoch berücksichtigt werden, dass Strings speziell behandelt werden: Zwei String-Literale desselben Inhalts verweisen auf dasselbe String-Objekt:

	var s1 = "abc";
	var s2 = "abc";
	if (s1 == s2) {
	    System.out.println("s1 und s2 verweisen auf dasselbe Objekt");
	} else {
	    System.out.println("s1 und s2 verweisen auf unterschiedliche Objekte");
	}
	
* Dies hat als Ausgabe: `s1 und s2 verweisen auf dasselbe Objekt` (`==` prüft zwei Objekte auf Objekt-Identität, nicht auf inhaltliche Gleichheit wie bei Werten von primitiven Typen). Da zwei inhaltlich gleiche eingelesene Wörter also als ein und dasselbe `String`-Objekt repräsentiert werden, kann man sich die Eigenschaft von `Set<String>` zunutze machen, dass die Speicherung von Doubletten die Menge nicht ändert. 