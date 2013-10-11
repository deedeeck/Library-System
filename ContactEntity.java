package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity//(name="Contact")
public class ContactEntity implements Serializable {
    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;
    private String department;
    private String faculty;
    private String phone_number;
    private String email;

    public ContactEntity()
    {
    }
    
    public void create(String department, String faculty, String phone_number, String email)
    {
        this.setDepartment(department);
        this.setFaculty(faculty);
        this.setPhone_number(phone_number);
        this.setEmail(email);
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
       this.department = department;
    }

    public String getFaculty()
    {
        return faculty;
    }

    public void setFaculty(String faculty)
    {
        this.faculty = faculty;
    }

    public String getPhone_number()
    {
        return phone_number;
    }

    public void setPhone_number(String phone_number)
    {
       this.phone_number = phone_number;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
         this.email= email;
    }

}
