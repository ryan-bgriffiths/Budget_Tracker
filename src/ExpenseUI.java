import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Collections;

//
// ExpenseUI - handles the user interface for adding, modifying, and deleting expenses
// Dependencies: Expense, Driver; none
//
public class ExpenseUI {

    //
    // showExpenseMenu - landing page that routes user to add, modify, or delete
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void showExpenseMenu(Scanner inFile, LinkedList<Expense>[] monthList) {
        int choice = -1;
        while (choice != 0) {
        	//Header 
        	System.out.println("\t╔══════════════════════════════╗");
            System.out.println("\t║   ****  EXPENSE MENU  ****   ║");
            System.out.println("\t╚══════════════════════════════╝\n");
        	
            //Options 
            System.out.printf("\t%s\n", "Options:");
            System.out.printf("\t%s\n", "  1. Add Expense");
            System.out.printf("\t%s\n", "  2. Modify Expense");
            System.out.printf("\t%s\n", "  3. Delete Expense\n");
            choice = Driver.getMenuOption(3, inFile);

            switch (choice) {
                case 1: addExpense(inFile, monthList); break;
                case 2: modifyExpense(inFile, monthList); break;
                case 3: deleteExpense(inFile, monthList); break;
            }
        }
    }

    //
    // addExpense - collects expense info from user, creates Expense object, adds to list
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void addExpense(Scanner inFile, LinkedList<Expense>[] allMonths) {
        System.out.printf("\n%s\n", "-".repeat(50));
        
        
        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║   ****  ADD EXPENSE  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        // get name
        inFile.nextLine();
        System.out.print("Enter expense name: ");
        String name = inFile.nextLine().trim();

        // get amount
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

        // get date - press enter for today
        inFile.nextLine();
        LocalDate parsedDate = null;
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);
        
        while (parsedDate == null) {
            System.out.print("Enter date (YYYY-MM-DD), or press Enter for today: ");
            String dateInput = inFile.nextLine().trim();

            // if user pressed enter with no input, use today
            if (dateInput.isEmpty()) {
                parsedDate = today;
                break;
            }

            try {
                LocalDate candidate = LocalDate.parse(dateInput);

                // reject dates more than one year in the past
                if (candidate.isBefore(oneYearAgo)) {
                    System.out.println("[!] ERROR: Date cannot be more than one year ago.");
                    continue; // go back to top of loop and ask again
                }

                // reject dates in the future
                if (candidate.isAfter(today)) {
                    System.out.println("[!] ERROR: Date cannot be in the future.");
                    continue;
                }

                // date passed all checks
                parsedDate = candidate;

            } catch (DateTimeParseException e) {
                System.out.println("[!] ERROR: Use YYYY-MM-DD format. Try again.");
            }
        }
        

