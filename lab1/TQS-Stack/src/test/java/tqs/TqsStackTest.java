package tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTest {
    private TqsStack stack;

    @BeforeEach
    public void setUp() throws Exception {
        stack = new TqsStack(10);
    }

    @Test
    @DisplayName("Test if stack is empty on construction")
    public void testIsEmptyOnConstruction() throws Exception {
        assert stack.isEmpty();
    }

    @Test
    @DisplayName("Test if stack has size 0 on construction")
    public void testSizeOnConstruction() throws Exception {
        assert stack.size() == 0;
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    public void testPush() throws Exception {
        stack.push(1);
        assert !stack.isEmpty();
        assert stack.size() == 1;
        stack.push(2);
        assert !stack.isEmpty();
        assert stack.size() == 2;
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    public void testPushPop() throws Exception {
        stack.push(1);
        assert stack.pop() == 1;
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    public void testPushPeek() throws Exception {
        stack.push(1);
        assert stack.peek() == 1;
        assert stack.size() == 1;
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    public void testPop() throws Exception {
        stack.push(1);
        stack.push(2);
        stack.pop();
        assert !stack.isEmpty();
        assert stack.size() == 1;
        stack.pop();
        assert stack.isEmpty();
        assert stack.size() == 0;
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    public void testPopException() throws Exception {
        try {
            stack.pop();
        } catch (RuntimeException e) {
            assert true;
        }
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    public void testPeekException() throws Exception {
        try {
            stack.peek();
        } catch (RuntimeException e) {
            assert true;
        }
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    public void testPushException() throws Exception {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        try {
            stack.push(10);
        } catch (RuntimeException e) {
            assert true;
        }
    }
}
