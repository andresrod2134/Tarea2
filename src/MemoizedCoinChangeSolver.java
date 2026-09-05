import java.util.ArrayList;
import java.util.Arrays;
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
		ensureCapacity(amount);
		return solve(amount);
	}

	@Override
	public List<Integer> change(int amount) {
		//Nos aseguramos de que el subproblema ya este resuelto.
		int total = minCoins(amount);

		if (total == -1) {
			return null;
		}

		List<Integer> result = new ArrayList<>();
		int remaining = amount;

		while (remaining > 0) {
			for (int d : coins) {
				if (d <= remaining
						&& memory[remaining - d] != UNKNOWN
						&& memory[remaining - d] != -1
						&& memory[remaining - d] + 1 == memory[remaining]) {
					result.add(d);
					remaining -= d;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Agranda el arreglo memory si hace falta, conservando los valores ya calculados y
	 * marcando las posiciones nuevas como UNKNOWN
	 */
	private void ensureCapacity(int amount) {
		if (amount < memory.length) {
			return;
		}
		int oldLength = memory.length;
		int newLength = amount + 1;
		int [] newMemory = new int[newLength];
		System.arraycopy(memory, 0, newMemory, 0, oldLength);
		Arrays.fill(newMemory, oldLength, newLength, UNKNOWN);
		memory = newMemory;
	}

	/**
	 * Resuelve recursivamente C(m), guardando cada respuesta en memory para no
	 * volver a calcularla si se necesita de nuevo
	 */
	private int solve(int m) {
		if (m == 0) {
			return 0;
		}
		if (memory[m] != UNKNOWN) {
			return memory[m];
		}

		int best = -1;
		for (int d : coins) {
			if (d <= m) {
				int sub = solve(m - d);
				if (sub != -1 && (best == -1 || sub + 1 < best)) {
					best = sub + 1;
				}
			}
		}

		memory[m] = best;
		return best;
	}
}