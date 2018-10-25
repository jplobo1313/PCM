import java.util.concurrent.ThreadLocalRandom;

public class Pi {

	
	public static void main(String[] args) {
		
		final int N = 1000000000;
		long startTime;
		long stopTime;
		double pi;
		
		startTime = System.currentTimeMillis();
		pi = seq(N);
		stopTime = System.currentTimeMillis();
		System.out.println("(Seq) PI = " + pi + " Elapsed Time: " + (stopTime - startTime));
		
		startTime = System.currentTimeMillis();
		pi = par(N);
		stopTime = System.currentTimeMillis();
		System.out.println("(Par) PI = " + pi + " Elapsed Time: " + (stopTime - startTime));
		
	}
	
	public static double seq(int n) {
		
		double numAcertos = 0;
		double pi = 0.0;
		
		double x,y;
		
		for(int i = 0; i < n; i++) {
			
			/*
			x = Math.random();
			y = Math.random();
			*/
			x = ThreadLocalRandom.current().nextDouble(0, 1);
			y = ThreadLocalRandom.current().nextDouble(0, 1);
			
			if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= 1.0)
				numAcertos++;
		}
		
		pi = 4 * (numAcertos / n);
		
		return pi;		
	}
	
	public static double par(int n) {
		
		double numAcertos = 0;
		double pi = 0;
		
		int numThreads = Runtime.getRuntime().availableProcessors();
		
		PiThread[] ts = new PiThread[numThreads];
		
		for(int i = 0; i < numThreads; i++) {
			
			ts[i] = new PiThread((n / numThreads));
			ts[i].start();
		}
		
		for(int i = 0; i < numThreads; i++) {
			
			try {
				
				ts[i].join();
				numAcertos += ts[i].numAcertos;
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/*
		System.out.println(numLancamentos);
		System.out.println(numAcertos);
		*/
		
		pi = 4 * (numAcertos / n);
		
		return pi;
	}
}


