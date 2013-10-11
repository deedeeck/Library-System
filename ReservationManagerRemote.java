package ejb;

import javax.ejb.Remote;
import java.util.Date;
import exception.ExistException;
import java.util.ArrayList;
import java.util.List;
import ejb.ReservationEntity;

@Remote
public interface ReservationManagerRemote
{
  public void addLoan(Long bookId, Long memberId, Date loanDate, Date dueDate) throws ExistException;
  public void removeLoan(Long bookId) throws ExistException;
  public int calculateFine(Date duedate);
  public Date addDate(Date date, int days);
  public void addReservation(Long memberId,Long bookId,Date reservationDate);
  public ArrayList<ReservationEntity> getReservations(Long memberId);
  public void removeReservation(Long reservationId);
  public boolean extendLoan(Long bookId);
  public boolean permitLoan(Date loandate , Date duedate);
}
