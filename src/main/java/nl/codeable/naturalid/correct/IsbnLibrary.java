package nl.codeable.naturalid.correct;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "IsbnLibrary")
public class IsbnLibrary {

    @Id
    private Long id;

    private String name;

    protected IsbnLibrary() {
        // required
        System.out.println("hibernate new lib");
    }

    public IsbnLibrary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
//    @JoinColumn(name = "book_id")
    private Set<IsbnBook> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IsbnBook> getBooks() {
        return books;
    }

    public void setBooks(Set<IsbnBook> books) {
        this.books = books;
    }
}
