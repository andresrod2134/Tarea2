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

		int total = Integer.MAX_VALUE;
		//casos base
		if(amount == 0){
			return 0;
		}		
		//caso recursivo 
		for(int i =0; i < coins.size(); i++){
			if(coins.get(i) <= amount){
				int llamado = minCoins(amount - coins.get(i));

				if(llamado != -1){
					int resultado = 1 + llamado;
					total = Math.min(total, resultado);
				}

			}
		}			
		if(total == Integer.MAX_VALUE){
			return -1;
		}
		else{
			return total;
		}
		}
		
	

	@Override
	public List<Integer> change(int amount) {
		//TODO: Implementar con recursion pura. Sugerencia: una moneda d hace parte de una
		//solucion optima para el monto m si y solo si minCoins(m-d) == minCoins(m)-1
		List<Integer> resultado = new ArrayList<>();

		if(amount == 0){
			return resultado;
		}
		for(int i =0; i < coins.size(); i++){
			int d = coins.get(i);
			if(amount >= d && (minCoins(amount - d) == minCoins(amount)-1)){
				resultado.add(d);
				//recusividad
				List<Integer> recursion = change(amount-d);
				resultado.addAll(recursion); 

				return resultado;
			}
		}


		return resultado;
	}
}
