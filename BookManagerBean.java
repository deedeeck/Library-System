
package ejb;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import exception.ExistException;
import javax.ejb.Remove;
import java.util.ArrayList;
import java.util.List;
import appHelper.BookState;
import ejb.BookEntity;

@Stateful
public class BookManagerBean implements BookManagerRemote
{
    @PersistenceContext()
    EntityManager em;
    BookEntity book;

    public BookManagerBean(){}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addTitle(String ISBN, String titleofbook, String author, String publisher)
            throws ExistException
    {
        TitleEntity title = new TitleEntity();
        if(em.find(TitleEntity.class, ISBN) == null)
        {
        title.create(ISBN,titleofbook,author,publisher);
        em.persist(title);
        System.out.println("Title added!");
        System.out.println();
        }
        else
        {
            System.out.println("Title already exist");
            throw new ExistException("Title already exist");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addBook(String ISBN,Long bookID, int copy)
            throws ExistException
    {
        book = new BookEntity();
        Set <BookEntity> books = new HashSet<BookEntity>(); // check if needed
        book.create(bookID,copy);


        TitleEntity title = em.find(TitleEntity.class, ISBN);
        if (title == null)
        {
            em.clear();
            System.out.println("Title does not exist");
            System.out.println();
            throw new ExistException("Title does not exist");

        }
        if (title.getBooks() == null)
            title.setBooks(books);
        else
        {
            books = title.getBooks();
            books.add(book);
            title.setBooks(books);
            em.flush();
        }
        book.setTitle(title);
        em.persist(book);
        System.out.println("Book added!");
        System.out.println();
        
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeBook(Long bookID)
            throws ExistException
    {
        TitleEntity title;
        String ISBN;

        book = new BookEntity();
        book = em.find(BookEntity.class, bookID);

        if (book == null)
        {
            System.out.println("Book not found!");
            System.out.println();
            throw new ExistException("Book not found");
        }
        else
        {
            ISBN = book.getTitle().getISBN();
            title = em.find(TitleEntity.class, ISBN);

            Set<BookEntity> BookAssociatedWithTitle = new HashSet<BookEntity>();
            BookAssociatedWithTitle = title.getBooks();

            BookAssociatedWithTitle.remove(book);
            title.setBooks(BookAssociatedWithTitle);
            em.flush();

          System.out.println("Book found..");
          System.out.println("Deleting book..");
          System.out.println();
          em.remove(book);
          //em.persist(title);
        }
    }

    public ArrayList<BookEntity> getBooks(Long memberId)
    {
        Query q = em.createQuery("SELECT p FROM BookEntity p");
        ArrayList<BookEntity> stateList = new ArrayList<BookEntity>();
        Long bookId;
        for (Object o: q.getResultList())
        {
            BookEntity p = (BookEntity)o;
            bookId = p.getBookId();
            p=em.find(BookEntity.class, bookId);

            if(p.getMember() == null)
            {
                continue;
            }
            else if(p.getMember().getMemberId().compareTo(memberId) == 0)
            {
                stateList.add(p);
            }       
        }
        return stateList;
    }

    public ArrayList<BookEntity> getBooksFromSearch(String searchParameters)
    {
       Query q = em.createQuery("SELECT p FROM BookEntity p");
       ArrayList<BookEntity> bookList = new ArrayList<BookEntity>();

       for (Object o: q.getResultList())
       {
            BookEntity p = (BookEntity)o;

            if(p.getTitle().getTitle().equals(searchParameters) || p.getTitle().getAuthor().equals(searchParameters))
            {
                bookList.add(p);
            }
       }
        return bookList;
    }
    
    @Remove
    public void remove()
    {
        System.out.println("MemberManagerBean: remove()");
    }
}
