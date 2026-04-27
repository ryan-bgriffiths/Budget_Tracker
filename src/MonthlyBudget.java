
public class MonthlyBudget {
	public static class Node{
		Expense expense;
		Node next; // Points to either next node or previous in the list
		Node prev;
		int month; // Stores the month
		
		Node(Expense expense, int month){
			
			this.prev = null;
			this.next = null;
			this.month = month;
			this.expense = expense;
		}
	}
	Node[] head = new Node[12];
	Node[] tail = new Node[12];
	
	public void AddTransaction(double amount, String transDescription, String transDate, int month) { //A method for adding a new transaction to the list
		if (month < 1 || month > 12) {
			System.out.println("Invalid Month");
			return;
		}
		Expense expense = new Expense(amount, transDescription, transDate);// Creating a new node with the given data
		Node newNode = new Node(expense, month);
		int index = month - 1; //I think an array would be an effective way for a fixed size for only 12 months
		if (head[index] == null) {
			head[index] = newNode;
			tail[index] = newNode;
		} else {
			tail[index].next = newNode; //Current last node points to the new node
			newNode.prev = tail[index]; //New node points to old tail
			tail[index] = newNode;
		}
	}
	public void DisplayTransaction(int month) { //A method to print all transaction throughout the node from start to end
		if (month < 1 || month > 12) { //I believe if month is = 0 or 13, this will crash (Month validation)
			System.out.println("Invalid Month");
			return;
		}
		Node current = head[month - 1];
		while (current != null) { //This will keep looping as long as current is pointing at a valid node
			System.out.println("What is the amount? : " + current.expense.amount);
			System.out.println("Description? : " + current.expense.transDescription);
			System.out.println("Date? : " + current.expense.transDate);
			System.out.println("Month? : " + current.month);
			System.out.println();
			current = current.next; //Move to the next node
		}
	}
}
