import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Program to test the algorithms implemented in this project
 * @author Daniel Mahecha
 *
 */
public class DynamicProgrammingExample {

	//Default algorithm used to solve the coin change problem if none is provided
	public static final String DEF_ALGORITHM = "Tabular";

	/**
	 * Main method for the dynamic programming example. It has four parameters:
	 * args[0]: Path to the input file with the denominations of the coin system. It must be a
	 * text file with one denomination per line
	 * args[1]: Path to the input file with the amounts to pay. It must be a text file with one
	 * amount per line
	 * args[2]: Path to the output file where the solution of each amount will be written
	 * args[3]: (Optional) Algorithm used to solve the coin change problem. It can be Naive,
	 * Memoized, Tabular or Greedy. If not provided, the algorithm implemented in the class
	 * TabularCoinChangeSolver is used.
	 * WARNING: the algorithm Naive is exponential. It should only be executed with small amounts
	 * @param args Array with the arguments described above
	 * @throws Exception if the input files do not exist or they can not be read
	 * @throws Exception if the algorithm is not implemented
	 */
	public static void main(String[] args) throws Exception {
		//Read parameters
		String coinsFilename = args[0];
		String amountsFilename = args[1];
		String outFilename = args[2];
		String algorithm = DEF_ALGORITHM;
		if(args.length>3) algorithm = args[3];

		//Read input files
		List<Integer> coins = loadNumbers(coinsFilename);
		List<Integer> amounts = loadNumbers(amountsFilename);
		validate(coins, amounts);

		System.out.println("Sistema monetario ("+coins.size()+" denominaciones): "+coins);
		System.out.println("Montos a pagar: "+amounts.size()+". Algoritmo: "+algorithm);

		//Solve every amount with the selected algorithm
		CoinChangeSolver solver = loadSolver(algorithm);
		solver.setCoins(coins);
		int [] sizes = new int[amounts.size()];
		List<List<Integer>> solutions = new ArrayList<>();

		long startTime = System.currentTimeMillis();
		for(int i=0;i<amounts.size();i++) {
			int amount = amounts.get(i);
			sizes[i] = solver.minCoins(amount);
			solutions.add(solver.change(amount));
		}
		long endTime = System.currentTimeMillis();

		//Output answer checking the consistency of the solution of each amount
		Set<Integer> coinsSet = new HashSet<>(coins);
		int errors = 0;
		try (PrintStream out = new PrintStream(outFilename)) {
			out.println("Monto\tNumeroMonedas\tMonedas");
			for(int i=0;i<amounts.size();i++) {
				int amount = amounts.get(i);
				List<Integer> solution = solutions.get(i);
				if(solution == null) {
					if(sizes[i]!=-1) errors += reportError("El metodo minCoins retorno "+sizes[i]+" para el monto "+amount+" pero el metodo change no retorno una solucion");
					out.println(amount+"\t-1\tIMPOSIBLE");
					continue;
				}
				int total = 0;
				for(int coin:solution) {
					total += coin;
					if(!coinsSet.contains(coin)) errors += reportError("La solucion para el monto "+amount+" incluye la moneda "+coin+" que no hace parte del sistema monetario");
				}
				if(total!=amount) errors += reportError("Las monedas entregadas para el monto "+amount+" suman "+total);
				else if(solution.size()!=sizes[i]) errors += reportError("El metodo minCoins retorno "+sizes[i]+" para el monto "+amount+" pero el metodo change retorno "+solution.size()+" monedas");
				out.println(amount+"\t"+solution.size()+"\t"+solution);
			}
		}
		if(errors>0) System.out.println("Se encontraron "+errors+" inconsistencias en las soluciones. Revisar la implementacion del algoritmo "+algorithm);
		System.out.println("Montos resueltos. Tiempo total(milisegundos): "+(endTime-startTime));

		//Compare the answers with the answers of the greedy algorithm
		CoinChangeSolver greedy = new GreedyCoinChangeSolver();
		greedy.setCoins(coins);
		int differences = 0;
		for(int i=0;i<amounts.size();i++) {
			int amount = amounts.get(i);
			int greedySize = greedy.minCoins(amount);
			if(greedySize == sizes[i]) continue;
			differences++;
			if(differences<=5) System.out.println("Diferencia con el algoritmo voraz en el monto "+amount+". "+algorithm+": "+sizes[i]+" monedas. Greedy: "+greedySize+" monedas");
		}
		System.out.println("El algoritmo voraz entrego una respuesta diferente en "+differences+" de los "+amounts.size()+" montos");
	}

	/**
	 * Loads the solver of the given algorithm using introspection
	 * @param algorithm Name of the algorithm
	 * @return CoinChangeSolver object implementing the given algorithm
	 * @throws Exception if the class implementing the algorithm can not be instantiated
	 */
	private static CoinChangeSolver loadSolver(String algorithm) throws Exception {
		String classname = algorithm+"CoinChangeSolver";
		try {
			Class<?> algorithmClass = Class.forName(classname);
			Constructor<?> emptyConstructor = algorithmClass.getConstructor();
			return (CoinChangeSolver)emptyConstructor.newInstance();
		} catch (Exception e) {
			throw new Exception("Algoritmo invalido "+algorithm,e);
		}
	}

	/**
	 * Loads the numbers within the given file. Empty lines and lines starting with the
	 * character # are ignored
	 * @param filename Path to the file with the numbers
	 * @return List<Integer> Numbers within the file
	 * @throws Exception if the file does not exist or it can not be read
	 */
	private static List<Integer> loadNumbers(String filename) throws Exception {
		List<Integer> answer = new ArrayList<>();
		try (FileReader reader = new FileReader(filename);
			 BufferedReader in = new BufferedReader(reader)) {
			String line = in.readLine();
			for (int i=0;line != null;i++) {
				String value = line.trim();
				if(value.length()>0 && !value.startsWith("#")) {
					try {
						answer.add(Integer.parseInt(value));
					} catch (Exception e) {
						System.err.println("Can not read number from line "+i+" of the file "+filename+" content: "+line);
					}
				}
				line = in.readLine();
			}
		}
		return answer;
	}

	/**
	 * Validates the input data of the program
	 * @param coins Denominations of the coin system
	 * @param amounts Amounts to pay
	 */
	private static void validate(List<Integer> coins, List<Integer> amounts) {
		if(coins.size()==0) throw new RuntimeException("El sistema monetario no tiene denominaciones");
		if(amounts.size()==0) throw new RuntimeException("No se encontraron montos para pagar");
		Set<Integer> unique = new HashSet<>();
		for(int coin:coins) {
			if(coin<=0) throw new RuntimeException("Las denominaciones deben ser numeros positivos. Denominacion encontrada: "+coin);
			if(!unique.add(coin)) throw new RuntimeException("La denominacion "+coin+" aparece mas de una vez en el sistema monetario");
		}
		for(int amount:amounts) {
			if(amount<0) throw new RuntimeException("Los montos deben ser numeros no negativos. Monto encontrado: "+amount);
		}
	}

	/**
	 * Reports an inconsistency found within a solution
	 * @param message describing the inconsistency
	 * @return int one, to be added to the total number of inconsistencies
	 */
	private static int reportError(String message) {
		System.err.println("ERROR: "+message);
		return 1;
	}

}
