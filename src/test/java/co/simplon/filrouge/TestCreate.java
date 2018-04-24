package co.simplon.filrouge;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.hamcrest.core.*;

public class TestCreate {
	
	Truc truc = new Truc();
	
	@Before 
	public void before() {
	System.out.println("Début test --------------------------------------------------------------");
	}
	
	@Test
	public void createWeapon() {
        Assert.assertThat(weapon, IsNull.notNullValue());
    }

	@Test
	public void Weapon() {
        Assert.assertThat(, IsNull.notNullValue());
    }
	
	@Test
	public void updateWeapon() {
        Assert.assertThat(, IsNull.notNullValue());
    }
	
	@Test
	public void deleteWeapon() {

		truc.delete("1", "one");
	    truc.delete("2", "two");

	    assertNull(truc.read("1"));
	    assertNull(truc.read("2"));
    }	
	
	@After 
	public void after() {
	System.out.println("Fin test ----------------------------------------------------------------");
	}
	
}
