/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(5);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    //@Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setB.add(11));

        assertFalse(setB.contains(11), "add: added element not found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");
    }

    //@Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @DisplayName("Test if a valid array contains duplicates")
    @Test
    public void testAddDuplicates() {
        int[] elems = new int[]{10, 30, 30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setD.add(elems));

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setD.add(30));
    }

    @DisplayName("Test if the bounded set is full")
    @Test
    public void testIsFull() {
        int[] elems = new int[]{10, 20, 30, 40, 50, 60};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setD.add(elems));
    }

    @DisplayName("Test if two sets intersect")
    @Test
    public void testIntersect() {
        assertTrue(setB.intersects(setC));
        assertFalse(setA.intersects(setC));
    }

    @DisplayName("Test the size of the set")
    @Test
    public void testSize() {
        assertEquals(0, setA.size());
        assertEquals(6, setB.size());
        assertEquals(2, setC.size());
        assertEquals(0, setD.size());
    }
}
