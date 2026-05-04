import java.util.LinkedList;

// Design pattern Strategy Interface
public interface ExpenseSortStrategy {
	
	void sort(LinkedList<Expense> expenses);

}
