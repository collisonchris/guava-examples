package org.gradle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

public class CollectionsTest {
    
    private static final ImmutableSet<String> RGB = ImmutableSet.of("Red", "Green", "Blue");
    
    private static final ImmutableSet<String> CYMK = ImmutableSet.of("Cyan", "Yellow", "Magenta", "Black");
    
    private static List<String> words = Lists.newArrayList();
    
    private Person chris;
    
    private Pet fido;
    private Pet rover;
    private Pet socks;
    private Pet boots;
    private Pet petey;
    
    @Before
    public void before() {
        chris = new Person("Christopher", 23, Boolean.FALSE, "Chris");
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
    
    @SuppressWarnings("unused")
    @Test
    public void testStaticCollectionConstructors() {
        List<String> exampleList = Lists.newArrayList();
        exampleList = Lists.newLinkedList();
        
        Set<String> exampleSet = Sets.newHashSet();
        exampleSet = Sets.newLinkedHashSet();
        exampleSet = Sets.newTreeSet();
        
        Map<String, String> exampleMap = Maps.newHashMap();
        exampleMap = Maps.newLinkedHashMap();
        exampleMap = Maps.newTreeMap();

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
        //old way
        Map<Person, List<Pet>> personPetListMap = new HashMap<Person, List<Pet>>();
        List<Pet> chrisList = new ArrayList<Pet>();
        chrisList.add(fido);
        chrisList.add(rover);
        chrisList.add(socks);
        chrisList.add(boots);
        chrisList.add(petey);
        personPetListMap.put(chris, chrisList);
        
        assertTrue(personPetListMap.get(chris).size() == 5);
        
        personPetListMap.get(chris).remove(petey);
        assertTrue(personPetListMap.get(chris).size() == 4);
        
        personPetListMap.get(chris).removeAll(chrisList);
        assertTrue(personPetListMap.get(chris).size() == 0);
        
        //guava way, the child collection is determined by implementation type chosen here
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

        //counting the times words appear in a list manually
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
    
    @Test
    public void testBiMapOperations() {
        /* Test with non-empty Map. */
        Map<String, String> map = ImmutableMap.of(
            "canada", "dollar",
            "chile", "peso",
            "switzerland", "franc");
        HashBiMap<String, String> bimap = HashBiMap.create(map);
        assertEquals("dollar", bimap.get("canada"));
        //flip to check based on inverse
        assertEquals("canada", bimap.inverse().get("dollar"));
    }
    
    @Test 
    public void testMapMaker() {
        Map<String, String> childMap = Maps.newHashMap();
        childMap.put("Iowa", "Hawkeyes");
        childMap.put("ISU", "Cyclones");
        childMap.put("UNI", "Panthers");
        
        Map<String, Map<String, String>> computingMap = makeComputingMap();
        computingMap.put("Iowa Colleges", childMap);
        
        
        Map<String, Map<String, String>> anotherComputingMap = makeComputingMapNew();
        anotherComputingMap.put("Iowa Colleges", childMap);
        
        //different implementation, but on the surface, same result
        assertEquals(computingMap, anotherComputingMap);
    }
    
    
    /* Deprecated behavior! */
    @SuppressWarnings("deprecation")
    public static Map<String, Map<String, String>> makeComputingMap() {
        return new MapMaker().makeComputingMap(new Function<String, Map<String, String>>() {
            public Map<String, String> apply(String input) {
                return Maps.newHashMap();
            }
        });
    }
    
    /* non deprecated behavior! */
    public static Map<String, Map<String, String>> makeComputingMapNew() {
        return new MapMaker().makeMap();
    }
    
    @Test
    public void interablesTransfomTests() {
        List<Double> prices = Lists.newArrayList(Arrays.asList(20.00, 25.00, 40.00));
        
        //functional guava way
        List<Double> discountedPrices = Lists.newArrayList(Iterables.transform(prices, new Function<Double, Double>() {
            public Double apply(final Double from) {
                return from *.90;
            }
        }));
        //manual way
        List<Double> manuallyDiscountedPrices = Lists.newArrayList();
        
        for(Double price: prices) {
            manuallyDiscountedPrices.add(price * .90);
        }

        //different implementation, same results
        assertTrue(discountedPrices.containsAll(manuallyDiscountedPrices));
        assertTrue(manuallyDiscountedPrices.containsAll(discountedPrices));
    }
    
}
