
package ejb;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import exception.MemberException;
import exception.ExistException;
import appHelper.MemberState;
import javax.ejb.Remove;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class MemberManagerBean implements ejb.MemberManagerRemote {

    @PersistenceContext()
    EntityManager em;
    MemberEntity member;

    public MemberManagerBean(){}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addMember(Long memberId, String name, String type, String password, String department,
            String faculty, String phone_number, String email)
            throws ExistException
    {
        //System.out.println("addMember in sessions bean is invoked");
        ContactEntity contact;

        member = new MemberEntity();
        if(em.find(MemberEntity.class, memberId) == null)
        {
        member.create(memberId,name,type,password);

        contact = createContact(department, faculty, phone_number, email);
        member.setContact(contact);
        em.persist(member);
        System.out.println("Member added");
        }
        else
        {
            System.out.println("Member already exists");
            throw new ExistException("Member already exist");
        }
    }

    private ContactEntity createContact(String department,
            String faculty, String phone_number, String email)
    {
        ContactEntity contact = new ContactEntity();
        contact.create(department, faculty, phone_number, email);
        return contact;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeMember(Long memberId) throws ExistException, MemberException
    {
       // Date currentDate = new Date();

        member = em.find(MemberEntity.class, memberId);

        if(member == null)
        {
            throw new ExistException("MEMBER DOES NOT EXIST");
        }
        else if(member.getBooks().size() > 0) //&& member.getBooks().getDuedate() )
        {
            throw new MemberException(MemberException.getHAS_LOANS());
        }
        else
        {
            em.remove(member);
        }
    }

    public List<MemberEntity> getMembers()
    {
        Query q = em.createQuery("SELECT p FROM MemberEntity p");
        List stateList = new ArrayList();
        for (Object o: q.getResultList())
        {
            MemberEntity p = (MemberEntity)o;
            stateList.add(p);
        }
        return stateList;
    }

    @Remove
    public void remove()
    {
        System.out.println("MemberManagerBean: remove()");
    }

    }

