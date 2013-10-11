

package ejb;

import javax.ejb.Remote;
import java.util.HashSet;
import java.util.Set;
import exception.MemberException;
import exception.ExistException;
import appHelper.MemberState;
import javax.ejb.Remove;
import java.util.ArrayList;
import java.util.List;

@Remote
public interface MemberManagerRemote {

    public void addMember(Long memberId, String name, String type, String password, String department,
            String faculty, String phone_number, String email) throws ExistException;

    public void removeMember(Long memberId) throws ExistException, MemberException;

   // public List<MemberState> getMembers();

     public List<MemberEntity> getMembers();

    public void remove();

}
