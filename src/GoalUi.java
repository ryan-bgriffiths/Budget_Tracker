import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Scanner;

public class GoalUi {
	public static void goalMenu(Scanner inFile, LinkedList<Goal> goals) { // Menu
		int choice = -1;

		while (choice != 0) {
			System.out.printf("\n%s\n", "-".repeat(50));
			System.out.printf("%32s\n", "GOAL MENU");
			System.out.printf("%s\n", "-".repeat(50));
			System.out.printf("\n\t%s\n", "Options:");
			System.out.printf("\t%s\n", "  1. Add Goal");
			System.out.printf("\t%s\n", "  2. Modify Goal");
			System.out.printf("\t%s\n", "  3. Delete Goal");

			choice = Driver.getMenuOption(3, inFile);

			switch (choice) {
			case 1:
				addGoal(inFile, goals);
				break;
			case 2:
				modifyGoal(inFile, goals);
			case 3:
				deleteGoal(inFile, goals);
				break;
			}
		}
	}

	public static void addGoal(Scanner inFile, LinkedList<Goal> goals) { // Collect goal and goal information from the user and adds to list
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%32s\n", "ADD GOAL");
		System.out.printf("%s\n\n", "-".repeat(50));

		//Get type of goal
		boolean savingsOrBudget = false;
		boolean validType = false;

		while (!validType) {
			System.out.print("Is this a savings or budget goal? (s/b)");
			String typeInput = inFile.next().trim().toLowerCase();

			if (typeInput.equals("s")) {
				savingsOrBudget = true;
				validType = true;
			} else if (typeInput.equals("b")) {
				savingsOrBudget = false;
				validType = true;
			} else {
				System.out.println("[!] ERROR: Invalid input.");
			}
		}
		
		//Get name and description
		System.out.print("Enter goal name: ");
		String name = inFile.nextLine().trim();

		System.out.print("Enter description: ");
		String description = inFile.nextLine().trim();
		
		//Get start date - press enter for today
        inFile.nextLine();
        LocalDate parsedStartDate = null;
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);
        
        while (parsedStartDate == null) {
            System.out.print("Enter date (YYYY-MM-DD), or press Enter for today: ");
            String dateInput = inFile.nextLine().trim();

            // if user pressed enter with no input, use today
            if (dateInput.isEmpty()) {
                parsedStartDate = today;
                break;
            }

            try {
                LocalDate candidate = LocalDate.parse(dateInput);

                // reject dates more than one year in the past
                if (candidate.isBefore(oneYearAgo)) {
                    System.out.println("[!] ERROR: Date cannot be more than one year ago.");
                    continue; // go back to top of loop and ask again
                }

                // date passed all checks
                parsedStartDate = candidate;

            } catch (DateTimeParseException e) {
                System.out.println("[!] ERROR: Use YYYY-MM-DD format. Try again.");
            }
        }
        
     //Get end date - press enter for today
        inFile.nextLine();
        LocalDate parsedEndDate = null;
        
        while (parsedEndDate == null) {
            System.out.print("Enter date (YYYY-MM-DD), cannot be the same date as starting: ");
            String dateInput = inFile.nextLine().trim();

            // if user pressed enter with no input
            if (dateInput.isEmpty()) {
                System.out.println("[!] ERROR: Please enter a date.");
                continue;
            }

            try {
                LocalDate candidate = LocalDate.parse(dateInput);

                // reject dates more than one year in the past
                if (candidate.isBefore(oneYearAgo)) {
                    System.out.println("[!] ERROR: Date cannot be more than one year ago.");
                    continue; // go back to top of loop and ask again
                }

                // reject date if equal to starting
                if (candidate.equals(parsedStartDate)) {
                	System.out.println("[!] ERROR: Date cannot be same as starting.");
                	continue;
                }

                // date passed all checks
                parsedEndDate = candidate;

            } catch (DateTimeParseException e) {
                System.out.println("[!] ERROR: Use YYYY-MM-DD format. Try again.");
            }
        }

        //Get amount
		System.out.print("Enter amount: ");
		float amount = inFile.nextFloat();

