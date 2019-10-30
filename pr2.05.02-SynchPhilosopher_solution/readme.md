# Thread Steuerung #

## Dinnierende Philosophen (Lösung mit Deadlock-Gefahr!) ##

Die _dining philosophers_ sind ein Standardbeispiel für ein nebenläufiges System, in dem es zu Verklemmungen kommen kann.

Der Lebenszyklus eines Philosophen ist:
1. denken
2. linkes Essstäbchen greifen
3. rechtes Essstäbchen greifen
4. essen
5. rechtes Essstäbchen zurücklegen
6. linkes Essstäbchen zurücklegen
7. weiter bei 1.

Die Essstäbchen sind eine geteilte Ressource. Ein Stäbchen darf nur jeweils von einem Philosophen benutzt werden. Wollen andere Philosophen ein benutztes Stäbchen verwenden, müssen sie aufgehalten werden, bis das Essstäbchen wieder frei ist. Eine Möglichkeit, dies zu erreichen ist die Modellierung dieses gegenseitigen Ausschlusses durch ``synchronized``-Blöcke.

In diesem Beispiel werden die Essstäbchen als Objekte modelliert. Die Klasse Chopstick ist leer. Im Moment hat ein Essstäbchen noch keine über ``Object`` hinausgehenden Eigenschaften oder Methoden. Man kann aber Instanzen von ``Chopstick`` als Schlossvariablen benutzen.  

## Aufgaben ##

* Analysieren Sie den Quellcode. Machen Sie sich klar, welche Objekte in ``PhilosopherExperiment`` erzeugt werden und wie sie zusammenhängen. Wie können Sie erkennen, ob es zu einer Verklemmung gekommen ist?
* Machen Sie eine Reihe von Experimenten, indem Sie ``PhilosopherExperiment`` laufen lassen. Falls es zu einer Verklemmung kommt: Dokumentieren Sie den Ablauf, der zur Verklemmung geführt hat. Falls es zu keinem Deadlock gekommen ist, analysieren Sie, ob es "gerecht" zwischen den Philosophen zuging und dokumentieren Sie das Ergebnis. 
* Modifizieren Sie die Parameter 
  * ``PhilosopherExperiment.PHILOSOPHER_NUM``, 
  * ``PhilosopherExperiment.MAX_TAKING_TIME_MS``, 
  * ``PhilosopherExperiment.EXP_DURATION_MS`` und
  * ``PhilosopherExperiment.MAX_THINKING_DURATION_MS``
  und wiederholen Sie die Experimente. Wie können Sie die Wahrscheinlichkeit eines Deadlocks vergrößern?