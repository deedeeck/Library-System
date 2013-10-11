package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FineEntity implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fineId;
    private int amount;

    @OneToMany(cascade={CascadeType.PERSIST})
    private Set<PaymentEntity> payments = new HashSet<PaymentEntity>();

    public FineEntity()
    {

    }

    public void create(int amount)
    {
         this.setAmount(amount);
    }

    public Set<PaymentEntity> getPayments()
    {
        return payments;
    }

    public void setPayment(Set<PaymentEntity> payments)
    {
        this.payments = payments;
    }
    public Long getFineId() {
        return fineId;
    }

    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

   

}
