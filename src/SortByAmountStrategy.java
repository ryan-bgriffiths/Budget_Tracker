import java.util.LinkedList;

//Design pattern: Strategy 
//	implemented using the ExpenseSortStrategy interface. 
//	SortByAmountStrategy class provides implementation that defines how expenses are sorted. 
//	This allows sorting behavior to be separated from the main program logic allowing for 
//	modification and extensions in the future.
public class SortByAmountStrategy implements ExpenseSortStrategy {

	@Override
	public void sort(LinkedList<Expense> expenses) {
		expenses.sort((a, b) -> Float.compare(a.getAmount(), b.getAmount()));
	}

}
