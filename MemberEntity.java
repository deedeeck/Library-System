package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MemberEntity implements Serializable {

    @Id
    private Long memberId;
    private String name;
    private String type;
    private String password;

    @OneToOne(cascade={CascadeType.PERSIST})
    private ContactEntity contact;

    @OneToOne(cascade={CascadeType.PERSIST})
    private FineEntity fine;

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="member")
    private Set<ReservationEntity> reservations = new HashSet<ReservationEntity>();

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="member")
    private Set<BookEntity> books = new HashSet<BookEntity>();

    public MemberEntity()
    {
    }

    public void create(Long memberId, String name, String type, String password)
    {
        this.setMemberId(memberId);
        this.setName(name);
        this.setType(type);
        this.setPassword(password);
    }


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId)
    {
        this.memberId = memberId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ContactEntity getContact()
    {
        return contact;
    }

    public void setContact(ContactEntity contact)
    {
        this.contact = contact;
    }

    public FineEntity getFine()
    {
        return fine;
    }

    public void setFine(FineEntity fine)
    {
        this.fine = fine;
    }

    public Set<ReservationEntity> getReservations()
    {
        return reservations;
    }

    public void setReservations(Set<ReservationEntity> reservations)
    {
        this.reservations = reservations;
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
