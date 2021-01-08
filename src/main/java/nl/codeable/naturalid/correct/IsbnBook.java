package nl.codeable.naturalid.correct;

import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.StringJoiner;

@Entity(name = "IsbnBook")
public class IsbnBook {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private IsbnLibrary library;

    protected IsbnBook() {
        // required by JPA/Hibernate
    }

    public IsbnBook(String isbn, String title) {
        this.title = title;
        this.isbn = isbn;
    }

    public IsbnLibrary getLibrary() {
        return library;
    }

    public void setLibrary(IsbnLibrary library) {
        this.library = library;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        IsbnBook book = (IsbnBook) o;
        return Objects.equals( isbn, book.isbn );
    }

    @Override
    public int hashCode() {
        return Objects.hash( isbn );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IsbnBook.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("isbn='" + isbn + "'")
                .add("title='" + title + "'")
                .toString();
    }
}
