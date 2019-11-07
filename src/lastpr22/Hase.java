package lastpr22;

public class Hase extends Tier implements Runnable  {
	
	protected Hase() {
		//lebes energie muss immer 40 sein 
		super(40);
		
	}
	public static void create() {
		anzahl++;
		//da sein leben muss in der methode starten soll 
		//muss type hase sein 
		Hase t =new Hase();
		t.run();
	}
	
}
