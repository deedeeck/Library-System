package ejb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import ejb.MemberEntity;
import ejb.FineEntity;
import ejb.PaymentEntity;


@MessageDriven(mappedName = "jms/Queue3", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class MakePaymentMDBean implements MessageListener {

    @PersistenceContext()
    EntityManager em;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    @Resource(mappedName = "jms/Queue4")
    private Queue queue4;

    public MakePaymentMDBean() {
    }

    public void onMessage(Message inMessage)
    {
        MapMessage msg = null;

        try
        {
            if (inMessage instanceof MapMessage) {
                System.out.println("In TLS MakePaymentMDBean");
                msg = (MapMessage) inMessage;
                //Thread.sleep(processingTime.nextInt(5) * 1000);
                makePayment(msg);
            } else {
                System.out.println("MakePaymentMDBean.onMessage: " +
                  "Message of wrong type: " + inMessage.getClass().getName());
            }
        }

		catch (Throwable te) {
            System.out.println("MakePaymentMDBean.onMessage: Exception: " +
              te.toString());
        }


    }

    public void makePayment(MapMessage msg)
    {
        MemberEntity member = new MemberEntity();
        FineEntity fine = new FineEntity();
        

        Long fineId = null;
        Long memberId = null;
        int amtpaid;
        int amtowed;
        int amtbalance;
        boolean paystatus = false;

       TextMessage replyMsg          = null;
       Destination replyDest         = null;
       String replyCorrelationMsgId  = null;
       Session queueSession          = null;
       MessageProducer queueProducer = null;
       Connection queueConnection    = null;

       try
       {
           memberId = msg.getLong("memberId");
           amtpaid = msg.getInt("payamt");

           member = em.find(MemberEntity.class, memberId);

           if (member == null)
           {
               System.out.println("Member does not exist");
               paystatus = false;
           }
           else
           {
               fine = member.getFine();
               if(fine == null)
               {
                  System.out.println("Member has no fines");
                  paystatus = false;
              // finelist.add(null);
               }
               else
               {
                   fineId = fine.getFineId();
                   amtowed = fine.getAmount();

                   amtbalance = amtowed - amtpaid;
                   fine.setAmount(amtbalance);
                   em.flush();

                   PaymentEntity payment = new PaymentEntity();
                   Date datepaid = new Date();
                   payment.setAmountpaid(amtpaid);
                   payment.setDatepaid(datepaid);
                   Set<PaymentEntity> paymentset = new HashSet<PaymentEntity>();
                   paymentset.add(payment);
                   fine.setPayment(paymentset);
                   em.flush();
                   em.persist(payment);
                   paystatus = true;
               }
           }
       }catch (IllegalArgumentException iae) {
            System.out.println("MakePaymentMDBean.MakePayment: " +
                               "No entity found");
        } catch (Exception e) {
            System.out.println("MakePaymentMDBean.MakePayment: " +
                               "em.find failed without throwing " +
                               "IllegalArgumentException");
        }

       try
       {
          queueConnection = queueConnectionFactory.createConnection();
       }catch (Exception ex) {
                System.out.println("MakePaymentMDBean.MakePayment: " +
                  "Unable to connect to JMS provider: " + ex.toString());
            }
       try
       {
                replyDest = msg.getJMSReplyTo();
                replyCorrelationMsgId = msg.getJMSMessageID();

                queueSession  = queueConnection.createSession(true, 0);
                queueProducer = queueSession.createProducer(replyDest);
                replyMsg = queueSession.createTextMessage();
                String replymessage;
                if(paystatus)
                    replymessage = "Payment made sucuessfully";
                else
                   replymessage = "Error in payment process";
                replyMsg.setText(replymessage);
                replyMsg.setJMSCorrelationID(replyCorrelationMsgId);
                queueProducer.send(replyMsg);
                 System.out.println("Payment reply message sent");

       }catch (JMSException je) {
                System.out.println("MakePaymentMDBean.MakePayment: " +
                  "JMSException: " + je.toString());
            } catch (Exception e) {
                System.out.println("MakePaymentMDBean.MakePayment: " +
                              "Exception: " + e.toString());
            }
       finally {
                if (queueConnection != null) {
                    try {
                        queueConnection.close();
                    } catch (JMSException je) {
                        System.out.println("ViewFineBean.searchFines: " +
                          "JMSException: " + je.toString());
                    }
                    queueConnection = null;
                }
            }

    }
    
}
