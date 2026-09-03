import java.util.ArrayList;
import java.util.List;

/**
 * Implements the coin change problem solving directly the recurrence
 * C(0) = 0 and C(m) = 1 + min { C(m-d) : d denomination, d <= m }
 * with plain recursion. It is NOT allowed to store partial results in this class
 * @author Daniel Mahecha
 *
 */
public class NaiveCoinChangeSolver implements CoinChangeSolver {

	//Denominations of the coin system
	private List<Integer> coins = new ArrayList<>();

	@Override
	public void setCoins(List<Integer> coins) {
		this.coins = new ArrayList<>(coins);
	}

	@Override
	public int minCoins(int amount) {
		//TODO: Implementar la recurrencia con recursion pura. No se permite guardar resultados parciales
		//Retornar -1 si el monto no se puede pagar con el sistema monetario actual
		return -1;
	}

	@Override
	public List<Integer> change(int amount) {
		//TODO: Implementar con recursion pura. Sugerencia: una moneda d hace parte de una
		//solucion optima para el monto m si y solo si minCoins(m-d) == minCoins(m)-1
		return null;
	}
}
