package spbau.eliseeva.mock;

import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/** Tests Calculator class and its methods using mock objects.*/
public class CalculatorTest {
    /**
     * Tests evaluate method. Creates two mock objects instead of the stacks and
     * evaluates an expression in polish notation using them. Checks the answer
     * and that needed methods were called for mock objects.
     */
    @Test
    public void evaluateTest() {
        Stack<Integer> mock1 = mock(Stack.class);
        Stack<Character> mock2 = mock(Stack.class);
        String expression = "1 2 19 + 9 3 / - 2 * - ";
        when(mock1.top()).thenReturn(19, 2, 3, 9, 3, 21, 2, 18, 36, 1, -35);
        assertEquals(-35, new Calculator(mock1, mock2).evaluate(expression));
        verify(mock1, times(1)).push(1);
        verify(mock1, times(2)).push(2);
        verify(mock1, times(2)).push(3);
        verify(mock1, times(1)).push(9);
        verify(mock1, times(1)).push(18);
        verify(mock1, times(1)).push(19);
        verify(mock1, times(1)).push(21);
        verify(mock1, times(1)).push(36);
        verify(mock1, times(1)).push(-35);
        verify(mock1, times(10)).pop();
        verify(mock2, never()).push(any());
        verify(mock2, never()).pop();
        verify(mock1, times(11)).top();
        verify(mock2, never()).top();
        verify(mock1, times(10)).isEmpty();
        verify(mock2, never()).isEmpty();
    }

    /**
     * Tests evaluate method. Creates two mock objects instead of the stacks and
     * converts an expression into polish notation using them. Checks the answer
     * and that needed methods were called for mock objects.
     */
    @Test
    public void toPolishTest() {
        Stack<Integer> mock1 = mock(Stack.class);
        Stack<Character> mock2 = mock(Stack.class);
        String expression = "1-4*(21+3)";
        when(mock1.top()).thenReturn(1, 4, 21, 3);
        when(mock2.top()).thenReturn('(', '(', '-', '*', '(', '(', '+',
                '+', '+', '(', '(', '(', '*', '*', '*', '-', '-', '-', '(', '(', '(');
        when(mock2.isEmpty()).thenReturn(false, false, false, false,
                false, false, false, false, false, true);
        assertEquals("1 4 21 3 + * - ", new Calculator(mock1, mock2).toPolish(expression));
        verify(mock1, never()).isEmpty();
        verify(mock2, times(10)).isEmpty();
        verify(mock1, times(1)).push(1);
        verify(mock1, times(1)).push(4);
        verify(mock1, times(1)).push(21);
        verify(mock1, times(1)).push(3);
        verify(mock2, times(2)).push('(');
        verify(mock2, times(1)).push('-');
        verify(mock2, times(1)).push('*');
        verify(mock2, times(1)).push('+');
        verify(mock1, never()).pop();
        verify(mock2, times(5)).pop();
        verify(mock1, times(4)).top();
        verify(mock2, times(21)).top();
    }


    /**
     * Checks if check method works correct for
     * expressions with 'good' and 'bad' symbols.
     */
    @Test
    public void checkTest() {
        assertEquals(true, Calculator.check("123-456*1-(23/2)"));
        assertEquals(false, Calculator.check("123-aaa"));
    }

}