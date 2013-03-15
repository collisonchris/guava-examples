package org.gradle;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {
    Person chris;
    Person tom;
    Person jerry;
    Person chrisClone;

    @Before
    public void before() {
        chris = new Person("Christopher", 23, Boolean.FALSE, "Chris");
        chrisClone = new Person("Christopher", 23, Boolean.FALSE, "Chris");
        tom = new Person("Thomas", 19, Boolean.TRUE, "Tom");
        jerry = new Person("Jerry", 23, Boolean.TRUE, "J");
        
    }

    @Test
    public void getPreferredNameShort() {
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
    
    @Test
    public void printPerson() {
        assertEquals("Person{name=Christopher, age=23, isTall=false, shortName=Chris}", chris.toString());
    }
    
    @Test
    public void comparatorTest() {
        //both work functionally the same way
        assertEquals(1,chris.compareTo(tom));
        assertEquals(1,chris.oldCompareTo(tom));
        
        assertEquals(-1,chris.compareTo(jerry));
        assertEquals(-1,chris.oldCompareTo(jerry));
        
        assertEquals(0,chris.compareTo(chrisClone));
        assertEquals(0,chris.oldCompareTo(chrisClone));
        
        
    }

}
