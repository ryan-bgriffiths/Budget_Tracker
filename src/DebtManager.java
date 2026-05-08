import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

// DebtManager - Handles debt dashboard, add, delete, modify, past debt, and debt details
// dependencies: Debt, LinkedList, Scanner, LocalDate; sub-classes: none
public class DebtManager {

    // showDebtMenu - Displays debt menu and routes user to debt options
    // Scanner, LinkedList<Debt>; void
    public static void showDebtMenu(Scanner input, LinkedList<Debt> debtList) {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\t╔══════════════════════════════╗");
            System.out.println("\t║    ****  DEBT MENU  ****     ║");
            System.out.println("\t╚══════════════════════════════╝\n");

            System.out.printf("\t%s\n", "Options:");
            System.out.printf("\t%s\n", "  1. View Debt Dashboard");
            System.out.printf("\t%s\n", "  2. Add Debt");
            System.out.printf("\t%s\n", "  3. Modify Debt");
            System.out.printf("\t%s\n", "  4. Delete Debt");
            System.out.printf("\t%s\n", "  5. View Past Debt");
            System.out.printf("\t%s\n", "  6. View Debt Details\n");

            choice = Driver.getMenuOption(6, input);
            input.nextLine();

            switch (choice) {
                case 1: displayDashboard(debtList); break;
                case 2: addDebt(debtList, input); break;
                case 3: modifyDebt(debtList, input); break;
                case 4: deleteDebt(debtList, input); break;
                case 5: viewPastDebt(debtList); break;
                case 6: viewDebtDetails(debtList, input); break;
            }
        }
    }

    // displayDashboard - Displays active debt entries and total active debt
    // LinkedList<Debt>; void
    public static void displayDashboard(LinkedList<Debt> debtList) {
        float totalDebt = 0;
        boolean foundActiveDebt = false;

        System.out.println("\n\t╔══════════════════════════════╗");
        System.out.println("\t║   ****  DEBT PANEL  ****     ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        System.out.println("--- Active Debt Entries ---");

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            if (debt.getStatus() == true) {
                foundActiveDebt = true;
                totalDebt += debt.remainingBalance;

                System.out.println("\nDebt #" + (i + 1));
                System.out.println("Name: " + debt.name);
                System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
                System.out.println("Status: Active");
            }
        }

        if (!foundActiveDebt) {
            System.out.println("No active debt entries found.");
        }

        System.out.printf("\nTotal Active Debt: $%,.2f\n", totalDebt);
        System.out.printf("\n%s\n", "-".repeat(50));
    }

    // addDebt - Adds a new debt entry
    // LinkedList<Debt>, Scanner; void
    public static void addDebt(LinkedList<Debt> debtList, Scanner input) {
        System.out.printf("\n%s\n", "-".repeat(50));

        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║     ****  ADD DEBT  ****     ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        System.out.print("Enter debt name: ");
        String name = input.nextLine();

        System.out.print("Enter principal amount: ");
        float principle = Float.parseFloat(input.nextLine());

        System.out.print("Enter remaining balance: ");
        float remainingBalance = Float.parseFloat(input.nextLine());

        System.out.print("Enter interest rate: ");
        float interestRate = Float.parseFloat(input.nextLine());

        System.out.println("Interest Type:");
        System.out.println("1. Simple Interest");
        System.out.println("2. Compound Interest");
        System.out.print("Choose (1 or 2): ");
        int interestChoice = Integer.parseInt(input.nextLine());

        boolean compoundOrSimple = interestChoice == 2;

        System.out.print("Enter minimum payment due date (YYYY-MM-DD): ");
        LocalDate dueDate = LocalDate.parse(input.nextLine());

        boolean status = true;
        LinkedList<Expense> paymentHistory = new LinkedList<Expense>();

        Debt debt = new Debt(name, principle, dueDate.getYear(),
                dueDate.getMonthValue(), dueDate.getDayOfMonth(),
                interestRate, compoundOrSimple, remainingBalance,
                status, paymentHistory);

        debtList.add(debt);

        System.out.printf("\n%s\n", "-".repeat(50));
        System.out.println("Debt added successfully.");
    }

    // deleteDebt - Deletes a debt entry
    // LinkedList<Debt>, Scanner; void
    public static void deleteDebt(LinkedList<Debt> debtList, Scanner input) {
        System.out.printf("\n%s\n", "-".repeat(50));

        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║   ****  DELETE DEBT  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to delete: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index >= 0 && index < debtList.size()) {
            debtList.remove(index);
            System.out.println("Debt deleted successfully.");
        } else {
            System.out.println("Invalid debt number.");
        }
    }

    // modifyDebt - Modifies an existing debt entry
    // LinkedList<Debt>, Scanner; void
    public static void modifyDebt(LinkedList<Debt> debtList, Scanner input) {
        System.out.printf("\n%s\n", "-".repeat(50));

        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║   ****  MODIFY DEBT  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to modify: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index < 0 || index >= debtList.size()) {
            System.out.println("Invalid debt number.");
            return;
        }

        Debt debt = debtList.get(index);

        System.out.print("Enter new debt name: ");
        debt.name = input.nextLine();

        System.out.print("Enter new principal amount: ");
        debt.principle = Float.parseFloat(input.nextLine());

        System.out.print("Enter new remaining balance: ");
        debt.remainingBalance = Float.parseFloat(input.nextLine());

        System.out.print("Enter new interest rate: ");
        debt.interestRate = Float.parseFloat(input.nextLine());

        System.out.println("Interest Type:");
        System.out.println("1. Simple Interest");
        System.out.println("2. Compound Interest");
        System.out.print("Choose (1 or 2): ");
        int interestChoice = Integer.parseInt(input.nextLine());

        debt.compoundOrSimple = interestChoice == 2;

        System.out.print("Enter new minimum payment due date (YYYY-MM-DD): ");
        debt.endDate = LocalDate.parse(input.nextLine());

        System.out.println("Debt Status:");
        System.out.println("1. Active");
        System.out.println("2. Inactive");
        System.out.print("Choose (1 or 2): ");
        int statusChoice = Integer.parseInt(input.nextLine());

        debt.status = (statusChoice == 1);

        System.out.println("Debt modified successfully.");
    }

    // viewPastDebt - Displays inactive debt entries
    // LinkedList<Debt>; void
    public static void viewPastDebt(LinkedList<Debt> debtList) {
        boolean foundPastDebt = false;

        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║    ****  PAST DEBT  ****     ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            if (debt.getStatus() == false) {
                foundPastDebt = true;

                System.out.println("\nDebt #" + (i + 1));
                System.out.println("Name: " + debt.name);
                System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
                System.out.println("Status: Inactive");
            }
        }

        if (!foundPastDebt) {
            System.out.println("No past debt entries found.");
        }
    }

    // viewDebtDetails - Displays details for one debt case
    // LinkedList<Debt>, Scanner; void
    public static void viewDebtDetails(LinkedList<Debt> debtList, Scanner input) {
        System.out.println("\t╔══════════════════════════════╗");
        System.out.println("\t║  ****  DEBT DETAILS  ****    ║");
        System.out.println("\t╚══════════════════════════════╝\n");

        displayAllDebts(debtList);

        if (debtList.isEmpty()) {
            return;
        }

        System.out.print("Enter debt number to view details: ");
        int index = Integer.parseInt(input.nextLine()) - 1;

        if (index < 0 || index >= debtList.size()) {
            System.out.println("Invalid debt number.");
            return;
        }

        Debt debt = debtList.get(index);

        System.out.println("\n===== DEBT CASE DETAILS =====");
        System.out.println("Name: " + debt.name);
        System.out.printf("Principal Amount: $%,.2f\n", debt.principle);
        System.out.printf("Remaining Balance: $%,.2f\n", debt.remainingBalance);
        System.out.printf("Interest Rate: %.2f%%\n", debt.interestRate);
        System.out.println("Interest Type: " + (debt.compoundOrSimple ? "Compound" : "Simple"));
        System.out.println("Next Payment Due Date: " + debt.endDate);
        System.out.println("Status: " + (debt.getStatus() ? "Active" : "Inactive"));
    }

    // displayAllDebts - Displays all debt entries
    // LinkedList<Debt>; void
    public static void displayAllDebts(LinkedList<Debt> debtList) {
        System.out.println("\n===== ALL DEBT ENTRIES =====");

        if (debtList.isEmpty()) {
            System.out.println("No debt entries found.");
            return;
        }

        for (int i = 0; i < debtList.size(); i++) {
            Debt debt = debtList.get(i);

            System.out.println("\nDebt #" + (i + 1));
            System.out.println("Name: " + debt.name);
            System.out.printf("Total Amount: $%,.2f\n", debt.remainingBalance);
            System.out.println("Status: " + (debt.getStatus() ? "Active" : "Inactive"));
        }
    }
}