		Goal newGoal = new Goal(savingsOrBudget, 
				parsedStartDate.getYear(), 
				parsedStartDate.getMonthValue(), 
				parsedStartDate.getDayOfMonth(), 
				parsedEndDate.getYear(), 
				parsedEndDate.getMonthValue(), 
				parsedEndDate.getDayOfMonth(), 
				name,
				description, 
				amount);
		goals.add(newGoal);
		sortByDate(goals);

		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.println("Goal added.");
	}
	
	public static void modifyGoal(Scanner inFile, LinkedList<Goal> goals) { //Allows the user to modify goals from the list
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%32s\n", "MODIFY GOAL");
		System.out.printf("%s\n\n", "-".repeat(50));
		
		if(goals.isEmpty()) {
			System.out.println("No goals have been entered yet.");
			return;
		}
		for (int i = 0; i < goals.size(); i++) {
			System.out.println((i + 1) + ". " + goals.get(i).getName());
		}
		System.out.println("\nEnter goal number to modify or 0 to cancel: ");
		int choice = inFile.nextInt();
		
		if(choice == 0) {
			return;
		}
		if(choice < 1 || choice > goals.size()) {
			System.out.println("Invalid selection");
			return;
		}
		
		Goal selectedGoal = goals.get(choice - 1);
		inFile.nextLine();
		System.out.print("Enter a new name, or press enter to keep current name: ");
		String name = inFile.nextLine().trim();
		if(!name.isEmpty()) {
			selectedGoal.setName(name);
		}
		
		System.out.print("Eneter a new description or enter to keep current description: ");
		String description = inFile.nextLine().trim();
		if(!description.isEmpty()) {
			selectedGoal.setDescription(description);
		}
		
		System.out.print("Enter the new amount or -1 to keep current amount: ");
		float amount = inFile.nextFloat();
		if(amount >= 0) {
			selectedGoal.setAmount(amount);
		}
		System.out.print("Enter the new progress or -1 to keep current progress: ");
		float progress = inFile.nextFloat();
		if (progress >= 0) {
			selectedGoal.setProgress(progress);
		}
		System.out.print("Enter new end year, or 0 to keep current date: ");
		int endYear = inFile.nextInt();
		
		if (endYear != 0) {
			System.out.print("Enter new end month: ");
			int endMonth = inFile.nextInt();
			
			System.out.print("Enter new end day: ");
			int endDay = inFile.nextInt();
			
			selectedGoal.setEndDate(endYear, endMonth, endDay);
		}
		sortByDate(goals);
		System.out.printf("\n%s\n","-".repeat(50));
		System.out.println("Goal successfully modified");
	}

	public static void deleteGoal(Scanner inFile, LinkedList<Goal> goals) { //User picks one goal to delete
		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.printf("%32s\n", "DELETE GOAL");
		System.out.printf("%s\n\n", "-".repeat(50));

		if (goals.isEmpty()) {
			System.out.println("No goals have been entered yet.");
			return;
		}

		//Display goals
		for (int i = 0; i < goals.size(); i++) {
			Goal target = goals.get(i);
			System.out.println((i + 1) + ". " + target.getName() + " -----");
			System.out.println("\tDescription: " + target.getDescription());
			System.out.println("\tAmount: " + target.getAmount());
			System.out.println("\tStart Date: " + target.getStartDate());
			System.out.println("\tEnd Date: " + target.getEndDate());
			if(!target.isComplete())
				System.out.println("\tProgress: " + target.getProgress());
			else
				System.out.println("\tProgress: Completed!");
		}

		//Prompt for Goal object to delete
		System.out.print("\nEnter goal number to delete, or 0 to cancel: ");
		int choice = inFile.nextInt();

		if (choice == 0) {
			return;
		}

		if (choice < 1 || choice > goals.size()) {
			System.out.println("[!] ERROR: Invalid selection.");
			return;
		}

		goals.remove(choice - 1);

		System.out.printf("\n%s\n", "-".repeat(50));
		System.out.println("Goal deleted successfully.");
	}

	public static void sortByDate(LinkedList<Goal> goals) { //Sort date from earliest to latest.
		goals.sort((g1, g2) -> g1.getEndDate().compareTo(g2.getEndDate()));
	}
}