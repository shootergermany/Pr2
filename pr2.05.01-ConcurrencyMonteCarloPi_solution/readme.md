# Aufgabe: Monte-Carlo-Algorithmus zur Annäherung von π

## Lernziele

Eine Schleife in mehrere nebenläufige aufteilen und konkurrierenden Zugriff auf Speicher kontrollieren. Auswirkungen der Thread-Sicherheit von Zufallszahlengeneratoren, speziell bei Monte-Carlo-Algorithmen. 


## Umgebung

  * Eclipse

## Monte-Carlo-Algorithmus zur Annäherung von π

Monte-Carlo-Algorithmen sind randomisierte Algorithmen, die durch fortgesetzte Wahl von Zufallszahlen versuchen, ein Ergebnis anzunähern. Es gibt bei ihnen aber keine Garantie für Korrektheit.

Ein bekannter Monte-Carlo-Algorithmus zur Annäherung von π basiert darauf, dass zufällig Punkte `(x y)` im Koordinatenbereich von `(0, 0)` bis `(1, 1)` gezogen werden. Für jeden gezogenen Punkt wird vermerkt, ob er innerhalb eines Einheitsviertelkreises (Radius=1) liegt. Durch Anwendung des Satzes des Pythagoras, kann dies geprüft werden.

1. Im folgenden Fall wurde zufällig ein Punkt mit den Koordinaten `x` und `y` gewählt, so dass gilt: `sqrt(x*x + y*y) = 1`. Der Punkt liegt also zufällig genau auf dem Kreisbogen.<br/>
![sqrt(x*x + y*y) = 1](doc/on.png "sqrt(x*x + y*y) = 1") 

2. Im nächsten Fall wurde zufällig ein Punkt mit den Koordinaten `x` und `y` gewählt, so dass gilt: `sqrt(x*x + y*y) < 1`. Der Punkt liegt also zufällig innerhalb des Kreisbogens.<br/>
![sqrt(x*x + y*y) < 1](doc/in.png "sqrt(x*x + y*y) < 1") 

3. Im letzten Fall wurde zufällig ein Punkt mit den Koordinaten `x` und `y` gewählt, so dass gilt: `sqrt(x*x + y*y) > 1`. Der Punkt liegt also zufällig außerhalb des Kreises.<br/>
![sqrt(x*x + y*y) > 1](doc/out.png "sqrt(x*x + y*y) > 1") 

Werden sehr viele Punkte gezogen, die sich innerhalb der Koordinaten `(0, 0)` und `(1, 1)` gleichmäßig verteilen, sollte sich für das Verhältnis der Anzahl der Punkte innerhalb des Kreises (einschließlich auf dem Kreisbogen) zu allen innerhalb des Quadrats das Verhältnis der Fläche des Viertelkreises (Radius 1) zur Fläche des Quadrats (Kantenlänge 1) ergeben.


Anzahl der Punkte innerhalb und auf dem Kreis (`sqrt(x*x + y*y) <= 1`) / Anzahl aller Punkte = (π r^2 / 4) / r^2

Da `r=1` kann π angenähert werden als `pi = 4.0 * in / (in + out)`, wobei `in` die Anzahl der Punkte innerhalb des Kreises ist, für die gilt `sqrt(x*x + y*y) <= 1` und `out` die restlichen, die außerhalb des Kreises liegen. 

