/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.util.ArrayList;
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

/**
 *
 * TLS
 */
@MessageDriven(mappedName = "jms/Queue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
        //@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
       // @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "MessageDrivenBean1")
    })
public class ViewFineMDBean implements MessageListener {

    @PersistenceContext()
    EntityManager em;

    //@Resource(mappedName = "jms/TopicConnectionFactory")
    //private ConnectionFactory topicConnectionFactory;
    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    @Resource(mappedName = "jms/Queue2")
    private Queue queue2;

   // static Object waitUntilDone = new Object();

   //Connection queueConnection    = null;

    public ViewFineMDBean() {
    }

    public void onMessage(Message inMessage) {
        MapMessage msg = null;
      
        try {
            if (inMessage instanceof MapMessage) {
                System.out.println("In TLS ViewFineMDBean");
                msg = (MapMessage) inMessage;
                //Thread.sleep(3000);
                searchFines(msg);
            } else {
                System.out.println("ViewFineBean.onMessage: " +
                  "Message of wrong type: " + inMessage.getClass().getName());
            }
        }
        /*catch (InterruptedException ie) {
            System.out.println("ViewFineBean.onMessage: " +
                               "InterruptedException: " + ie.toString());
        }*/ catch (Throwable te) {
            System.out.println("ViewFineBean.onMessage: Exception: " +
              te.toString());
        }
        /*
        finally
        {
            if (queueConnection != null)
            {
                try
                {
                    queueConnection.close();
                }
                catch (Exception e)
                {
                    System.out.println("TLS ViewFineBean.onMessage(): Close exception : " + e.toString());
                }
            }
        }
        */
    }
    
    public void searchFines(MapMessage msg)
    {
       String StringmemberId = null;
       MemberEntity member = new MemberEntity();
       FineEntity fine = new FineEntity();
      
       Long fineId = null;
       String sfineId = null;
       Integer fineamt = null;
       String sfineamt = null;

       /*
       Connection connection         = null;
       Session session               = null;
       MessageProducer producer      = null;
       TextMessage message           = null;
        */
       Destination replyDest         = null;
       String replyCorrelationMsgId  = null;
       

       MapMessage replyMsg           = null;
       Session queueSession          = null;
       MessageProducer queueProducer = null;
       Connection queueConnection    = null;
       
       try
       {                      
           StringmemberId = msg.getString("memberId");
           Long memberId = Long.parseLong(StringmemberId);
           
           member = em.find(MemberEntity.class, memberId);
           if (member == null)
            {
               System.out.println("Member " + memberId + " does not exist");
               sfineId = null;
               sfineamt = null;
            }

           else
            {
               System.out.println("ViewFineBean.searchFines: Found memberId " + memberId);
               fine = member.getFine();

               if(fine == null)
                {
                   System.out.println("Member " + memberId + " has no fines");
                   sfineId = null;
                   sfineamt = null;
                }
               else
               {
                   System.out.println("Member " + memberId + " has fines");
                   fineId = fine.getFineId();
                   fineamt= fine.getAmount();
                   sfineId = fineId.toString();
                   sfineamt = fineamt.toString();
               }
           }
       }catch (IllegalArgumentException iae) {
            System.out.println("ViewFineBean.SearchFines: " +
                               "No entity found");
        } catch (Exception e) {
            System.out.println("ViewFineBean.SearchFines: " + 
                               "em.find failed without throwing " +
                               "IllegalArgumentException");
        }
       try
       {
           queueConnection = queueConnectionFactory.createConnection();

       }catch (Exception ex) {
                System.out.println("ViewFineBean.searchFines: " +
                  "Unable to connect to JMS provider: " + ex.toString());
            }
       
       try {
                replyDest = msg.getJMSReplyTo();
                replyCorrelationMsgId = msg.getJMSMessageID();
                
                queueSession  = queueConnection.createSession(true,0);
                queueProducer = queueSession.createProducer(replyDest);
                //queueConnection.start();
                replyMsg = queueSession.createMapMessage();
                replyMsg.setString("fineId", sfineId);
                replyMsg.setString("fineamt", sfineamt);
                replyMsg.setJMSCorrelationID(replyCorrelationMsgId);

                //replyMsg = createReplyMsg(queueSession, replyCorrelationMsgId,sfineId,sfineamt);
                queueProducer.send(replyMsg);
                System.out.println("Reply message sent");
            } catch (JMSException je) {
                System.out.println("ViewFineBean.searchFines: " +
                  "JMSException: " + je.toString());
            } catch (Exception e) {
                System.out.println("ViewFineBean.searchFines: " +
                              "Exception: " + e.toString());
            }
       finally {
                if (queueConnection != null) {
                    try {
                        queueConnection.close();
                        System.out.println("Queue connection in TLS closed");
                    } catch (JMSException je) {
                        System.out.println("ViewFineBean.searchFines: " +
                          "JMSException: " + je.toString());
                    }
                    queueConnection = null;
                }
            }
    }
/*
    private MapMessage createReplyMsg(Session session, String msgId, String fineId, String fineamt)
        throws JMSException
    {
        MapMessage replyMsg2 = session.createMapMessage();
        replyMsg2.setString("fineId", fineId);
        replyMsg2.setString("fineamt", fineamt);
        replyMsg2.setJMSCorrelationID(msgId);
        return replyMsg2;
    }
*/
    
}
    

