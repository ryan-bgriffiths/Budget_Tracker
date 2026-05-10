import java.util.LinkedList;
//
// BudgetSubject - Manages observers and sends notifications for budget events
// Dependencies: BudgetObserver, LinkedList; sub-classes: none
//
public class BudgetSubject {
	
	// List of all registered observers
	private LinkedList<BudgetObserver> observers = new LinkedList<>();
	
	//
	// addObserver - adds an observer to the notification list
	// BudgetObserver observer; void
	//
	public void addObserver(BudgetObserver observer) {
		observers.add(observer);
	}
	
	//
	// notifyObservers - sends update notifications to all observers
	// String eventType; void
	//
	public void notifyObservers(String eventType) {
		for (BudgetObserver observer : observers) {
			observer.update(eventType);
		}
	}
}
