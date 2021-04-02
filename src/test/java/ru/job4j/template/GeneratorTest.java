package ru.job4j.template;

import org.junit.Test;
import ru.job4j.tdd.*;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GeneratorTest {

    @Test
    public void produceNormal() {
        Generator generator = new SimpleGenetator();
        Map<String, String> args = Map.of("${name}", "Andrey Shalygin", "${subject}", "you");
        String template = "I am a ${name}, Who are ${subject}?";
        String expected = "I am a Andrey Shalygin, Who are you?";
        String result = generator.produce(template, args);
        assertEquals(result, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void produceNoKeysInMap() {
        Generator generator = new SimpleGenetator();
        Map<String, String> args = Map.of("${name}", "Andrey Shalygin");
        String template = "I am a ${name}, Who are ${subject}?";
        String expected = "I am a Andrey Shalygin, Who are you?";
        String result = generator.produce(template, args);
        assertEquals(result, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void produceMoreKeysInMap() {
        Generator generator = new SimpleGenetator();
        Map<String, String> args = Map.of("${name}", "Andrey Shalygin", "${subject}", "you",
                "${surname}" , "Ivanovich");
        String template = "I am a ${name}, Who are ${subject}?";
        String expected = "I am a Andrey Shalygin, Who are you?";
        String result = generator.produce(template, args);
        assertEquals(result, is(expected));
    }
}
