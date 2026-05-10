//
// BudgetObserver - Observer interface for Observer design pattern
// Dependencies: none; sub-classes: SimpleObserver
//
public interface BudgetObserver {
	//
	// update - receives notification when budget-related data changes
	// String eventType; void
	//
	void update(String eventType);
}

