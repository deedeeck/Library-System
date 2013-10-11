/*
 * Main.java
 */

package tls;

import javax.ejb.EJB;
import ejb.MemberManagerRemote;
import ejb.BookManagerRemote;
import ejb.ReservationManagerRemote;

public class Main {

   @EJB
     public static MemberManagerRemote memberManager;
   @EJB
    public static BookManagerRemote bookManager;
   @EJB
    public static ReservationManagerRemote reservationManager;

   public Main()
   {
   }
    public static void main(String[] args) {
        new Selection().LibSystem(args);
    }

}