Falls die zufällig gezogenen Punkte gleichmäßig über die Fläche verteilt sind, nähert sich `pi` der [Konstante π](https://3.141592653589793238462643383279502884197169399375105820974944592.eu/)
 an. Es liegt in der Natur der Sache von Monte-Carlo-Algorithmen, dass dies nicht in jedem Fall zu einem korrekten Ergebnis führt. Je mehr Punkte gezogen werden, desto sollte im Mittel der Wert von π angenähert werden. Es ist also sinnvoll, so viele Punkte wie möglich zu ziehen. Im Package `io.dama.ffi.concurrency.pi`  ist die Klasse `Const` enthalten, die drei Konstanten beinhaltet.

* `TOTAL_CYCLES` gibt an, wieviele Punkte insgesamt gezogen werden sollen. Das ist sozusagen das Rechenziel. 
* `PARALLELISM` gibt an, wieviele Threads neben dem Main-Thread gestartet werden sollen, um das Rechenziel zu erreichen. Die beiden Programme `MonteCarloPiSeq` und `MonteCarloPiSeqThreadLocal` verwenden nur den Main-Thread und arbeiten sequentiell statt nebenläufig.
* `CYCLES` ist `TOTAL_CYCLES / PARALLELISM`. Das ist das *anteilige Rechenziel* in jedem einzelnen Thread, wenn mit Nebenläufigkeit gearbeitet wird. 

## Aufgaben

* Bauen Sie das Programm `MonteCarloPiSeq` so um, dass die `TOTAL_CYCLES` Ziehungen der Zufallszahlen `x` und `y` auf `PARALLELISM` Threads verteilt werden.
* Ist die dadurch errechnete Näherung von π mit der des sequenziellen Programms vergleichbar?
* Welche Geschwindigkeitssteigerung können Sie bei der Berechnung mit Parallelisierung erreichen?


## Lösungsskizze
* Der *normale*  Zufallszahlengenerator `java.util.Random` ist Thread-sicher programmiert. D.h. auf ein `Random`-Objekt kann aus mehreren Threads heraus zugegriffen werden, ohne dass man selber Vorkehrungen treffen müsste. Verwendet man einen Zufallszahlengenerator aber exklusiv in einem Thread, kann eine Variante verwendet werden, die nicht Thread-sicher ist. Sie ist viel schneller. Die Thread-lokale Variante des Zufallszahlengenerators kann dafür verwendet werden. In `MonteCarloPiSeqThreadLocal` wird diese Variante benutzt, ohne dass mehrere Threads gestartet werden. Der Vergleich von 0) zu 1) im Experiment unten zeigt, dass diese Variante fast 3x schneller ist.  
* Die Gesamtanzahl der Punkte im Kreis und außerhalb des Kreises müssen gespeichert werden. Sollte aus mehreren Threads auf dieselbe (globale oder Instanz-) Variable mit `++` o.ä. zugegriffen werden, ist das eine *Race-Condition* . Der Zugriff muss z.B. mit `synchronized` koordiniert erfolgen, sonst kommt es dazu, dass Punkte verloren gehen und die Näherung von π fehlerhaft ist. Allerdings benötigt die Verwaltung der Monitore Zeit. In den Fällen 2), 3) und 4) im Experiment unten wird synchronized verwendet. Im Vergleich zu den anderen Lösungen 5), 6) und 7), in denen Thread-weise getrennte Variablen zum Akkumulieren der Punkte verwendet werden, die erst nach dem Ende der nebenläufigen Berechnung ausgelesen werden, ergibt sich ein ca. 10facher Aufwand.
* Dabei muss man unterscheiden wie der Zugriff auf die zwei Variablen koordiniert wird: Wird derselbe Monitor für beide Variablen benutzt, wird der Zugriff auf die eine Variable überflüssigerweise blockiert, auch wenn gerade auf die andere Variable zugegriffen wird. Die Fälle 2) und 3) im Experiment unten zeigen, dass dies die Berechnung etwas verlangsamt.
* Im Fall 4) werden nicht die Punkte innerhalb und außerhalb des Kreises protokolliert, sondern innerhalb des Kreises und alle. Es wird also so oft zusätzlich auf eine Akkumulatorvariable mit dem Erfordernis einer Blockierung mit `synchronized` zugegriffen, wie Punkte innerhalb des Kreises liegen. Im Vergleich von 3) und 4) kommt es deshalb zu einer Verlangsamung um ca. 30%.   
* Wenn Thread-weise auf getrennte Variablen zugegriffen wird, um die Anzahl der Punkte zu akkumulieren, ist es egal, ob mit `Runnable` oder Erben von `Thread` gearbeitet wird. Im Experiment unten zeigt sich, dass die beiden Fälle 5) und 6) ähnlich schnell sind.
* Die maximale Beschleunigung bringt die Verwendung Thread-weise getrennter Variablen zusammen mit einem nicht Thread-sicheren Zufallszahlengenerator. Der Zufallszahlengenerator darf erst innerhalb der `run`-Methode mit `ThreadLocalRandom.current()` abgerufen werden, da erst hier getrennte Threads vorliegen (also nicht als Instanzvariable mit Default-Block oder im Konstruktor initialisieren), sonst wird aus mehreren Threads auf dasselbe nicht Thread-sichere Zufallszahlengenerator-Objekt zugergiffen, was zu falschen Ergebnissen führen würde. 

