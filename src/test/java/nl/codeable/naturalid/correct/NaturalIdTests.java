package nl.codeable.naturalid.correct;

import nl.codeable.naturalid.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NaturalIdTests extends BaseTest {

	@Test
	void testEmFindAndReadingWithNaturalIdEqualsHashCode() {

		var book1 = new IsbnBook("isbn-1.1", "book 1");
		var book2 = new IsbnBook("isbn-1.2", "book 2");

		doInJPA(em -> {
			em.persist(new IsbnLibrary(1L, "babylon"));
		});

		IsbnLibrary library = createLibrary(1L, book1, book2);

		// both books are stored
		assertThat(library.getBooks()).containsExactlyInAnyOrder(book1, book2);

		doInJPA(em -> {
			var book = em.find(IsbnBook.class, book1.getId());

			assertThat(book.getLibrary().getBooks()).hasSize(2);
			assertTrue(book.getLibrary().getBooks().contains(book), "book");
			assertTrue(book.getLibrary().getBooks().contains(book1), "book1");
			assertTrue(book.getLibrary().getBooks().contains(book2), "book2");
		});
	}

	@Test
	void testEmQueryWithNaturalIdEqualsHashCode() {

		var book1 = new IsbnBook("isbn-2.1", "book 1");
		var book2 = new IsbnBook("isbn-2.2", "book 2");

		doInJPA(em -> {
			em.persist(new IsbnLibrary(2L, "babylon"));
		});

		IsbnLibrary library = createLibrary(2L, book1, book2);

		// both books are stored
		assertThat(library.getBooks()).containsExactlyInAnyOrder(book1, book2);

		doInJPA(em -> {
			var book = em.createQuery("select b from IsbnBook b where b.isbn = 'isbn-2.1'", IsbnBook.class).getSingleResult();
			assertThat(book.getLibrary().getBooks()).hasSize(2);
			assertTrue(book.getLibrary().getBooks().contains(book), "book");
			assertTrue(book.getLibrary().getBooks().contains(book1), "book1");
			assertTrue(book.getLibrary().getBooks().contains(book2), "book2");
		});
	}

	private IsbnLibrary createLibrary(long libId, IsbnBook book1, IsbnBook book2) {
		return doInJPA(em -> {
			var lib = em.find(IsbnLibrary.class, libId);
			book1.setLibrary(lib);
			em.persist(book1);
			lib.getBooks().add(book1);

			book2.setLibrary(lib);
			em.persist(book2);
			lib.getBooks().add(book2);

			return lib;
		});
	}

}
