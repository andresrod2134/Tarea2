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
		//TODO: Implementar llenando las tablas de manera ascendente. Las posiciones que ya
		//fueron calculadas en llamados anteriores no se deben volver a calcular
		return -1;
	}

	@Override
	public List<Integer> change(int amount) {
		//TODO: Implementar recorriendo la tabla lastCoin desde el monto hasta llegar a cero
		return null;
	}
}
