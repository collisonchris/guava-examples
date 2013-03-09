package org.gradle;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

public class CollectionsTest {
    
    private static final ImmutableSet<String> SEASONS = ImmutableSet.of("Winter", "Spring", "Summer","Fall");
    
    private static final ImmutableSet<String> RGB = ImmutableSet.of("Red", "Green", "Blue");
    
    private static final ImmutableSet<String> CYMK = ImmutableSet.of("Cyan", "Yellow", "Magenta", "Black");
    
    private static List<String> words = Lists.newArrayList();
    
    private Person chris;
    private Person tom;
    
    private Pet fido;
    private Pet rover;
    private Pet socks;
    private Pet boots;
    private Pet petey;
    
    @Before
    public void before() {
        chris = new Person("Christopher", 23, Boolean.FALSE, "Chris");
        tom = new Person("Thomas", 19, Boolean.TRUE, null);
        fido = new Pet("Fido", PetType.DOG);
        rover = new Pet("Rover", PetType.DOG);
        socks = new Pet("Socks", PetType.CAT);
        boots = new Pet("Boots", PetType.CAT);
        petey = new Pet("Petey", PetType.BIRD);
    }
    
    @Test
    public void testImmutableSetBuilder() {
          ImmutableSet<String> LOTS_OF_COLORS = 
                ImmutableSet.<String>builder()
                    .addAll(RGB)
                    .addAll(CYMK)
                    .add("Orange")
                    .build();
          
          assertTrue(LOTS_OF_COLORS.contains("Red"));
    }
    
    
    /**
     * Available concrete implementations for Multimaps:
     * HashMap->HashMultiSet,allows null elements
     * TreeMap->TreeMultiSet,allows null elements
     * LinkedHashMap->LinkedHashMultiset, allows null elements
     * ConcurrentHashMap->ConcurrentHashMultiset, does not allow null elements
     * ImmutableMap->ImmutableMultiset, does not allow null elements
     */
    @Test
    public void testMultiMapListOperations() {
        Multimap<Person, Pet> personPetMap = ArrayListMultimap.create();
        personPetMap.put(chris, fido);
        personPetMap.put(chris, rover);
        personPetMap.put(chris, socks);
        personPetMap.put(chris, boots);
        personPetMap.put(chris, petey);
        
        assertTrue(personPetMap.size() == 5);
        
        //get collection of pets
        Collection<Pet> myPets = personPetMap.get(chris);
        
        assertTrue(myPets.size() == 5);

        //petey's head fell off
        personPetMap.remove(chris, petey);
        
        assertTrue(personPetMap.size() == 4);
        assertTrue(myPets.size() == 4);
        
        //no more pets for Chris!
        personPetMap.removeAll(chris);
        
        assertTrue(personPetMap.size() == 0);
    }
    
    @Test
    public void testMulitSetOperations() {
        words.addAll(Arrays.asList("The", "The", "The", "taco", "taco", "was", "tasty"));

        //counting the times words appear in a list
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String word : words) {
          Integer count = counts.get(word);
          if (count == null) {
            counts.put(word, 1);
          } else {
            counts.put(word, count + 1);
          }
        }
        assertTrue(counts.get("The") == Integer.valueOf(3));
        assertTrue(counts.get("taco") == Integer.valueOf(2));
        assertTrue(counts.get("was") == Integer.valueOf(1));
        assertTrue(counts.get("tasty") == Integer.valueOf(1));
        
        //pretty unwieldy! how about this instead?
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(words);

        assertTrue(wordsMultiset.count("The") == 3);
        assertTrue(wordsMultiset.count("taco") == 2);
        assertTrue(wordsMultiset.count("was") == 1);
        assertTrue(wordsMultiset.count("tasty") == 1);
        
    }
    
}
