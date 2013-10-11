
package exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ExistException extends Exception
{
    public ExistException() {}
    public ExistException(String message)
    {
        super(message);
    }
}
