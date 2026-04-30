// OverallExpense - displays the overall expenses page in terminal format
// This class ONLY handles displaying data, not modifying or storing it

public class OverallExpense 
{
    // constant that limits how many expenses are shown per page
    private static final int MAX_ITEMS = 10;

    // ========================================
    //             OVERALL EXPENSE
    // ========================================
    private static void printHeader(String title, String subtitle)
    {
        int width = 40; // total width of header line

        // spacing before header
        System.out.println();

        // print top border line
        System.out.println("=".repeat(width));

        // center and print title text
        System.out.printf("%" + ((width + title.length()) / 2) + "s%n", title);

        // print bottom border line
        System.out.println("=".repeat(width));

        // spacing after header
        System.out.println();
    }

    /*
     * displayOverallExpense - displays monthly expenses with pagination
     * input: MonthlyBreakdown currentMonth, int startIndex; output: none
     */
    public static void displayOverallExpense(MonthlyBreakdown currentMonth, int startIndex)
    {
        // ===== HEADER =====
        // calls helper method to display formatted title section
        printHeader("OVERALL EXPENSE", "This shows monthly data");

        // ===== MONTH DISPLAY =====
        // displays the current month name (no year per requirement)
        System.out.println("Month: " + currentMonth.name);
        System.out.println(); // spacing for readability

        // ===== TOTAL SPENDING =====
        // retrieves total spending for the entire month from MonthlyBreakdown
        float totalSpending = currentMonth.getTotalExpenses();

        // prints total formatted to 2 decimal places
        System.out.printf("Total Spending: $%.2f%n%n", totalSpending);

        // ===== EXPENSE LIST HEADER =====
        // prints column labels for expense table
        System.out.println("Expenses:");
        System.out.println("----------------------------------------");

        // formatted columns: index, name, amount
        System.out.printf("%-5s %-15s %-10s%n", "#", "Name", "Amount");
        System.out.println("----------------------------------------");

        // ===== PAGINATION SETUP =====
        // tracks number of items printed (limit = MAX_ITEMS)
        int itemsShown = 0;

        // convert from 1-based index (user view) to 0-based index (list access)
        int expenseIndex = startIndex - 1;

        // ===== DISPLAY EXPENSES =====
        // loops through list and prints up to MAX_ITEMS (10) expenses
        while (expenseIndex < currentMonth.monthlyExpenses.size() && itemsShown < MAX_ITEMS)
        {
            // retrieve current expense object
            Expense currentExpense = currentMonth.monthlyExpenses.get(expenseIndex);

            // print formatted row with index, name, and amount
            System.out.printf("%-5d %-15s $%-10.2f%n",
                    expenseIndex + 1,              // display index (1-based)
                    currentExpense.getName(),      // expense name
                    currentExpense.getAmount());   // expense amount

            // move to next expense in list
            expenseIndex++;

            // increment count of displayed items
            itemsShown++;
        }

        // ===== EMPTY STATE =====
        // if no expenses were displayed, notify the user
        if (itemsShown == 0)
        {
            System.out.println("No expenses found for this month.");
        }

        // ===== FOOTER =====
        // separator line for clean UI
        System.out.println("----------------------------------------");

        // ===== USER OPTIONS =====
        // options displayed to user (handled elsewhere, e.g., Driver.java)
        System.out.println();
        System.out.println("Options:");
        System.out.println("1. Return to Home Page");   // navigates back to main menu
        System.out.println("2. List Next 10 Items");    // shows next page of expenses

        // prompt user input (input handling NOT done here)
        System.out.print("Enter your choice: ");
    }
}