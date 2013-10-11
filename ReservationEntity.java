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

@Entity
public class ReservationEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;
    @Temporal(value = TemporalType.DATE)
    private Date reservationDate;

    @ManyToOne//(cascade={CascadeType.PERSIST})
    private MemberEntity member;

    @ManyToOne//(cascade={CascadeType.PERSIST})
    private BookEntity book;

    public void create(MemberEntity me, BookEntity be, Date reservationDate)
    {
        this.setMember(me);
        this.setBook(be);
        this.setReservationDate(reservationDate);

    }
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public BookEntity getBook()
    {
        return book;
    }

    public void setBook(BookEntity book)
    {
        this.book = book;
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
