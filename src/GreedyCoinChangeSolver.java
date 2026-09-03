import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a greedy algorithm for the coin change problem. The algorithm delivers, as many
 * times as possible, the largest denomination that does not exceed the remaining amount.
 * This class is provided as a reference implementation. Notice that this algorithm is fast
 * but it is NOT optimal for every coin system
 * @author Daniel Mahecha
 *
 */
public class GreedyCoinChangeSolver implements CoinChangeSolver {

	//Denominations of the coin system sorted in descending order
	private List<Integer> coins = new ArrayList<>();

	@Override
	public void setCoins(List<Integer> coins) {
		this.coins = new ArrayList<>(coins);
		Collections.sort(this.coins, Collections.reverseOrder());
	}

	@Override
	public int minCoins(int amount) {
		List<Integer> answer = change(amount);
		if(answer == null) return -1;
		return answer.size();
	}

	@Override
	public List<Integer> change(int amount) {
		if(amount < 0) throw new IllegalArgumentException("The amount can not be negative: "+amount);
		List<Integer> answer = new ArrayList<>();
		int remaining = amount;
		for(int coin:coins) {
			while(coin <= remaining) {
				answer.add(coin);
				remaining -= coin;
			}
		}
		if(remaining > 0) return null;
		return answer;
	}
}
