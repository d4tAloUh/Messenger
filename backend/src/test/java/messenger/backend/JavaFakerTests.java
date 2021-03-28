package messenger.backend;

import com.github.javafaker.Faker;
import messenger.backend.seeds.Tuple;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


public class JavaFakerTests {


    @Test
    public void generateRandomText(){

        Tuple first = new Tuple(5,10);
        Tuple second = new Tuple(10,5);

        System.out.println("Does first equals second: " + first.equals(second));

        Set<Tuple> set = new HashSet<>();

        set.add(first);
        set.add(second);

        System.out.println(set);
    }
}
