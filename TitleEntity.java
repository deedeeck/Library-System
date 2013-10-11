package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.HashSet;

@Entity
public class TitleEntity implements Serializable {

    @Id
    private String ISBN;
    private String title;
    private String author;
    private String publisher;

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="title")
    private Set<BookEntity> books = new HashSet<BookEntity>();

    public TitleEntity(){}

    public void create (String ISBN, String title, String author, String publisher)
    {
        this.setISBN(ISBN);
        this.setTitle(title);
        this.setAuthor(author);
        this.setPublisher(publisher);
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Set<BookEntity> getBooks()
    {
        return books;
    }

    public void setBooks(Set<BookEntity> books)
    {
        this.books = books;
    }

}
