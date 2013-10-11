package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity//(name="book")
public class BookEntity implements Serializable {

    @Id
    private Long bookId;
    private int copy;
    @Temporal(value = TemporalType.DATE)
    private Date loandate;
    @Temporal(value = TemporalType.DATE)
    private Date duedate;

   @ManyToOne
   private TitleEntity title;

   @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="book")
   private Set<ReservationEntity> reservations = new HashSet<ReservationEntity>();

   @ManyToOne
   private MemberEntity member;

   public BookEntity(){}

   public void create(Long bookId, int copy)
   {
       this.setBookId(bookId);
       this.setCopy(copy);
   }


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

  
    public int getCopy() {
        return copy;
    }

   
    public void setCopy(int copy) {
        this.copy = copy;
    }

    public Date getLoandate() {
        return loandate;
    }

   
    public void setLoandate(Date loandate) {
        this.loandate = loandate;
    }


    public Date getDuedate() {
        return duedate;
    }

 
    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public TitleEntity getTitle()
    {
        return title;
    }

    public void setTitle(TitleEntity title)
    {
        this.title = title;
    }

    public Set<ReservationEntity> getReservations()
    {
        return reservations;
    }

    public void setReservations(Set<ReservationEntity> reservations)
    {
        this.reservations = reservations;
    }

    public MemberEntity getMember()
    {
        return member;
    }

    public void setMember(MemberEntity member)
    {
        this.member = member;
    }
}
