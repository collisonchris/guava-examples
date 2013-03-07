package org.gradle;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {
    Person chris;
    Person tom;

    @Before
    public void before() {
        chris = new Person("Christopher", 23, Boolean.FALSE, "Chris");
        tom = new Person("Thomas", 19, Boolean.TRUE, null);
    }

    @Test
    public void getPreferredNameShort() {
        tom.setShortName("Tom");
        assertEquals("Tom", tom.preferredName());
    }

    @Test
    public void getPrerredNameFull() {
        tom.setShortName(null);
        assertEquals("Thomas", tom.preferredName());
    }

    @Test
    public void canDrink() {
        assertTrue(chris.canDrink());
        assertFalse(tom.canDrink());
    }

    @Test(expected = IllegalStateException.class)
    public void canDrinkNullAge() {
        chris.setAge(null);

        chris.canDrink();
    }

}
