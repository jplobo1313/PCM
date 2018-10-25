
public class Utils {

	public static double calcArea(double a, double b) {
		
		return ((f(a) + f(b)) / 2) * (b - a);
	}
	
	
	public static double f (double x) {
		return x * (x-1);
	}
}
