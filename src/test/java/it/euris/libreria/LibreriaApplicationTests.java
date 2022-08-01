package it.euris.libreria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.euris.libreria.util.Utils;

@SpringBootTest
class LibreriaApplicationTests {

	@Test
	void contextLoads() {
		assertEquals("Ciao", Utils.capitalize("ciao"));
	}

}
