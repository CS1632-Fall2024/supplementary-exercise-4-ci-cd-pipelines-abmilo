//Ibrahim Miloua
package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatIntegrationTest {

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
		c1 = Cat.createInstance(InstanceType.IMPL, 1, "Jennyanydots");
		c2 = Cat.createInstance(InstanceType.IMPL, 2, "Old Deuteronomy");
		c3 = Cat.createInstance(InstanceType.IMPL, 3, "Mistoffelees");

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
	public void testGetCatNullNumCats0()  throws Exception{
		
		Method m = r.getClass().getDeclaredMethod("getCat", int.class);
		m.setAccessible(true);
		Object ret = m.invoke(r, 2);
		assertTrue(ret == null);
		
	}
	
	@Test
	public void testGetCatNumCats3() throws Exception{
		
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Method m = r.getClass().getDeclaredMethod("getCat", int.class);
		m.setAccessible(true);
		Object ret = m.invoke(r, 2);
		Cat cat = (Cat) ret;
		assertTrue(ret != null);
		assertEquals(2, cat.getId());
		
	}

	@Test
	public void testListCatsNumCats0() throws Exception {
		
		Method m = r.getClass().getDeclaredMethod("listCats");
		m.setAccessible(true);
		Object ret = m.invoke(r);
		assertEquals(ret, "");
	}

	@Test
	public void testListCatsNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		String ret = r.listCats();
		assertEquals(ret, "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n");
	}
	
	@Test
	public void testRenameFailureNumCats0() {
		boolean ret = r.renameCat(2, "Garfield");
		assertFalse(ret);
		String expected = "Invalid cat ID." + newline;
		assertEquals(expected, out.toString());
	}

	@Test
	public void testRenameNumCat3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		boolean ret = r.renameCat(2, "Garfield");
		assertTrue(ret);
		assertEquals(c2.getName(), "Garfield");
	}

	@Test
	public void testRentCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		boolean ret = r.rentCat(2);
		assertTrue(ret);
		assertEquals("Old Deuteronomy has been rented." + newline, out.toString());
	}

	@Test
	public void testRentCatFailureNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		c2.rentCat();
		boolean ret = r.rentCat(2);
		assertFalse(ret);
		assertEquals("Sorry, Old Deuteronomy is not here!" + newline, out.toString());
	}

	@Test
	public void testReturnCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		c2.rentCat();
		boolean ret = r.returnCat(2);
		assertTrue(ret);
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
	}

	@Test
	public void testReturnFailureCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		boolean ret = r.returnCat(2);
		assertFalse(ret);
		assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
	}
}
