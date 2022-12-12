package org.example;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


public class ParseUtilsTest extends TestCase {

    public ParseUtilsTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ParseUtilsTest.class);
    }

    public void testNullPrimitive() throws Exception {
        assertEquals(Boolean.FALSE, ParseUtils.parse(boolean.class, null));
        assertEquals(new Character((char) 0), ParseUtils.parse(char.class, null));
        assertEquals(new Byte((byte) 0), ParseUtils.parse(byte.class, null));
        assertEquals(new Short((short) 0), ParseUtils.parse(short.class, null));
        assertEquals(new Integer(0), ParseUtils.parse(int.class, null));
        assertEquals(new Long(0), ParseUtils.parse(long.class, null));
        assertEquals(new Float(0), ParseUtils.parse(float.class, null));
        assertEquals(new Double(0), ParseUtils.parse(double.class, null));
    }

    public void testPrimitive() throws Exception {
        assertEquals(Boolean.TRUE, ParseUtils.parse(boolean.class, "true"));
        assertEquals(Boolean.FALSE, ParseUtils.parse(boolean.class, "false"));
        assertEquals(new Character((char) 10), ParseUtils.parse(char.class, "\n"));
        assertEquals(new Byte((byte) 10), ParseUtils.parse(byte.class, "10"));
        assertEquals(new Short((short) 10), ParseUtils.parse(short.class, "10"));
        assertEquals(new Integer(10), ParseUtils.parse(int.class, "10"));
        assertEquals(new Long(10), ParseUtils.parse(long.class, "10"));
        assertEquals(new Float(10), ParseUtils.parse(float.class, "10"));
        assertEquals(new Double(10), ParseUtils.parse(double.class, "10"));
    }

    public void testNullObject() throws Exception {
        assertNull(ParseUtils.parse(Boolean.class, null));
        assertNull(ParseUtils.parse(Byte.class, null));
        assertNull(ParseUtils.parse(Character.class, null));
        assertNull(ParseUtils.parse(Short.class, null));
        assertNull(ParseUtils.parse(Integer.class, null));
        assertNull(ParseUtils.parse(Long.class, null));
        assertNull(ParseUtils.parse(Float.class, null));
        assertNull(ParseUtils.parse(Double.class, null));
        assertNull(ParseUtils.parse(BigInteger.class, null));
        assertNull(ParseUtils.parse(BigDecimal.class, null));
        assertNull(ParseUtils.parse(Date.class, null));
        assertNull(ParseUtils.parse(String.class, null));
    }

    public void testObject() throws Exception {
        assertEquals(Boolean.TRUE, ParseUtils.parse(Boolean.class, "true"));
        assertEquals(Boolean.FALSE, ParseUtils.parse(Boolean.class, "false"));
        assertEquals(new Character((char) 10), ParseUtils.parse(Character.class, "\n"));
        assertEquals(new Byte((byte) 10), ParseUtils.parse(Byte.class, "10"));
        assertEquals(new Short((short) 10), ParseUtils.parse(Short.class, "10"));
        assertEquals(new Integer(10), ParseUtils.parse(Integer.class, "10"));
        assertEquals(new Long(10), ParseUtils.parse(Long.class, "10"));
        assertEquals(new Float(10), ParseUtils.parse(Float.class, "10"));
        assertEquals(new Double(10), ParseUtils.parse(Double.class, "10"));
        assertEquals(new BigInteger("10"), ParseUtils.parse(BigInteger.class, "10"));
        assertEquals(new BigDecimal(10), ParseUtils.parse(BigDecimal.class, "10"));
        assertEquals(new Date(0), ParseUtils.parse(Date.class, "1/1/70"));
        assertEquals("foo", ParseUtils.parse(String.class, "foo"));
    }

    public void testBadValues() throws Exception {
        try {
            ParseUtils.parse(Boolean.class, "no");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Character.class, "10");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Byte.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Short.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Integer.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Long.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Float.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Double.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(BigInteger.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(BigDecimal.class, "abc");
            fail("Exception expected");
        } catch (Exception e) {
        }
        try {
            ParseUtils.parse(Date.class, "1.1.70");
            fail("Exception expected");
        } catch (Exception e) {
        }
    }

    public void testBadType() throws Exception {
        try {
            ParseUtils.parse(Cloneable.class, "dolly");
            fail("Exception expected");
        } catch (Exception e) {
        }
    }

}
