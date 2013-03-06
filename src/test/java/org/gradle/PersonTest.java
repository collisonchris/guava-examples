package org.gradle;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void canConstructAPersonWithANameAgeAndTall() {
        Person person = new Person("Christopher", 23, Boolean.TRUE, "Chris");
        assertEquals("Christopher", person.getName());
    }
}
