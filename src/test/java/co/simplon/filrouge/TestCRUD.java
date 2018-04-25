package co.simplon.filrouge;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.hamcrest.core.*;

import co.simplon.filrouge.service.WeaponLinkService;
import co.simplon.filrouge.modele.Weapon;
import co.simplon.filrouge.repository.WeaponRepository;
import co.simplon.filrouge.controleur.WeaponControleur;

/* @RunWith : invoque la classe pour lancer le test dans Junit
 * (= pont entre les tests et JUnit)
 */
@RunWith(SpringRunner.class)
/* @SpringBootTest : remplace toutes les annotations existantes
 * pour faire des tests d'intégration 
 */
@SpringBootTest(classes = FilRougeApplication.class)

public class TestCRUD {
	
	static Weapon weapon;
	static Weapon updatedWeapon;
	static WeaponLinkService weaponLinkService;
	static ResponseEntity<?> newWeapon;
	static ResponseEntity<?> deletedWeapon;
	
	@Autowired
	private WeaponControleur weaponControleur;
	
	@Autowired
	private WeaponRepository weaponRepository;
	
	@Before 
	public void before() {
	System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
	System.out.println("Début test --------------------------------------------------------------");
	}
	
	@BeforeClass
	public static void initUtilisateur() throws Exception{
		weaponLinkService = new WeaponLinkService();
		weapon = new Weapon();
	}
	
	
	@Transactional
	// @Rollback(true) : devant une méthode de test, oblige un rollback à la fin 
	@Rollback(true)
	// @Test : lancement du test même
	@Test
	public void testUpdateWeapon() {

		updatedWeapon = null;
		weapon = createMock("Lance-pierre", "Anti char");

		try {
			updatedWeapon = weaponRepository.save(weapon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(updatedWeapon != null);
		assertEquals("Lance-pierre", updatedWeapon.getModele());
		assertEquals("Anti char", updatedWeapon.getType());
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testCreateWeapon() {     
		
    }
	
	@Transactional
	@Rollback(true)
	@Test
	public void testInsertWeapon() {
        
		try {
			weapon = createMock("Essai", "Test");
			newWeapon = weaponControleur.createWeapon(weapon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(newWeapon != null);
    }

	@Transactional
	@Rollback(true)
	@Test
	public void testDeleteWeapon() {
		
		try {
			weapon = createMock("Essai", "Test");
			deletedWeapon = weaponControleur.createWeapon(weapon);
			deletedWeapon = weaponControleur.deleteWeapon((long) 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(deletedWeapon.getBody() == null);
    }	
	
	@After 
	public void after() {
	System.out.println("Fin test ----------------------------------------------------------------");
	System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
	}
	
	private Weapon createMock(String modele, String type) {
		Weapon mock = new Weapon();
		mock.setModele(modele);
		mock.setType(type);
     	mock.setId(new Long(1));

		return mock;
	}
}
