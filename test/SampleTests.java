import com.encora.expenses.exceptions.InvalidEmployeeIdException;
import com.encora.expenses.utilities.EmployeeUtilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTests {

    @Test
    public void testSomething() {

        int a = 4;
        int b = 8;

        int tot = a+b;

        assertEquals(12, tot);

    }

    @Test
    public void testEmployeeIdNumberIsConvertedCorrectly() throws InvalidEmployeeIdException {
        EmployeeUtilities employeeUtilities = new EmployeeUtilities();

        int result = employeeUtilities.validateEmployeeId("416");
        assertEquals(416, result);
    }

    @Test
    public void testThatExceptionIsThrownIfEmployeeIfIsNotAValidNumber() {
        EmployeeUtilities employeeUtilities = new EmployeeUtilities();

        assertThrows(InvalidEmployeeIdException.class, () -> {
            int result = employeeUtilities.validateEmployeeId("hola");
        });
    }
}
