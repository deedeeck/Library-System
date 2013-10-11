package servlet;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import ejb.MemberManagerRemote;
import ejb.BookManagerRemote;
import ejb.ReservationManagerRemote;
import ejb.MemberEntity;
import ejb.BookEntity;
import appHelper.MemberState;
import appHelper.BookState;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class TLSSystemServlet extends HttpServlet {

    @EJB
    private MemberManagerRemote mm;
    @EJB
    private BookManagerRemote bm;
    @EJB
    private ReservationManagerRemote rm;
    EntityManager em;
    private ArrayList data = null;

    public void init() {
        System.out.println("TLSSystemServlet: init()");

    }
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

       System.out.println("TLSSystemServlet processRequest()");
       
        try {
        RequestDispatcher dispatcher;
        ServletContext servletContext = getServletContext();

        String page = request.getPathInfo();
        page        = page.substring(1);

        if("login".equals(page))
        {
            System.out.println("In TLSSystemServlet");
        }
        else if("checkLogin".equals(page))
        {
            System.out.println("In TLSSystemServlet checkLogin");
            verifyLogin(request);
        }
        else if ("invalidLogin".equals(page))
        {
            System.out.println("In TLSSystemServlet invalidLogin");
        }
        else if ("MainPage".equals(page))
        {
            System.out.println("In TLSSystemServlet Mainpage");
        }
        else if ("ViewCurrentLoans".equals(page))
        {
            data = retrieveLoans(request);
            request.setAttribute("loans", data);
        }
        else if ("SearchBooks".equals(page))
        {
            System.out.println("In TLSSystemServlet SearchBooks");
        }
        else if ("SearchResult".equals(page))
        {
            System.out.println("In TLSSystemServlet SearchByTitle");
            data = getSearchResult(request);
            request.setAttribute("booksByTitle", data);
        }
        else if ("AddReservation".equals(page))
        {
            System.out.println("In TLSSystemServlet AddReservation");
            addReservation(request);
        }
        else if ("ViewReservations".equals(page))
        {
            System.out.println("In TLSSystemServlet ViewReservations");
            data = retrieveReservations(request);
            request.setAttribute("reservations", data);
        }
        else if ("DeleteReservation".equals(page))
        {
            System.out.println("In TLSSystemServlet DeleteReservation");
            deleteReservation(request);
        }
        else if ("RenewLoanResult".equals(page))
        {
            System.out.println("In TLSSystemServlet RenewLoanResult");
            String loanstatus;
            if(renewLoan(request))
            {
                loanstatus = "renewdone";
            }
            else
            {
                loanstatus = "renewfailed";
            }
            request.setAttribute("loanstatus", loanstatus);
            
        }
        else if ("Logout".equals(page))
        {
            System.out.println("In TLSSystemServlet Logout");
            HttpSession session = request.getSession(true);
            session.removeAttribute("username");
        }

        dispatcher = servletContext.getNamedDispatcher(page);
          if (dispatcher == null)
          {
           dispatcher = servletContext.getNamedDispatcher("Error");
          }

        dispatcher.forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            log("Exception in TLSSystemServlet.processRequest()");
        }
    }

    private void verifyLogin(HttpServletRequest request)
    {
      String Stringusername = request.getParameter("username");

      long username = Long.parseLong(Stringusername);
      String password = request.getParameter("password");

      List<MemberEntity> members = new ArrayList<MemberEntity>();
               members = mm.getMembers();

        for(int i=0 ; i<members.size() ; i++) {
            if(members.get(i).getPassword().equals(password) && members.get(i).getMemberId().equals(username))
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                return;
            }
      }
    }


    private ArrayList retrieveLoans(HttpServletRequest request)
    {
        System.out.println("In TLSSystemServlet: Retrieving Loans");

        HttpSession session = request.getSession(true);
        Long memberId = (Long)session.getAttribute("username");
        ArrayList books = new ArrayList();

        try{
        
        books =bm.getBooks(memberId);
        return books;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return books;
        
    }

    private ArrayList getSearchResult(HttpServletRequest request)
    {
        String title = request.getParameter("title");
        ArrayList books = new ArrayList();

         books = bm.getBooksFromSearch(title);       
            return books;
    }

    private void addReservation(HttpServletRequest request)
    {
        String StringmemberId = request.getParameter("memberId");
        Long memberId = Long.parseLong(StringmemberId);
        String StringbookId = request.getParameter("bookId");
        Long bookId =Long.parseLong(StringbookId);

        Date reservationDate = new Date();

        rm.addReservation(memberId, bookId, reservationDate);
    }

    private ArrayList retrieveReservations(HttpServletRequest request)
    {
        System.out.println("In TLSSystemServlet: Retrieving Reservations");
        HttpSession session = request.getSession(true);
        Long memberId = (Long)session.getAttribute("username");

        ArrayList reservations = new ArrayList();

        try{

        reservations =rm.getReservations(memberId);
        return reservations;

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

       return reservations;
    }

    private void  deleteReservation(HttpServletRequest request)
    {
        String StringreservationId = request.getParameter("reservationId");
        Long reservationId = Long.parseLong(StringreservationId);

        rm.removeReservation(reservationId);
    }

    public boolean renewLoan(HttpServletRequest request)
    {
        String StringbookId = request.getParameter("bookIdforRenewLoan");
        Long bookId = Long.parseLong(StringbookId);
       
        if(rm.extendLoan(bookId))
            return true;
        else
            return false;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

}
