//
// SimpleObserver - Concrete observer that displays update notifications
// Dependencies: BudgetObserver; sub-classes: none
//
public class SimpleObserver implements BudgetObserver {
   //
   // update - displays notification message when system changes occur
   // String eventType; void
   //
   public void update(String eventType) {
       System.out.println(eventType);
       System.out.println("System updated successfully.");
   }
}

