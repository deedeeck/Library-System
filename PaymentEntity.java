package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

@Entity
public class PaymentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @Temporal(value = TemporalType.DATE)
    private Date datepaid;
    private int amountpaid;

    public PaymentEntity()
    {

    }

    public void create(Date datepaid, int amountpaid)
    {
        this.setDatepaid(datepaid);
        this.setAmountpaid(amountpaid);
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Date getDatepaid() {
        return datepaid;
    }

    public void setDatepaid(Date datepaid) {
        this.datepaid = datepaid;
    }

    public double getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(int amountpaid) {
        this.amountpaid = amountpaid;
    }
}
