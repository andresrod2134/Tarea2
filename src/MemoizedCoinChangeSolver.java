import java.util.ArrayList;
import java.util.List;

/**
 * Implements the coin change problem with the top-down (memoization) approach of dynamic
 * programming. The recurrence is the same solved by the class NaiveCoinChangeSolver but the
 * answer of each subproblem is calculated only once and stored to be reused
 * @author Daniel Mahecha
 *
 */
public class MemoizedCoinChangeSolver implements CoinChangeSolver {

	//Value to identify subproblems that have not been solved yet
	private static final int UNKNOWN = -2;
	
	//Denominations of the coin system
	private List<Integer> coins = new ArrayList<>();
	
	//Answers of the subproblems that have been solved. The position m stores the answer of C(m)
	//It must be equal to UNKNOWN if the subproblem m has not been solved yet
	private int [] memory = new int[0];

	@Override
	public void setCoins(List<Integer> coins) {
		this.coins = new ArrayList<>(coins);
		memory = new int[0];
	}

	@Override
	public int minCoins(int amount) {
		//TODO: Implementar. El arreglo memory se debe agrandar (y los datos ya
		//calculados se deben conservar) si el monto es mayor que el tamano actual del arreglo
		//Sugerencia: usar el metodo java.util.Arrays.fill para inicializar las posiciones nuevas
		//en UNKNOWN y luego delegar el trabajo en un metodo recursivo
		return -1;
	}

	@Override
	public List<Integer> change(int amount) {
		//TODO: Implementar reutilizando la informacion almacenada en el atributo memory.
		//Este metodo no debe hacer llamados recursivos adicionales al metodo minCoins
		return null;
	}
}
