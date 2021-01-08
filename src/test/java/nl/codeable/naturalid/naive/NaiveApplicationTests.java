package nl.codeable.naturalid.naive;

import nl.codeable.naturalid.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaiveApplicationTests {

	@Autowired
	private TestService testService;

	@Test
	void testCreationWithNaiveEqualsHashCode() {

		var book1 = new Book("book 1");
		var book2 = new Book("book 2");

		testService.doInJpa(em -> {
			em.persist(new Library(1L, "babylon"));
		});

		var library = testService.doInJpa(em -> {
			var lib = em.find(Library.class, 1L);

			lib.getBooks().add(book1);
			lib.getBooks().add(book2);

			return lib;
		});

		// book2 is not stored because of hashCode problem
		assertThat(library.getBooks()).containsExactly(book1);
	}
}
