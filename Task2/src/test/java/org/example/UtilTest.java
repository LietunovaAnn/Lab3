package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void validLoadFromProperties() throws NoSuchMethodException, IllegalArgumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Path propFile = new File("src/test/resources/valid.properties").toPath();
        Example example = Util.loadFromProperties(Example.class, propFile);
        assertNotNull(example);
        assertEquals(example.getNumberProperty(), 10);
        assertEquals(example.getStringProperty(), "value1");
        assertEquals(example.getTimeProperty(), ParseUtils.parse(Instant.class, "29.11.2022 18:30"));
    }

    @Test()
    public void testNoSuchFieldException() {
        Path propFile = new File("src/test/resources/invalid.properties").toPath();

        assertThrows(RuntimeException.class, () -> Util.loadFromProperties(Example.class, propFile));

    }

}