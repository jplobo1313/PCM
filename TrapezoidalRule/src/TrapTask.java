import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class TrapTask extends RecursiveTask<Double> {

	double start, end, res, area;
	
	public TrapTask(double start, double end, double res) {
		
		this.start = start;
		this.end = end;
		this.res = res;
		this.area = 0.0;
	}
	
	@Override
	protected Double compute() {
		
		//abs( ( area(p1) + area(p2) ) - area(p1+p2))) < 10^-7
		double mid = (end - start) / 2;
		
		double p1 = Utils.calcArea(start, (start + mid));
		double p2 = Utils.calcArea((start + mid), end);
		double pol = Utils.calcArea(start, end); 
		
		if(Math.abs((p1 + p2) - pol) <= res) {
			
			return pol;
		}
		else {

			TrapTask t1 = new TrapTask(start, (start + mid), res);
			TrapTask t2 = new TrapTask((start + mid), end, res);
			
			t1.fork();
			
			try {
				return t2.compute() + t1.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
			return 0.0;
		}
	}

}