## Experiment

### 0) MonteCarloPiSeq: Sequenziell ##

	3,141110 (476 ms, 1 Thread, 10.000.000 Punkte)
	3,141514 (480 ms, 1 Thread, 10.000.000 Punkte)
	3,141853 (481 ms, 1 Thread, 10.000.000 Punkte)
	
	3,141762 (4577 ms, 1 Thread, 100.000.000 Punkte)
	3,141462 (4539 ms, 1 Thread, 100.000.000 Punkte)
	3,141862 (4598 ms, 1 Thread, 100.000.000 Punkte)

### 1) MonteCarloPiSeqThreadLocal: Sequenziell mit Thread-unsicherem Zufallszahlengenerator ##

	3,141624 (146 ms, 1 Thread, 10.000.000 Punkte)
	3,142118 (146 ms, 1 Thread, 10.000.000 Punkte)
	3,140592 (146 ms, 1 Thread, 10.000.000 Punkte)
	
	3,141681 (1289 ms, 1 Thread, 100.000.000 Punkte)
	3,141664 (1302 ms, 1 Thread, 100.000.000 Punkte)
	3,141695 (1287 ms, 1 Thread, 100.000.000 Punkte)

### 2) MonteCarloPiThread0: synchronized auf gemeinsamen Lock ##

	3,141623 (1517 ms, 8 Threads, 10.000.000 Punkte)
	3,141983 (1574 ms, 8 Threads, 10.000.000 Punkte)
	3,141500 (1516 ms, 8 Threads, 10.000.000 Punkte)
	
	3,142470 (1633 ms, 16 Threads, 10.000.000 Punkte)
	3,141049 (1649 ms, 16 Threads, 10.000.000 Punkte)
	3,141593 (1525 ms, 16 Threads, 10.000.000 Punkte)
	
	3,141692 (1495 ms, 32 Threads, 10.000.000 Punkte)
	3,140560 (1519 ms, 32 Threads, 10.000.000 Punkte)
	3,141612 (1500 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141496 (14925 ms, 8 Threads, 100.000.000 Punkte)
	3,141914 (14879 ms, 8 Threads, 100.000.000 Punkte)
	3,141761 (16643 ms, 8 Threads, 100.000.000 Punkte)

### 3) MonteCarloPiThread1: synchronized auf getrennte Lock-Objekte ##

	3,141676 (1476 ms, 8 Threads, 10.000.000 Punkte)
	3,141421 (1407 ms, 8 Threads, 10.000.000 Punkte)
	3,141067 (1450 ms, 8 Threads, 10.000.000 Punkte)
	
	3,141330 (1457 ms, 16 Threads, 10.000.000 Punkte)
	3,140607 (1448 ms, 16 Threads, 10.000.000 Punkte)
	3,142683 (1461 ms, 16 Threads, 10.000.000 Punkte)
	
	3,140551 (1457 ms, 32 Threads, 10.000.000 Punkte)
	3,142291 (1472 ms, 32 Threads, 10.000.000 Punkte)
	3,141232 (1456 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141704 (14946 ms, 8 Threads, 100.000.000 Punkte)
	3,141619 (14814 ms, 8 Threads, 100.000.000 Punkte)
	3,141718 (14529 ms, 8 Threads, 100.000.000 Punkte)

### 4) MonteCarloPiThread2: synchronized auf getrennte Lock-Objekte, aber es werden die Punkte im Kreis und alle zusammen gezählt:

	3,141429 (1906 ms, 8 Threads, 10.000.000 Punkte)
	3,142169 (1955 ms, 8 Threads, 10.000.000 Punkte)
	3,141061 (1886 ms, 8 Threads, 10.000.000 Punkte)
	
	3,140720 (1903 ms, 16 Threads, 10.000.000 Punkte)
	3,141230 (1848 ms, 16 Threads, 10.000.000 Punkte)
	3,142047 (1954 ms, 16 Threads, 10.000.000 Punkte)
	
	3,141635 (1944 ms, 32 Threads, 10.000.000 Punkte)
	3,141926 (1915 ms, 32 Threads, 10.000.000 Punkte)
	3,141128 (1912 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141608 (18352 ms, 8 Threads, 100.000.000 Punkte)
	3,141649 (18790 ms, 8 Threads, 100.000.000 Punkte)
	3,141599 (18757 ms, 8 Threads, 100.000.000 Punkte)

### 5) MonteCarloPiThread3: Instanzvariablen in Thread mit Thread-lokalem Zugriff (ohne synchronized) ##

	3,141324 (134 ms, 8 Threads, 10.000.000 Punkte)
	3,141668 (133 ms, 8 Threads, 10.000.000 Punkte)
	3,141710 (129 ms, 8 Threads, 10.000.000 Punkte)
	
	3,141132 (130 ms, 16 Threads, 10.000.000 Punkte)
	3,140978 (143 ms, 16 Threads, 10.000.000 Punkte)
	3,140544 (150 ms, 16 Threads, 10.000.000 Punkte)
	
	3,142255 (136 ms, 32 Threads, 10.000.000 Punkte)
	3,141222 (137 ms, 32 Threads, 10.000.000 Punkte)
	3,140989 (147 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141666 (878 ms, 8 Threads, 100.000.000 Punkte)
	3,141580 (895 ms, 8 Threads, 100.000.000 Punkte)
	3,141524 (896 ms, 8 Threads, 100.000.000 Punkte)

### 6) MonteCarloPiRunnable: Instanzvariablen in Runnable mit Thread-lokalem Zugriff (ohne synchronized) ##

	3,140945 (132 ms, 8 Threads, 10.000.000 Punkte)
	3,141344 (140 ms, 8 Threads, 10.000.000 Punkte)
	3,141655 (145 ms, 8 Threads, 10.000.000 Punkte)
	
	3,141252 (127 ms, 16 Threads, 10.000.000 Punkte)
	3,142052 (141 ms, 16 Threads, 10.000.000 Punkte)
	3,141092 (145 ms, 16 Threads, 10.000.000 Punkte)
	
	3,141886 (193 ms, 32 Threads, 10.000.000 Punkte)
	3,142577 (141 ms, 32 Threads, 10.000.000 Punkte)
	3,141586 (122 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141370 (963 ms, 8 Threads, 100.000.000 Punkte)
	3,141436 (974 ms, 8 Threads, 100.000.000 Punkte)
	3,141630 (947 ms, 8 Threads, 100.000.000 Punkte)

### 7) MonteCarloPiThreadLocal: 4) mit Thread-lokalem Zufallszahlengenrator ##

	3,141989 (54 ms, 8 Threads, 10.000.000 Punkte)
	3,141865 (62 ms, 8 Threads, 10.000.000 Punkte)
	3,142188 (51 ms, 8 Threads, 10.000.000 Punkte)
	
	3,141710 (67 ms, 16 Threads, 10.000.000 Punkte)
	3,140867 (66 ms, 16 Threads, 10.000.000 Punkte)
	3,141256 (65 ms, 16 Threads, 10.000.000 Punkte)
	
	3,141702 (89 ms, 32 Threads, 10.000.000 Punkte)
	3,142018 (64 ms, 32 Threads, 10.000.000 Punkte)
	3,141583 (74 ms, 32 Threads, 10.000.000 Punkte)
	
	3,141777 (262 ms, 8 Threads, 100.000.000 Punkte)
	3,141503 (251 ms, 8 Threads, 100.000.000 Punkte)
	3,141429 (253 ms, 8 Threads, 100.000.000 Punkte)
	
