package prueba.prueba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest_01 extends Prueba_Nohora {
	
	@BeforeClass
	public static void initTest() throws Exception {
		main(null);
	}

	@Test
	public void test_01() throws InterruptedException {
		assertTrue("Error ingresando busqueda!", enterWordToSearch());
		assertEquals("Error palabra a buscar no coincide!", "pruebas", verifyCorrectWord());
		assertTrue("Error Numero de Resultados!", verifyTotalResult());
	}

	@AfterClass
	public static void finalAll() throws Exception {
		driver.quit();
	}

}