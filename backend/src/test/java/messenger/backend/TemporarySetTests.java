package messenger.backend;

import messenger.backend.seeds.Tuple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TemporarySetTests {


    @Test
    public void checkTupleSetForDuplicates(){

        Tuple first = new Tuple(5,10);
        Tuple second = new Tuple(10,5);

        Set<Tuple> set = new HashSet<>();

        set.add(first);
        set.add(second);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(),second.hashCode());
        assertEquals(set.size(),1);
    }

    @Test
    public void test(){
        log.debug("testing");
        assertTrue((true || true));
    }

    @Test
    public void checkHashSetListForDuplicates(){
        Set<List<Integer>> setList = new HashSet<>();

        List<Integer> list1 = new ArrayList<>(){
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }
        };

        List<Integer> list2 = new ArrayList<>(){
            {
                add(5);
                add(4);
                add(3);
                add(2);
                add(1);
            }
        };

        assertFalse(list1.equals(list2));
        assertNotEquals(list1.hashCode(),list2.hashCode());

        setList.add(list1);
        setList.add(list2);

        assertEquals(setList.size(),2);
        log.info("SetList is {}", setList);
    }


}