        // get paid status
        boolean paid = false;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Is this paid? (y/n): ");
            String paidInput = inFile.next().trim().toLowerCase();
            if (paidInput.equals("y")) { paid = true; validInput = true; }
            else if (paidInput.equals("n")) { paid = false; validInput = true; }
            else { System.out.println("[!] ERROR: Enter y or n."); }
        }
        
        // get income toggle
        // income entries are stored in the same list as expenses
        // but flagged with isIncome = true so they can be displayed separately
        boolean isIncome = false;
        validInput = false;
        while (!validInput) {
            System.out.print("Is this income? (y/n): ");
            String incomeInput = inFile.next().trim().toLowerCase();
            if (incomeInput.equals("y")) { isIncome = true; validInput = true; }
            else if (incomeInput.equals("n")) { isIncome = false; validInput = true; }
            else { System.out.println("[!] ERROR: Enter y or n."); }
        }
        
        // get recurring toggle
        // recurring entries get auto-copied into the next month on startup
        boolean isRecurring = false;
        validInput = false;
        while (!validInput) {
            System.out.print("Is this a recurring entry? (y/n): ");
            String recurringInput = inFile.next().trim().toLowerCase();
            if (recurringInput.equals("y")) { isRecurring = true; validInput = true; }
            else if (recurringInput.equals("n")) { isRecurring = false; validInput = true; }
            else { System.out.println("[!] ERROR: Enter y or n."); }
        }
        
        // create Expense object and add to list
        Expense newExpense = new Expense(
            name, amount,
            parsedDate.getYear(),
            parsedDate.getMonthValue(),
            parsedDate.getDayOfMonth(),
            paid, isIncome, isRecurring
        );
     // put the expense in the correct month's list based on its date
        // this handles cases where user enters a date from a different month
        int targetMonthIndex = parsedDate.getMonthValue() - 1;
        allMonths[targetMonthIndex].add(newExpense);

        // sort the target month's list by date so entries stay in chronological order
        // Collections.sort with a Comparator compares two expenses by their dates
        Collections.sort(allMonths[targetMonthIndex],
            new Comparator<Expense>() {
                public int compare(Expense a, Expense b) {
                    return a.getDate().compareTo(b.getDate());
                }
            }
        );

        // save the updated list to file immediately so data persists
        String[] fileNames = {
            "JanuaryExpenses.txt", "FebruaryExpenses.txt", "MarchExpenses.txt",
            "AprilExpenses.txt", "MayExpenses.txt", "JuneExpenses.txt",
            "JulyExpenses.txt", "AugustExpenses.txt", "SeptemberExpenses.txt",
            "OctoberExpenses.txt", "NovemberExpenses.txt", "DecemberExpenses.txt"
        };
        SaveAsTXT.saveToFile(allMonths[targetMonthIndex], fileNames[targetMonthIndex]);

        System.out.printf("\n%s\n", "-".repeat(50));
        if (isIncome) {
            System.out.println("Income added successfully.");
        } else {
            System.out.println("Expense added successfully.");
        }
    }
    

    //
    // deleteExpense - shows all expenses, user picks one to delete
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void deleteExpense(Scanner inFile, LinkedList<Expense>[] monthList) {
        
    	//Header 
        System.out.println("\n\t╔══════════════════════════════╗");
        System.out.println(  "\t║   ****  DELETE EXPENSE ****  ║");
        System.out.println(  "\t╚══════════════════════════════╝\n");
        
        System.out.print("Enter the number of the month with the expense you want to delete, or 0 to cancel: ");
        int month = -1;
        if (inFile.hasNextInt()) {
        	month = inFile.nextInt();
        }
        if (month == 0) return;
        if (month < 1 || month > 12) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }
        int index = month - 1;
        if (monthList[month].isEmpty()) {
            System.out.println("No expenses have been entered yet.");
            return;
        }

        // display all expenses with numbers
        System.out.println("Existing Expenses:");
        int count = 1;
        for (Expense e : monthList[month]) {
            System.out.printf("  %d. %-15s $%-10.2f %s%n",
                count, e.getName(), e.getAmount(), e.getDate().toString());
            count++;
        }

        // get selection
        System.out.print("\nEnter the number of the expense to delete, or 0 to cancel: ");
        int choice = -1;
        if (inFile.hasNextInt()) {
            choice = inFile.nextInt();
        }
        if (choice == 0) return;
        if (choice < 1 || choice >= count) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }

        // get target expense
        Expense target = monthList[month].get(choice - 1);

        // confirm deletion
        System.out.printf("\nAre you sure you want to delete:\n%s - $%.2f - %s? (y/n): ",
            target.getName(), target.getAmount(), target.getDate().toString());
        String confirm = inFile.next().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        monthList[index].remove(choice - 1);
        
     // save updated list to file
        String[] fileNames = {
            "JanuaryExpenses.txt", "FebruaryExpenses.txt", "MarchExpenses.txt",
            "AprilExpenses.txt", "MayExpenses.txt", "JuneExpenses.txt",
            "JulyExpenses.txt", "AugustExpenses.txt", "SeptemberExpenses.txt",
            "OctoberExpenses.txt", "NovemberExpenses.txt", "DecemberExpenses.txt"
        };
        SaveAsTXT.saveToFile(monthList[index], fileNames[index]);
        
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Entry deleted successfully.");
    }

    //
    // modifyExpense - shows all expenses, user picks one and edits a field
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void modifyExpense(Scanner inFile, LinkedList<Expense>[] monthList) {
        
        //Header 
        System.out.println("\n\t╔══════════════════════════════╗");
        System.out.println("  \t║  ****  MODIFY EXPENSE  ****  ║");
        System.out.println("  \t╚══════════════════════════════╝\n");
        

        System.out.print("Enter the number of the month with the expense you want to modify, or 0 to cancel: ");
        int month = -1;
        if (inFile.hasNextInt()) {
        	month = inFile.nextInt();
        }
        if (month == 0) return;
        if (month < 1 || month > 12) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }
        int index = month - 1;
        if (monthList[index].isEmpty()) {
            System.out.println("No expenses have been entered yet.");
            return;
        }

        // display all expenses
        System.out.println("Existing Expenses:");
        int count = 1;
        for (Expense e : monthList[index]) {
            System.out.printf("  %d. %-15s $%-10.2f %s%n",
                count, e.getName(), e.getAmount(), e.getDate().toString());
            count++;
        }

        // get selection
        System.out.print("\nEnter the number of the expense to modify, or 0 to cancel: ");
        int choice = -1;
        if (inFile.hasNextInt()) {
            choice = inFile.nextInt();
        }
        if (choice == 0) return;
        if (choice < 1 || choice >= count) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }

        Expense target = monthList[index].get(choice - 1);

        // show edit options
        System.out.printf("\nEditing: %s - $%.2f - %s\n\n",
            target.getName(), target.getAmount(), target.getDate().toString());
        System.out.println("  1. Edit name");
        System.out.println("  2. Edit amount");
        System.out.println("  3. Edit date");
        System.out.println("  4. Toggle paid status");
        System.out.println("  5. Toggle income status");
        System.out.println("  6. Toggle recurring status");
        int field = Driver.getMenuOption(6, inFile); // change 4 to 6

        inFile.nextLine();
        switch (field) {
            case 1:
                System.out.print("New name: ");
                target.setName(inFile.nextLine().trim());
                break;
            case 2:
                float newAmount = -1;
                while (newAmount < 0) {
                    System.out.print("New amount: ");
                    if (inFile.hasNextFloat()) {
                        newAmount = inFile.nextFloat();
                        if (newAmount < 0) {
                            System.out.println("[!] ERROR: Amount cannot be negative.");
                        }
                    } else {
                        System.out.println("[!] ERROR: Invalid amount.");
                        inFile.next();
                    }
                }
                target.setAmount(newAmount);
                break;
            case 3:
                LocalDate today = LocalDate.now();
                LocalDate oneYearAgo = today.minusYears(1);
                LocalDate newDate = null;
                while (newDate == null) {
                    System.out.print("New date (YYYY-MM-DD): ");
                    String newDateInput = inFile.nextLine().trim();
                    try {
                        LocalDate candidate = LocalDate.parse(newDateInput);
                        if (candidate.isBefore(oneYearAgo)) {
                            System.out.println("[!] ERROR: Date cannot be more than one year ago.");
                            continue;
                        }
                        if (candidate.isAfter(today)) {
                            System.out.println("[!] ERROR: Date cannot be in the future.");
                            continue;
                        }
                        newDate = candidate;
                    } catch (DateTimeParseException e) {
                        System.out.println("[!] ERROR: Invalid format. Use YYYY-MM-DD.");
                    }
                }
                target.setDate(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
                Collections.sort(monthList[index],
                    new Comparator<Expense>() {
                        public int compare(Expense a, Expense b) {
                            return a.getDate().compareTo(b.getDate());
                        }
                    }
                );
                break;
            case 4:
                target.setPaid(!target.isPaid());
                System.out.println("Paid status set to: " +
                    (target.isPaid() ? "Paid" : "Unpaid"));
                break;
            case 5:
                target.setIncome(!target.isIncome());
                System.out.println("Income status set to: " + (target.isIncome() ? "Income" : "Expense"));
                break;
            case 6:
                target.setRecurring(!target.isRecurring());
                System.out.println("Recurring status set to: " + (target.isRecurring() ? "Recurring" : "Not recurring"));
                break;  
        }
        String[] fileNames = {
        	    "JanuaryExpenses.txt", "FebruaryExpenses.txt", "MarchExpenses.txt",
        	    "AprilExpenses.txt", "MayExpenses.txt", "JuneExpenses.txt",
        	    "JulyExpenses.txt", "AugustExpenses.txt", "SeptemberExpenses.txt",
        	    "OctoberExpenses.txt", "NovemberExpenses.txt", "DecemberExpenses.txt"
        	};
        	SaveAsTXT.saveToFile(monthList[index], fileNames[index]);
        	
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Expense modified successfully.");
    }
}
