import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

//SaveDebtTXT: Saves the data from linked lists to files, then can retrieve the data
//Has two functions, and one dependency. Dependant on linkedList from debt
public class SaveDebtTXT {

	// saveToFile: Saves persistent data to files so it can be reused
	// Inputs, doubly linked list, and the filename
	public static void saveToFile(LinkedList<Debt> debtList, String fileName) throws IOException {
		File file = new File(fileName);

		// Create file if it doesn't exist
		if (!file.exists()) {
			file.createNewFile();
		}

		// FileWriter WITHOUT append (overwrites)
		PrintWriter writer = new PrintWriter(new FileWriter(file, false));

		// For each expense in the list(expeseList) write data to writer.
		for (Debt debt : debtList) {
			writer.println(debt.name);
			writer.println(debt.principle);
			writer.println(debt.dueDate);
			writer.println(debt.interestRate);
			writer.println(debt.compoundOrSimple);
			writer.println(debt.remainingBalance);
			writer.println(debt.status);
			writer.println("---");
		}

		writer.close();
	}

	// loadFromFile: creates a linkedList from the previously saved file data
	// input = files, Outputs = LinkedList of debt
	public static LinkedList<Debt> loadFromFile(String fileName) throws IOException {
		LinkedList<Debt> debtList = new LinkedList<>();
		File file = new File(fileName);

		// If file doesn't exist OR is empty, return empty list
		if (!file.exists() || file.length() == 0) {
			return debtList;
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		while ((line = reader.readLine()) != null) {
			Debt debt = new Debt();

			debt.name = line;
			debt.principle = Float.parseFloat(reader.readLine());
			debt.dueDate = LocalDate.parse(reader.readLine());
			debt.interestRate = Float.parseFloat(reader.readLine());
			debt.compoundOrSimple = Boolean.parseBoolean(reader.readLine());
			debt.remainingBalance = Float.parseFloat(reader.readLine());
			debt.status = Boolean.parseBoolean(reader.readLine());

			debt.paymentHistory = new LinkedList<>();

			reader.readLine(); // skip "---"

			debtList.add(debt);
		}

		reader.close();
		return debtList;
	}
}
