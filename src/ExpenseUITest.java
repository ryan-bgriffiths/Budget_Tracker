
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

//
// ExpenseUITest - JUnit tests for user story 2 (add, modify, delete expenses)
// Dependencies: Expense; none
//
public class ExpenseUITest {

    private LinkedList<Expense> monthList;

    @BeforeEach
    public void setUp() {
        monthList = new LinkedList<>();
    }

    // add tests
    @Test
    public void testAddExpense_listNotEmpty() {
        monthList.add(new Expense("Groceries", 50.00f, 2026, 4, 25, false));
        assertFalse(monthList.isEmpty());
    }

    @Test
    public void testAddExpense_correctName() {
        monthList.add(new Expense("Gas", 40.00f, 2026, 4, 25, false));
        assertEquals("Gas", monthList.get(0).getName());
    }

    @Test
    public void testAddExpense_correctAmount() {
        monthList.add(new Expense("Rent", 800.00f, 2026, 4, 1, true));
        assertEquals(800.00f, monthList.get(0).getAmount());
    }

    @Test
    public void testAddExpense_correctPaidStatus() {
        monthList.add(new Expense("Internet", 60.00f, 2026, 4, 10, true));
        assertTrue(monthList.get(0).isPaid());
    }

    @Test
    public void testAddMultipleExpenses() {
        monthList.add(new Expense("Groceries", 50.00f, 2026, 4, 25, false));
        monthList.add(new Expense("Gas", 40.00f, 2026, 4, 25, false));
        assertEquals(2, monthList.size());
    }

    // delete tests
    @Test
    public void testDeleteExpense_listBecomesEmpty() {
        monthList.add(new Expense("Groceries", 50.00f, 2026, 4, 25, false));
        monthList.remove(0);
        assertTrue(monthList.isEmpty());
    }

    @Test
    public void testDeleteExpense_correctItemRemoved() {
        monthList.add(new Expense("Groceries", 50.00f, 2026, 4, 25, false));
        monthList.add(new Expense("Gas", 40.00f, 2026, 4, 25, false));
        monthList.remove(0);
        assertEquals("Gas", monthList.get(0).getName());
    }

    // modify tests
    @Test
    public void testModifyExpense_name() {
        monthList.add(new Expense("Groceries", 50.00f, 2026, 4, 25, false));
        monthList.get(0).setName("Supermarket");
        assertEquals("Supermarket", monthList.get(0).getName());
    }

    @Test
    public void testModifyExpense_amount() {
        monthList.add(new Expense("Gas", 40.00f, 2026, 4, 25, false));
        monthList.get(0).setAmount(55.00f);
        assertEquals(55.00f, monthList.get(0).getAmount());
    }

    @Test
    public void testModifyExpense_togglePaid() {
        monthList.add(new Expense("Internet", 60.00f, 2026, 4, 10, false));
        boolean original = monthList.get(0).isPaid();
        monthList.get(0).setPaid(!original);
        assertNotEquals(original, monthList.get(0).isPaid());
    }
}