
public class TrapezoidalRule {

	
	public static void main (String[] args) {
		
		double start = 0.0;
		double end = 1.0;
		double res = 10E-11;
		double area = 0.0;
		
		long startTime, stopTime;
		
		startTime = System.currentTimeMillis();
		area = seq(start, end, res);
		stopTime = System.currentTimeMillis();
		System.out.println("(Seq) Area = " + area + " Elapsed Time: " + (stopTime - startTime) / 1000.0);
		
		startTime = System.currentTimeMillis();
		area = par(start, end, res);
		stopTime = System.currentTimeMillis();
		System.out.println("(Par) Area = " + area + " Elapsed Time: " + (stopTime - startTime) / 1000.0);
		
		startTime = System.currentTimeMillis();
		area = parForkJoin(start, end, res);
		stopTime = System.currentTimeMillis();
		System.out.println("(ParWithForkJoin) Area = " + area + " Elapsed Time: " + (stopTime - startTime) / 1000.0);
		
	}
	
	public static double seq(double start, double end, double res) {
		
		double area = 0.0;
		
		for(double i = start; i + res <= end; i += res) {
			
			area += Utils.calcArea(i, i + res);
		}
		
		
		return Math.abs(area);
	}
	
	public static double par(double start, double end, double res) {
		
		double area = 0.0;
		
		int NTHREADS = Runtime.getRuntime().availableProcessors();
		TrapThread[] t = new TrapThread[NTHREADS];
		
		double quant = (end - start) / NTHREADS;
		
		for(int tid = 0; tid < NTHREADS; tid++) {
			
			t[tid] = new TrapThread(start + (quant * tid), quant + (quant * tid), res);
			t[tid].start();
		}
		
		for(int tid = 0; tid < NTHREADS; tid++) {
			
			try {
				t[tid].join();
				area += t[tid].area;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return Math.abs(area);
	}
	
	public static double parForkJoin(double start, double end, double res) {
		
		return Math.abs((new TrapTask(start, end, res)).compute());
	}
	
}
