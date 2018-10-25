import java.util.concurrent.ThreadLocalRandom;

public class PiThread extends Thread {

	public int n = 0;
	public int numAcertos = 0;
	
	public PiThread(int n) {
		
		this.n = n;
	}
	
	public void run() {
		
		double x,y;
		
		for(double i = 0; i < n; i++) {
			
			//x = Math.random();
			//y = Math.random();~
			x  = ThreadLocalRandom.current().nextDouble(0, 1);
			y  = ThreadLocalRandom.current().nextDouble(0, 1);
			
			if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= 1.0)
				this.numAcertos++;
		}		
	}
}
