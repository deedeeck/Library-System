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
import ejb.ReservationEntity;
import ejb.MemberEntity;
import ejb.BookEntity;
import ejb.FineEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

@Stateful
public class ReservationManagerBean implements ReservationManagerRemote
{
    @PersistenceContext()
    EntityManager em;

    public ReservationManagerBean(){}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addLoan(Long bookId,Long memberId, Date loanDate, Date dueDate)
            throws ExistException
    {
        BookEntity book = new BookEntity();
        book = em.find(BookEntity.class, bookId);
        MemberEntity member = em.find(MemberEntity.class, memberId);
        Set <BookEntity> books = new HashSet<BookEntity>(); 
        if (book == null)
        {
            em.clear();
            System.out.println("Book does not exist");
            System.out.println();
            throw new ExistException("Book does not exist");
        }

        else if (member == null)
        {
            em.clear();
            System.out.println("Member does not exist");
            System.out.println();
            throw new ExistException("Member does not exist");
        }
        else if (book.getMember() != null)
        {
            em.clear();
            System.out.println("This book is currently on loan");
            throw new ExistException("This book is on loan");
        }
        else if (member.getFine() != null)
        {
            em.clear();
            System.out.println("Member has outstanding fines");
            throw new ExistException("Member has outstanding fines");
        }
        else
        {
        book.setMember(member);
        book.setDuedate(dueDate);
        book.setLoandate(loanDate);
        em.flush();
            if(member.getBooks() == null)
            {
             books.add(book);
             member.setBooks(books); 
            }
            else
            {
            books = member.getBooks();
            books.add(book);
            }
        em.flush();
        System.out.println("Loan sucessful!");
        System.out.println("Book member id is " + book.getMember().getMemberId());
        }
    }

    public void removeLoan(Long bookId)
            throws ExistException
    {
      BookEntity be = new BookEntity();
      MemberEntity me = new MemberEntity();
      MemberEntity me2 = null;
      Long memberId = null;


      be = em.find(BookEntity.class, bookId);
      memberId = be.getMember().getMemberId();
      me = em.find(MemberEntity.class, memberId);

      if (be == null)
        {
            em.clear();
            System.out.println("Book does not exist");
            System.out.println();
            throw new ExistException("Book does not exist");
        }

      else if (me == null)
        {
            em.clear();
            System.out.println("Member does not exist");
            System.out.println();
            throw new ExistException("Member does not exist");
        }
      else if (memberId == null)
      {
          System.out.println("Book is not on loan");
          throw new ExistException("Book is not on loan");
      }
      be.setMember(me2);
      Date loandate = be.getLoandate();
      int fine = calculateFine(loandate);
      if (fine != 0)
      {
          FineEntity fe = new FineEntity();
          fe.setAmount(fine);
          me.setFine(fe);
          em.persist(fe);
      }
      em.flush();

       Set<BookEntity> BookAssociatedWithMember = new HashSet<BookEntity>();
       BookAssociatedWithMember = me.getBooks();

       BookAssociatedWithMember.remove(be);
       me.setBooks(BookAssociatedWithMember);
       em.flush();

       System.out.println("Loan sucuessfully removed");
    }

    public int calculateFine(Date duedate)
    {
        int fine = 0;
        Calendar returnDate=Calendar.getInstance();
        Calendar Duedate=Calendar.getInstance();
        Duedate.setTime(duedate);
        
        while(Duedate.before(returnDate))
        {
            Duedate.add(Calendar.DAY_OF_MONTH, 1);
            fine++;
        }
       return fine;

    }

    public Date addDate(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addReservation(Long memberId, Long bookId, Date reservationDate)
    {
        MemberEntity me = new MemberEntity();
        me = em.find(MemberEntity.class, memberId);
        BookEntity be = new BookEntity();
        be = em.find(BookEntity.class, bookId);

        ReservationEntity re = new ReservationEntity();
        re.create(me,be, reservationDate);
        em.persist(re);
    }

    public ArrayList<ReservationEntity> getReservations(Long memberId)
    {
        Query q = em.createQuery("SELECT p FROM ReservationEntity p");
        ArrayList<ReservationEntity> reservation = new ArrayList<ReservationEntity>();
        for (Object o: q.getResultList())
        {
            ReservationEntity r = (ReservationEntity)o;

            if(r.getMember() == null)
            {
                continue;
            }
            else if(r.getMember().getMemberId().compareTo(memberId) == 0)
            {
                reservation.add(r);
            }
        }
        return reservation;
    }

    public void removeReservation(Long reservationId)
    {
        ReservationEntity re = new ReservationEntity();
        re = em.find(ReservationEntity.class, reservationId);
        em.remove(re);
    }

    public boolean extendLoan(Long bookId)
    {
        BookEntity be = new BookEntity();
        be = em.find(BookEntity.class, bookId);
        boolean canloan = false; // to check member has not renewed before

        Date duedate = be.getDuedate();
        Date loandate = be.getLoandate();
        duedate = addDate(duedate, 14);
        canloan = permitLoan(loandate,duedate);
        if(canloan)
        {
        be.setDuedate(duedate);
        em.flush();
        return canloan;
        }
        else
            return canloan;
    }

    public boolean permitLoan(Date loandate , Date duedate) // to check if member has renewed before
    {
        int counter = 0;

        Calendar Cloandate = Calendar.getInstance();
        Cloandate.setTime(loandate);
        Calendar Cduedate = Calendar.getInstance();
        Cduedate.setTime(duedate);

        while(Cloandate.before(Cduedate))
        {
            Cloandate.add(Calendar.DAY_OF_MONTH, 1);
            counter++;
        }
        if(counter > 28)
       return false;
        else
       return true;

    }
}
