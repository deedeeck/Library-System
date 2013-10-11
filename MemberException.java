
package exception;
import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class MemberException extends Exception
{
    private int exception = 0;
    private static final int HAS_LOANS = 1;

    public MemberException(){}

    public MemberException(int exception)
    {
        setException(exception);
    }

    public int getException()
    {
        return exception;
    }

    public void setException(int exception)
    {
        this.exception = exception;
    }

    public static int getHAS_LOANS()
    {
        return HAS_LOANS;
    }

}
