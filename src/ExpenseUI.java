import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Scanner;

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
            System.out.printf("\n%s\n", "-".repeat(50));
            System.out.printf("%32s\n", "EXPENSE MENU");
            System.out.printf("%s\n", "-".repeat(50));
            System.out.printf("\n\t%s\n", "Options:");
            System.out.printf("\t%s\n", "  1. Add Expense");
            System.out.printf("\t%s\n", "  2. Modify Expense");
            System.out.printf("\t%s\n", "  3. Delete Expense");
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
    public static void addExpense(Scanner inFile, LinkedList<Expense>[] monthList) {
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.printf("%32s\n", "ADD EXPENSE");
        System.out.printf("%s\n\n", "-".repeat(50));

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
        System.out.print("Enter date (YYYY-MM-DD), or press Enter for today: ");
        String dateInput = inFile.nextLine().trim();
        LocalDate parsedDate;
        if (dateInput.isEmpty()) {
            parsedDate = LocalDate.now();
        } else {
            parsedDate = null;
            while (parsedDate == null) {
                try {
                    parsedDate = LocalDate.parse(dateInput);
                } catch (DateTimeParseException e) {
                    System.out.print("[!] ERROR: Use YYYY-MM-DD. Try again: ");
                    dateInput = inFile.nextLine().trim();
                }
            }
        }
        
        int month = parsedDate.getMonthValue();

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

        // create Expense object and add to list
        Expense newExpense = new Expense(
            name, amount,
            parsedDate.getYear(),
            parsedDate.getMonthValue(),
            parsedDate.getDayOfMonth(),
            paid
        );
        monthList[month-1].add(newExpense);

        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Expense added successfully.");
    }

    //
    // deleteExpense - shows all expenses, user picks one to delete
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void deleteExpense(Scanner inFile, LinkedList<Expense>[] monthList) {
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.printf("%32s\n", "DELETE EXPENSE");
        System.out.printf("%s\n\n", "-".repeat(50));
        
        System.out.print("\nEnter the number of the month with the expense you want to delete, or 0 to cancel: ");
        int month = -1;
        if (inFile.hasNextInt()) {
        	month = inFile.nextInt();
        }
        if (month == 0) return;
        if (month < 1 || month > 12) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }
        
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

        monthList[month].remove(choice - 1);
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Expense deleted successfully.");
    }

    //
    // modifyExpense - shows all expenses, user picks one and edits a field
    // Scanner inFile, LinkedList<Expense> monthList; void
    //
    public static void modifyExpense(Scanner inFile, LinkedList<Expense>[] monthList) {
        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.printf("%32s\n", "MODIFY EXPENSE");
        System.out.printf("%s\n\n", "-".repeat(50));

        System.out.print("\nEnter the number of the month with the expense you want to modify, or 0 to cancel: ");
        int month = -1;
        if (inFile.hasNextInt()) {
        	month = inFile.nextInt();
        }
        if (month == 0) return;
        if (month < 1 || month > 12) {
            System.out.println("[!] ERROR: Invalid selection.");
            return;
        }
        
        if (monthList[month].isEmpty()) {
            System.out.println("No expenses have been entered yet.");
            return;
        }

        // display all expenses
        System.out.println("Existing Expenses:");
        int count = 1;
        for (Expense e : monthList[month]) {
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

        Expense target = monthList[month].get(choice - 1);

        // show edit options
        System.out.printf("\nEditing: %s - $%.2f - %s\n\n",
            target.getName(), target.getAmount(), target.getDate().toString());
        System.out.println("  1. Edit name");
        System.out.println("  2. Edit amount");
        System.out.println("  3. Edit date");
        System.out.println("  4. Toggle paid status");
        int field = Driver.getMenuOption(4, inFile);

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
                System.out.print("New date (YYYY-MM-DD): ");
                String newDateInput = inFile.nextLine().trim();
                try {
                    LocalDate newDate = LocalDate.parse(newDateInput);
                    target.setDate(
                        newDate.getYear(),
                        newDate.getMonthValue(),
                        newDate.getDayOfMonth()
                    );
                } catch (DateTimeParseException e) {
                    System.out.println("[!] ERROR: Invalid format. Date not updated.");
                }
                break;
            case 4:
                target.setPaid(!target.isPaid());
                System.out.println("Paid status set to: " +
                    (target.isPaid() ? "Paid" : "Unpaid"));
                break;
        }

        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Expense modified successfully.");
    }
}
