package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ParseUtils {
    private static final Locale locale = Locale.UK;

    private static Object nullValue(Class type) {
        if (type.isPrimitive()) {
            if (type == boolean.class)
                return Boolean.FALSE;
            if (type == byte.class)
                return (byte) 0;
            if (type == char.class)
                return (char) 0;
            if (type == short.class)
                return (short) 0;
            if (type == int.class)
                return 0;
            if (type == long.class)
                return 0L;
            if (type == float.class)
                return (float) 0;
            if (type == double.class)
                return (double) 0;
        }
        return null;
    }

    private static Class objectType(Class type) {
        if (type.isPrimitive()) {
            if (type == boolean.class)
                return Boolean.class;
            if (type == byte.class)
                return Byte.class;
            if (type == char.class)
                return Character.class;
            if (type == short.class)
                return Short.class;
            if (type == int.class)
                return Integer.class;
            if (type == long.class)
                return Long.class;
            if (type == float.class)
                return Float.class;
            if (type == double.class)
                return Double.class;
        }
        return type;
    }

    private static Object parse(Format format, String value) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Object result = format.parseObject(value, pos);
        if (pos.getIndex() < value.length())
            throw new ParseException("Cannot parse " + value + " (garbage suffix)!", pos.getIndex());
        return result;
    }

    private static Date parseDate(String value) throws ParseException {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return (Date) parse(format, value);
    }

    private static Boolean parseBoolean(String value) throws ParseException {
        if ("true".equals(value)) {
            return Boolean.TRUE;
        } else if ("false".equals(value)) {
            return Boolean.FALSE;
        } else {
            throw new ParseException("Cannot parse '" + value + "' as boolean", 0);
        }
    }

    private static Character parseCharacter(String value) throws ParseException {
        if (value.length() != 1) {
            throw new ParseException("Cannot parse '" + value + "' as character", value.length());
        }
        return new Character(value.charAt(0));
    }

    private static Object parseInstant(String value) {
        String pattern = "d.M.uuuu HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(value, dateTimeFormatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    /**
     * Parse value of specified type. The string value has to be in
     * standard notation for the specified type.
     */
    public static Object parse(Class type, String value) throws ParseException {
        try {
            if (value == null) {
                return nullValue(type);
            } else if (value.length() == 0) {
                return type == String.class ? value : nullValue(type);
            }

            type = objectType(type);

            if (type == BigDecimal.class) {
                return new BigDecimal(value);
            } else if (type == BigInteger.class) {
                return new BigInteger(value);
            } else if (type == Boolean.class) {
                return parseBoolean(value);
            } else if (type == Byte.class) {
                return Byte.valueOf(value);
            } else if (type == Character.class) {
                return parseCharacter(value);
            } else if (type == Date.class) {
                return parseDate(value);
            } else if (type == Double.class) {
                return Double.valueOf(value);
            } else if (type == Float.class) {
                return Float.valueOf(value);
            } else if (type == Integer.class) {
                return Integer.valueOf(value);
            } else if (type == Long.class) {
                return Long.valueOf(value);
            } else if (type == Short.class) {
                return Short.valueOf(value);
            } else if (type == Instant.class) {
                return parseInstant(value);
            } else if (type == String.class) {
                return value;
            }
        } catch (ParseException e) {
            throw new ParseException("Cannot parse type because field not match type " + type, 0);
        }
        throw new ParseException("Cannot parse type not supported parse type " + type, 0);
    }


}
