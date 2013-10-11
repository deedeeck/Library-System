
package tls;

import ejb.MemberManagerRemote;
import ejb.BookManagerRemote;
import ejb.ReservationManagerRemote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import exception.MemberException;
import exception.ExistException;

public class Engine {

    private MemberManagerRemote mm;
    private BookManagerRemote bm;
    private ReservationManagerRemote rm;
    Input i = new Input();

    public Engine()
    {
        mm = Main.memberManager;
        bm = Main.bookManager;
        rm = Main.reservationManager;
    }

    public void doAddMember()
    {
        Long memberId;
        String memberName;
        String memberType;
        String memberPassword;
        String contactDepartment;
        String contactFaculty;
        String contactPhone_number;
        String contactEmail;

        try
        {
            System.out.println("\n\n\t\t== Add a Member ==\n");

            memberId = i.getLong("Member ID");
            memberName = i.getString("Member Name", null);
            memberType = i.getString("Member Type", null);
            memberPassword = i.getString("Member Password", null);
            contactDepartment = i.getString("Contact Department", null);
            contactFaculty = i.getString("Contact Faculty", null);
            contactPhone_number = i.getString("Contact Phone Number", null);
            contactEmail = i.getString("Contact Email", null);

            mm.addMember(memberId,memberName, memberType, memberPassword,
                    contactDepartment,contactFaculty,contactPhone_number, contactEmail);
            System.out.println("\nMember " + memberId + " added sucessfully!");
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to add member");
            System.out.println("UNKNOWN ERROR\n");
        }
    }

    public void doDeleteMember()
    {
        Long memberId;

        try
        {
             System.out.println("\n\n\t\t== Delete a Member ==\n");
             memberId = i.getLong("Member ID");
             mm.removeMember(memberId);
             System.out.println("\nMember " + memberId + " deleted sucessfully!");
        }
        catch(ExistException ex)
        {
          System.out.println("\nERROR: Failed to delete member");
          System.out.println(ex.getMessage() + "\n");
        }
        catch(MemberException ex)
        {
            System.out.println("\nERROR: Failed to delete member");
            if(ex.getException() == ex.getHAS_LOANS())
                System.out.println("MEMBER HAS OUTSTANDING LOANS");
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to delete member");
            System.out.println("UNKNOWN ERROR\n");
        }

    }

    public void doAddTitle()
    {
        String ISBN;
        String title;
        String author;
        String publisher;

        try
        {
            System.out.println("\n\n\t\t== Add a Title ==\n");

            ISBN = i.getString("ISBN",null);
            title = i.getString("Title",null);
            author = i.getString("Author",null);
            publisher = i.getString("Publisher",null);
            
            bm.addTitle(ISBN, title, author, publisher);
            System.out.println("\nTitle " + ISBN + " added sucessfully!");
        }

        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to add Title");
            System.out.println("UNKNOWN ERROR\n");
        }
    }

    public void doAddBook()
    {
        String ISBN;
        Long bookId;
        int copy;
        try
        {
         System.out.println("\n\n\t\t== Add a Book ==\n");

         ISBN = i.getString("ISBN",null);
         bookId = i.getLong("BookId");
         copy = i.getInt("Copy number");

         bm.addBook(ISBN,bookId, copy);
         System.out.println("\nBook " +bookId + " with ISBN " + ISBN + " added sucessfully!");
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to add Book");
            System.out.println(ex.getMessage() + "\n");
        }
    }

    public void doDeleteBook()
    {
        Long bookId;
        String ISBN;

        try
        {
         System.out.println("\n\n\t\t== Delete a Book ==\n");

         bookId = i.getLong("Book ID");

         bm.removeBook(bookId);
         System.out.println("\nBook " +bookId + " deleted sucessfully!");
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to delete Book");
            System.out.println(ex.getMessage() + "\n");

        }
    }

    public void doAddLoan()
    {
        Long memberId;
        Long bookId;
        Date loandate;
        Date duedate;

        try
        {
        
         System.out.println("\n\n\t\t== Add a Loan ==\n");

         memberId = i.getLong("Member ID");
         bookId = i.getLong("Book ID");
         loandate = i.getDateFromString("Loan Date");

         duedate = rm.addDate(loandate, 14);
         rm.addLoan(bookId,memberId,loandate,duedate);
         System.out.println("\nBook " +bookId + " loan sucessfully by " +memberId);
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to loan Book");
            System.out.println(ex.getMessage() + "\n");
        }
    }

    public void doDeleteLoan()
    {

     Long bookId;
     Long memberId;

        try
        {
         System.out.println("\n\n\t\t== Delete a Loan ==\n");

         bookId = i.getLong("Book ID");
         rm.removeLoan(bookId);
         System.out.println("\nLoan for book " + bookId + " deleted sucessfully!");
        }
        catch(Exception ex)
        {
            System.out.println("\nERROR: Failed to delete Book");
            System.out.println(ex.getMessage() + "\n");
        }
    }
}
