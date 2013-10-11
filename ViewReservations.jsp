<%-- 
    Document   : ViewReservations
    Created on : Mar 28, 2013, 3:27:27 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, ejb.ReservationEntity" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <title>View Reservation Page</title>
    </head>
     <body>
        <h1>Here are your current reservations</h1>
         <%
            Long username=(Long)session.getAttribute("username");
            if(username == null)
                {
            response.sendRedirect("invalidLogin");
                }
            else
            {
            ArrayList reservationDetail = (ArrayList)request.getAttribute("reservations");
            ReservationEntity re = new ReservationEntity();
%>
            <table border = "1">
              <th>Reservation ID</th>
              <th>Reservation Date</th>
              <th>Book Title</th>
              <th>Book Author</th>
              <th>Renew Loan</th>
           <%
            for(int i=0; i< reservationDetail.size(); i++)
                {
                    re = (ReservationEntity)reservationDetail.get(i);
          %>
          <tr>
              <td align ="center"><p> <%= re.getReservationId() %> </p></td>
              <td><p> <%= re.getReservationDate() %> </p></td>
              <td align ="center"><p> <%= re.getBook().getTitle().getTitle() %> </p></td>
              <td align="center"><p> <%= re.getBook().getTitle().getAuthor() %> </td>
              <td><p> <form action="DeleteReservation" method="POST">
                             <input type="hidden" name="reservationId" value=<%= re.getReservationId()%>>
                             <p><input type="submit" value="Click to delete this reservation"/></p>
                         </form> </p></td>
          </tr>
          <%--
                <p> Reservation Id = <%= re.getReservationId() %>
                <p> Reservation Date = <%= re.getReservationDate() %>
                <p> Book Title =  <%= re.getBook().getTitle().getTitle() %>
                <p> Book Author = <%= re.getBook().getTitle().getAuthor() %>
                    <form action="DeleteReservation" method="POST">
                             <input type="hidden" name="reservationId" value=<%= re.getReservationId()%>>
                             <p><input type="submit" value="Click to delete this reservation"/></p>
                         </form>
          --%>
        <%
                }
            %>
            </table>
            <%
            }
        %>
        <h2 <p>Back to <a href="MainPage"</a> MainPage </p> </h2>
    </body>
</html>
