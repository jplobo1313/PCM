
public class TrapThread extends Thread {

	double start;
	double end;
	double res;
	double area;
	
	public TrapThread(double start, double end, double res) {
		
		this.start = start;
		this.end = end;
		this.res = res;
		this.area = 0.0;
	}
	
	public void run() {
		
		for(double i = start; i + res <= end; i += res) {
			
			area += Utils.calcArea(i, i + res);
		}
		
	}
	
}
