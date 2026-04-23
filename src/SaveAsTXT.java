import java.io.*;
import java.time.LocalDate;

//Note to programming team, if you run the program and don't see changes to
//the files, please try refreshing the project by right clicking it in eclipse
//and pressing the refresh icon in the drop down. Thanks!



//SaveAsTXT: Saves the data from linked lists to files, then can retrieve the data
//Has two functions, and one dependency. Dependant on linkedList class
public class SaveAsTXT {

	
	//saveToFile: Saves persistent data to files so it can be reused
	//Inputs, doubly linked list, and the filename/month, outputs the data for that month
	public static void saveToFile(ExpenseDoublyLinkedList list, String fileName) {
		try {
			File file = new File(fileName);

			// Create file if it doesn't exist
			if (!file.exists()) {
				file.createNewFile();
			}

			// FileWriter WITHOUT append  (overwrites)
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));

			ExpenseNode current = list.getHead();

			while (current != null) {
				writer.println(current.amount);
				writer.println(current.date);
				writer.println(current.name);
				writer.println(current.paid ? 0 : 1);
				writer.println("---");
				current = current.next;
			}

			writer.close();

		} catch (IOException e) {
			System.out.println("Error saving file: " + fileName);
		}
	}

	
	//loadFromFile: creates a linkedList from the previously saved file data
	//input = files, Outputs = LinkedLists for each month/file saved
	public static ExpenseDoublyLinkedList loadFromFile(String fileName) {
		ExpenseDoublyLinkedList list = new ExpenseDoublyLinkedList();
		File file = new File(fileName);

		// If file doesn't exist OR is empty, return empty list
		if (!file.exists() || file.length() == 0) {
			return list;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = reader.readLine()) != null) {
				float amount = Float.parseFloat(line);
				LocalDate date = LocalDate.parse(reader.readLine());
				String name = reader.readLine();
				int paidInput = Integer.parseInt(reader.readLine());
				reader.readLine(); // skip "---"

				boolean paid = (paidInput == 0);
				list.addLast(amount, date, name, paid);
			}

		} catch (IOException e) {
			System.out.println("Error reading file: " + fileName);
		}

		return list;
	}
}