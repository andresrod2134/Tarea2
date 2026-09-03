import java.util.List;

/**
 * Common interface for the algorithms that solve the coin change problem.
 * Given a coin system (a set of denominations with unlimited supply) and an amount,
 * the problem consists on paying exactly the amount using the smallest possible number of coins
 * @author Daniel Mahecha
 *
 */
public interface CoinChangeSolver {
	/**
	 * Changes the coin system used by this solver. Implementations keeping internal
	 * tables must discard them when this method is called
	 * @param coins Denominations of the coin system. An unlimited supply of coins is
	 * assumed for each denomination
	 */
	public void setCoins(List<Integer> coins);
	
	/**
	 * Calculates the minimum number of coins needed to pay exactly the given amount
	 * @param amount to pay. It must be a non negative number
	 * @return int Minimum number of coins needed to pay the amount. -1 if the amount
	 * can not be paid with the current coin system
	 */
	public int minCoins(int amount);
	
	/**
	 * Calculates a group of coins of minimum size paying exactly the given amount
	 * @param amount to pay. It must be a non negative number
	 * @return List<Integer> Coins paying exactly the amount. The size of the list must be
	 * equal to the value returned by the method minCoins for the same amount. An empty list
	 * if the amount is zero. null if the amount can not be paid with the current coin system
	 */
	public List<Integer> change(int amount);
}
