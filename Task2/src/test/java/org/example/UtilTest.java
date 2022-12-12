package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void validLoadFromProperties() throws NoSuchMethodException, IllegalArgumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Path propFile = Path.of("Task2/src/test/resources/valid.properties");
        Example example = Util.loadFromProperties(Example.class, propFile);
        assertNotNull(example);
        assertEquals(example.getNumberProperty(), 10);
        assertEquals(example.getStringProperty(), "value1");
        assertEquals(example.getTimeProperty(), ParseUtils.parse(Instant.class, "29.11.2022 18:30"));
    }

    @Test()
    public void NoSuchFieldException() throws Exception {
        Path propFile = Path.of("Task2/src/test/resources/invalid.properties");

        assertThrows(RuntimeException.class, () -> Util.loadFromProperties(Example.class, propFile));

    }
//    @Test
//    void testInvalidInput() {
//        Path validPath = Path.of("src/test/resources/invalid/person_illegalArg.properties");
//        assertThrows(IllegalArgumentException.class,
//                ()->Loader.loadFromProperties(Person.class, validPath));
//    }
//
//    @Test
//    void noSetterShouldThrowException() {
//        class NoSetterClass {
//            @Property
//            private int value;
//        }
//        Path path = Path.of("src/test/resources/invalid/noSetter.properties");
//        assertThrows(NoSuchMethodException.class,
//                ()-> Loader.loadFromProperties(NoSetterClass.class, path));
//    }
//    @Test
//    void propertiesNotFoundCase() {
//        Path validPath = Path.of("src/test/resources/invalid/none.properties");
//        assertThrows(FileNotFoundException.class,
//                ()-> Loader.loadFromProperties(Person.class, validPath));
//    }


}