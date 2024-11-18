//Ibrahim Miloua
package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

    RentACat r;
    Cat c1;
    Cat c2;
    Cat c3;

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream stdout;
    String newline = System.lineSeparator();

    @Before
    public void setUp() {
        r = RentACat.createInstance(InstanceType.IMPL);
        c1 = Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");
        c2 = Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");
        c3 = Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");
        stdout = System.out;
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(stdout);
        r = null;
        c1 = null;
        c2 = null;
        c3 = null;
    }

    @Test
    public void testGetCatNullNumCats0() throws Exception {
        
		Method m = r.getClass().getDeclaredMethod("getCat", int.class);
		m.setAccessible(true);
		Object ret = m.invoke(r, 2);
		assertTrue(ret == null);
       
    }

    @Test
    public void testGetCatNumCats3() throws Exception {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);

        Mockito.when(c2.getId()).thenReturn(2);

		Method m = r.getClass().getDeclaredMethod("getCat", int.class);
		m.setAccessible(true);
		Object ret = m.invoke(r, 2);
		assertTrue(ret.equals(c2));
       
    }

    @Test
    public void testListCatsNumCats0() {
        String ret = r.listCats();
        assertTrue(ret.equals(""));
    }

    @Test
    public void testListCatsNumCats3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        String ret = r.listCats();
        assertTrue(ret.equals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"));
    }

    @Test
    public void testRenameFailureNumCats0() {
        boolean ret = r.renameCat(2, "Garfield");
        assertTrue(ret == false);
    }

    @Test
    public void testRenameNumCat3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        boolean ret = r.renameCat(2, "Garfield");
        Mockito.verify(c2).renameCat("Garfield");
        assertTrue(ret == true);
    }

    @Test
    public void testRentCatNumCats3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        Mockito.when(c2.getRented()).thenReturn(false);
        boolean ret = r.rentCat(2);
        assertTrue(ret == true);

        assertEquals( "Old Deuteronomy has been rented." + newline, out.toString());
    }

    @Test
    public void testRentCatFailureNumCats3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        Mockito.when(c2.getRented()).thenReturn(true);
        boolean ret = r.rentCat(2);
        assertTrue(ret == false);

        assertEquals("Sorry, Old Deuteronomy is not here!" + newline, out.toString());
    }

    @Test
    public void testReturnCatNumCats3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        Mockito.when(c2.getRented()).thenReturn(true);
        boolean ret = r.returnCat(2);
        assertTrue(ret == true);

        assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
    }

    @Test
    public void testReturnFailureCatNumCats3() {

        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);

        Mockito.when(c2.getRented()).thenReturn(false);
        boolean ret = r.returnCat(2);
        assertTrue(ret == false);
        assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
    }

}
