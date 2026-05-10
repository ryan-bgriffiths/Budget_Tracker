import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

//SaveDebtTXT: Saves the data from linked lists to files, then can retrieve the data
//Has two functions, and one dependency. Dependent on linkedList from debt
public class SaveDebtTXT {

	// saveToFile: Saves persistent data to files so it can be reused
	// Inputs, doubly linked list, and the filename
	public static void saveToFile(LinkedList<Debt> debtList, String fileName) {
		File file = new File(fileName);

		try {
		// Create file if it doesn't exist
		if (!file.exists()) {
			file.createNewFile();
		}

		// FileWriter WITHOUT append (overwrites)
		PrintWriter writer = new PrintWriter(new FileWriter(file, false));

		// For each expense in the list(expeseList) write data to writer.
		for (Debt debt : debtList) {
			writer.println(debt.getName());
			writer.println(debt.getPrincipleAmount());
			writer.println(debt.getDueDate());
			writer.println(debt.getInterestRate());
			writer.println(debt.getCompoundOrSimple());
			writer.println(debt.getRemainingBalance());
			writer.println(debt.getStatus());
			writer.println("---");
		}

		writer.close();
		} catch (IOException e) {
			System.out.println("Error saving file: " + fileName);
		}
		
	}

	// loadFromFile: creates a linkedList from the previously saved file data
	// input = files, Outputs = LinkedList of debt
	public static LinkedList<Debt> loadFromFile(String fileName) {
		LinkedList<Debt> debtList = new LinkedList<>();
		File file = new File(fileName);

		try {
		// If file doesn't exist OR is empty, return empty list
		if (!file.exists() || file.length() == 0) {
			return debtList;
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		while ((line = reader.readLine()) != null) {

			String name = line;
			float principle = Float.parseFloat(reader.readLine());
			LocalDate dueDate = LocalDate.parse(reader.readLine());
			float interestRate = Float.parseFloat(reader.readLine());
			boolean compoundOrSimple = Boolean.parseBoolean(reader.readLine());
			float remainingBalance = Float.parseFloat(reader.readLine());
			boolean status = Boolean.parseBoolean(reader.readLine());


			reader.readLine(); // skip "---"
			
			Debt debt = new Debt(name, principle, dueDate.getYear(),
	                dueDate.getMonthValue(), dueDate.getDayOfMonth(), interestRate,
					 compoundOrSimple,  remainingBalance,  status);

			debtList.add(debt);
		}

		reader.close();
		
		} catch (IOException e) {
			System.out.println("Error saving file: " + fileName);
		}
		
		return debtList;
	}
}
