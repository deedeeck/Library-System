
package ejb;

import javax.ejb.Remote;
import java.util.Date;
import exception.ExistException;
import appHelper.BookState;
import ejb.BookEntity;
import javax.ejb.Remove;
import java.util.ArrayList;
import java.util.List;


@Remote
public interface BookManagerRemote
{
    public void addTitle(String ISBN, String titleofbook, String author, String publisher) throws ExistException;
    public void addBook(String ISBN, Long bookID, int copy) throws ExistException;
    public void removeBook(Long bookID) throws ExistException;
    public ArrayList<BookEntity> getBooks(Long memberId);
    public ArrayList<BookEntity> getBooksFromSearch(String searchParameters);
    public void remove();
    
}
