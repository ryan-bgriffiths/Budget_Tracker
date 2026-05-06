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
				break;
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

		
		//Get name and description
		inFile.nextLine();
		System.out.print("Enter goal name: ");
		String name = inFile.nextLine().trim();

		System.out.print("Enter description: ");
		String description = inFile.nextLine().trim();
		
		//Get start date - press enter for today
        LocalDate parsedStartDate = null;
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);
        
        while (parsedStartDate == null) {
            System.out.print("Enter starting date (YYYY-MM-DD), or press Enter for today: ");
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

        //Get amount
        float amount = -1;
        while (amount < 0) {
            System.out.print("Enter amount: ");
            if (inFile.hasNextFloat()) {
                amount = inFile.nextFloat();
                if (amount < 0) {
                    System.out.println("[!] ERROR: Amount cannot be negative.");
                }
            } else {
                System.out.println("[!] ERROR: Invalid amount. Enter a number.");
                inFile.next();
            }
        }

		Goal newGoal = GoalFactory.createGoal(parsedStartDate, name, description, amount);
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
		
		//Display goals
		for (int i = 0; i < goals.size(); i++) {
			Goal target = goals.get(i);
			System.out.println((i + 1) + ". " + target.getName() + " -----");
			System.out.println("\tDescription: " + target.getDescription());
			System.out.println("\tAmount: " + target.getAmount());
			System.out.println("\tStart Date: " + target.getStartDate());
			if(!target.isComplete())
				System.out.println("\tProgress: " + target.getProgress());
			else
				System.out.println("\tProgress: Completed!");
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
		
		System.out.print("Enter a new description or enter to keep current description: ");
		String description = inFile.nextLine().trim();
		if(!description.isEmpty()) {
			selectedGoal.setDescription(description);
		}
		
		System.out.print("Enter the new amount or -1 to keep current amount: ");
		float amount = inFile.nextFloat();
		if (amount >= 0 && amount <= selectedGoal.getProgress()) {
			//If new goal amount is less than or equal to the progress, then it is marked complete
			selectedGoal.setAmount(amount);
			selectedGoal.setComplete(true);
			selectedGoal.setProgress(selectedGoal.getAmount());
		}
		else if (amount >= 0 && amount > selectedGoal.getProgress()) {
			selectedGoal.setAmount(amount);
		}
		
		if (!selectedGoal.isComplete()) {	//Does not run if goal is complete
			boolean completed = false;
			boolean validType = false;

			while (!validType) {
				System.out.print("Is the goal complete? (y/n) ");	//Asks if user wants to mark goal as complete
				String typeInput = inFile.next().trim().toLowerCase();

				if (typeInput.equals("y")) {
					completed = true;
					selectedGoal.setComplete(true);
					selectedGoal.setProgress(selectedGoal.getAmount());
					validType = true;
				} else if (typeInput.equals("n")) {
					completed = false;
					validType = true;
				} else {
					System.out.println("[!] ERROR: Invalid input.");
				}
			}
		
			if(!completed) {
				System.out.print("Enter progress toward goal (dollar amount) or -1 to keep current progress: ");
				float progress = inFile.nextFloat();
				if (progress >= 0 && progress > selectedGoal.getAmount()) {
					//If dollar amount is greater than goal amount, sets progress to goal amount and marks as complete
					selectedGoal.setComplete(true);
					selectedGoal.setProgress(selectedGoal.getAmount());
				}
				else if (progress >= 0 && progress <= selectedGoal.getAmount()) {
					selectedGoal.setProgress(progress);
				}
			}
		}
		
		boolean wantNewDate = false;
		boolean validType = false;

		while (!validType) {
			System.out.print("Enter new date? (y/n) ");
			String typeInput = inFile.next().trim().toLowerCase();

			if (typeInput.equals("y")) {
				wantNewDate = true;
				validType = true;
			} else if (typeInput.equals("n")) {
				wantNewDate = false;
				validType = true;
			} else {
				System.out.println("[!] ERROR: Invalid input.");
			}
		}
		
		if (wantNewDate) {	//Runs if new date is needed
			//Get date
			inFile.nextLine();
	        LocalDate parsedStartDate = null;
	        LocalDate today = LocalDate.now();
	        LocalDate oneYearAgo = today.minusYears(1);
        
			while (parsedStartDate == null) {
		           System.out.print("Enter starting date (YYYY-MM-DD), or press Enter for today: ");
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
			 
			selectedGoal.setStartDate(parsedStartDate.getYear(), parsedStartDate.getMonthValue(), parsedStartDate.getDayOfMonth());
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
		goals.sort((g1, g2) -> g1.getStartDate().compareTo(g2.getStartDate()));
	}
}