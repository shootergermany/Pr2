package lastpr22;

public class Tier implements Runnable {
/**
 * alle instancen mussen auf protected gestezt
 * 
 */
	static int anzahl=0 ;
	
	protected int  xKoordinate;
	protected int  yKoordinate;
	protected int energy;
	protected Thread thread;
	protected double i = Math.random();
	
	
	protected Tier(int lebenEnergy) {
		this.energy=lebenEnergy;
		
	}
	
	public static void create() {
		anzahl++;
		
		//Energy soll ein zufall zahl  zwischen 0 und 99
		int x = (int )( Math.random() * 100 );
		//da sein leben muss in der methode starten soll 
		Tier t =new Tier(x);
		t.run();
	}
	
	public void   setName () {
		//StringBuilder name=new StringBuilder();
		String name ="";
		if (anzahl<=9) {
			 name="Tier-00";
			 name+=anzahl;
			
		} else if (anzahl<=99 && anzahl>9 ){
			 name="Tier-0";
			 name+=anzahl;
			 
		}else if (anzahl<=999 && anzahl>99 || anzahl>999) {
			name="Tier-"+anzahl;
		}
		int a = 0;
		Tier (a) =new Tier(33);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(energy>0) {
			
			energy--;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
public static void main (String []args) {
	Tier t1 = new Tier(33);
	
}
}
