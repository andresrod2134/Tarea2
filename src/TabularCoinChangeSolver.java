import java.util.ArrayList;
import java.util.List;

/**
 * Implements the coin change problem with the bottom-up (tabulation) approach of dynamic
 * programming. The subproblems are solved in increasing order of the amount, filling a table
 * that allows to answer both the minimum number of coins and the coins of an optimal solution
 * @author Daniel Mahecha
 *
 */
public class TabularCoinChangeSolver implements CoinChangeSolver {

	//Denominations of the coin system
	private List<Integer> coins = new ArrayList<>();
	//Answer of each subproblem. The position m stores the minimum number of coins to pay m
	//It must be equal to -1 if the amount m can not be paid
	private int [] minCoinsTable = new int[0];
	//Last coin delivered within an optimal solution for each amount. It is used by the method
	//change to rebuild an optimal solution without solving the subproblems again
	private int [] lastCoin = new int[0];

	@Override
	public void setCoins(List<Integer> coins) {
		this.coins = new ArrayList<>(coins);
		minCoinsTable = new int[0];
		lastCoin = new int[0];
	}

	@Override
	public int minCoins(int amount) {
		ensureCapacity(amount);
		return minCoinsTable[amount];
	}

	@Override
	public List<Integer> change(int amount) {
		ensureCapacity(amount);

		if (minCoinsTable[amount] == -1) {
			return null;
		}

		
		List<Integer> result = new ArrayList<>();
		int remaining = amount;
		while (remaining > 0) {
			int coin = lastCoin[remaining];
			result.add(coin);
			remaining -= coin;
		}

		return result;
	}

	/**
	 * Agranda las tablas si hace falta, conservando lo que ya se habia calculado, y
	 * calcula unicamente las posiciones nuevas de manera ascendente
	 */
	private void ensureCapacity(int amount) {
		int oldLength = minCoinsTable.length;
		if (amount < oldLength) {
			return;
		}

		int newLength = amount + 1;
		int [] newMinCoins = new int[newLength];
		int [] newLastCoin = new int[newLength];
		System.arraycopy(minCoinsTable, 0, newMinCoins, 0, oldLength);
		System.arraycopy(lastCoin, 0, newLastCoin, 0, oldLength);
		minCoinsTable = newMinCoins;
		lastCoin = newLastCoin;

		int start = oldLength;
		if (start == 0) {
			minCoinsTable[0] = 0;
			lastCoin[0] = -1;
			start = 1;
		}

		for (int m = start; m < newLength; m++) {
			int best = -1;
			int bestCoin = -1;
			for (int d : coins) {
				if (d <= m && minCoinsTable[m - d] != -1) {
					int candidate = minCoinsTable[m - d] + 1;
					if (best == -1 || candidate < best) {
						best = candidate;
						bestCoin = d;
					}
				}
			}
			minCoinsTable[m] = best;
			lastCoin[m] = bestCoin;
		}
	}
}