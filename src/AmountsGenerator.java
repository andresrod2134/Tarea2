import java.util.Random;

/**
 * Generates a file with random amounts to be used as input of the class DynamicProgrammingExample
 * @author Daniel Mahecha
 *
 */
public class AmountsGenerator {

	/**
	 * Main method to generate random amounts. It has three parameters:
	 * args[0]: Number of amounts to generate
	 * args[1]: Minimum value of an amount
	 * args[2]: Maximum value of an amount
	 * The amounts are written in the standard output, one amount per line
	 * @param args Array with the arguments described above
	 */
	public static void main(String[] args) {
		int numValues = Integer.parseInt(args[0]);
		int minValue = Integer.parseInt(args[1]);
		int maxValue = Integer.parseInt(args[2]);
		Random random = new Random();
		for(int i=0;i<numValues;i++) {
			int amount = random.nextInt(maxValue-minValue+1)+minValue;
			System.out.println(amount);
		}
	}
}
