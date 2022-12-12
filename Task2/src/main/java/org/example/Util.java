package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Properties;

public class Util {

    public static <T> T loadFromProperties(Class<T> cls, Path propertiesFile) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> c = cls.getConstructor();
        T obj = c.newInstance();

        try (InputStream input = Util.class.getClassLoader().getResourceAsStream(propertiesFile.getFileName().toString())) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + propertiesFile.getFileName().toString());
                return null;
            }

            prop.load(input);
            prop.forEach((key, value) -> {
                Field nameField;
                try {
                    nameField = cls.getDeclaredField(key.toString());
                    nameField.setAccessible(true);
                    Object parsedValue = ParseUtils.parse(nameField.getType(), value.toString());
                    nameField.set(obj, parsedValue);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("NoSuchField " + key + "in " + cls.getName() + " ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return obj;
    }
}
