
public class main {
	class Node{
		double amount; // This will store the money value of the transaction.
		String transDescription; // A short explanation by user
		String transDate; // Date of the transaction
		Node next; // Points to either next node or previous in the list
		Node prev;
		int month; // Stores the month
		
		Node(double amount, String transDescription, String transDate, int month){
			this.amount = amount;
			this.transDescription = transDescription;
			this.transDate = transDate;
			this.prev = null;
			this.next = null;
			this.month = month;
		}
	}
	Node[] head = new Node[12];
	Node[] tail = new Node[12];
	
	public void AddTransaction(double amount, String transDescription, String transDate, int month) { //A method for adding a new transaction to the list
		Node NewNode = new Node(amount, transDescription, transDate, month); // Creating a new node with the given data
		int index = month - 1; //I think an array would be an effective way for a fixed size for only 12 months
		if (head[index] == null) {
			head[index] = NewNode;
			tail[index] = NewNode;
		} else {
			tail[index].next = NewNode; //Current last node points to the new node
			NewNode.prev = tail[index]; //New node points to old tail
			tail[index] = NewNode;
		}
	}
	public void DisplayTransaction(double amount, String transDescription, String transDate, int month) { //A method to print all transaction throughout the node from start to end
		Node current = head[month - 1];
		while (current != null) { //This will keep looping as long as current is pointing at a valid node
			System.out.println("What is the amount? : " + current.amount);
			System.out.println("Description? : " + current.transDescription);
			System.out.println("Date? : " + current.transDate);
			System.out.println("Month? : " + current.month);
			System.out.println();
			current = current.next; //Move to the next node
		}
		if (month < 1 || month > 12) {
			System.out.println("Invalid Month");
			return;
		}
	}
}
