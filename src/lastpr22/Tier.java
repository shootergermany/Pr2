package lastpr22;

public class Tier implements Runnable {
	/**
	 * alle instancen mussen auf protected gestezt
	 * 
	 */
	static int anzahl = 0;

	protected int xKoordinate;
	protected int yKoordinate;
	protected int energy;
	protected Thread thread;
	protected double i = Math.random();/// das muss hier sein oder in der creat methode ...!!!!

	protected Tier(int lebenEnergy) {
		this.energy = lebenEnergy;

	}

	public static void create() {
		anzahl++;

		// Energy soll ein zufall zahl zwischen 0 und 99
		int x = (int) (Math.random() * 100);
		// da sein leben muss in der methode starten soll
		Tier t = new Tier(x);
		t.run();
	}

	public void setName() {/// hier muss man die thread safe classen abechten
		// StringBuilder name=new StringBuilder();
		String name = "";
		if (anzahl <= 9) {
			name = "Tier-00";
			name += anzahl;

		} else if (anzahl <= 99 && anzahl > 9) {
			name = "Tier-0";
			name += anzahl;

		} else if (anzahl <= 999 && anzahl > 99 || anzahl > 999) {
			name = "Tier-" + anzahl;
		}
		int a = 0;
		//// daynamic naming problem musst ask
	}

	public void move() {
		
		switch ((int) (Math.random() * 5)) {
		case 0:
			xKoordinate = xKoordinate;
			yKoordinate = yKoordinate;
		this.energy+= xKoordinate+yKoordinate;
		return;
		
		case 1:
			//move forward
			xKoordinate = xKoordinate++;
			yKoordinate = yKoordinate;
			
		this.energy+= xKoordinate+yKoordinate;
		return;
		
		case 2:
			//move left
			xKoordinate = xKoordinate;
			yKoordinate = yKoordinate--;
			this.energy+= xKoordinate+yKoordinate;
			return;
			
		case 3:
			//move backwards
			xKoordinate = xKoordinate--;
			yKoordinate = yKoordinate;
			this.energy+= xKoordinate+yKoordinate;
			return;
			
		case 4:
			//move right
			xKoordinate = xKoordinate;
			yKoordinate = yKoordinate++;
			this.energy+= xKoordinate+yKoordinate;
			return;
			
		}
	}
	
	
	
	
	
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (energy > 0) {

			energy--;
			try {
				System.out.println("Paralleler Thread"+anzahl);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Tier t1 = new Tier(33);

	}
}
