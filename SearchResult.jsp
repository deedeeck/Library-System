<%-- 
    Document   : SearchResult
    Created on : Mar 27, 2013, 8:27:16 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, ejb.BookEntity" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <title>Search Books Page</title>
        <script src="http://code.jquery.com/jquery-latest.js">
        </script>
    </head>
     <body>
        <h1 align="center">Here are your searched books</h1>

        <%
             Long username=(Long)session.getAttribute("username");
            if(username == null) {
            response.sendRedirect("invalidLogin");
            } else {
                 pageContext.setAttribute("memberId",new Long(username));
            ArrayList titleDetail = (ArrayList)request.getAttribute("booksByTitle");
            BookEntity be = new BookEntity();
            boolean onloan = false;
            //int onloan = 0; // 0 for no loan
            %>
            <table border = "1">
              <th>Book ISBN</th>
              <th>Book Title</th>
              <th>Book Author</th>
              <th>Book Publisher</th>
              <th>Copy Number</th>
              <th>Loan status</th>
              <th>Reservation</th>
            <%
            for(int i=0; i< titleDetail.size(); i++)
                {
                    be = (BookEntity)titleDetail.get(i);
          %>
          <tr>
              <td align="center"> <p> <%= be.getTitle().getISBN() %> </td>
              <td> <p> <%= be.getTitle().getTitle() %> </td>
              <td> <p> <%= be.getTitle().getAuthor() %> </td>
              <td> <p> <%= be.getTitle().getPublisher() %> </td>
              <td align="center"> <p> <%= be.getCopy() %> </td>
               <%  if(be.getMember() == null)
                    {
                        onloan = false;
                        pageContext.setAttribute("onloan",new Boolean(onloan));
                    }
                    else
                        {
                        onloan = true;
                        pageContext.setAttribute("onloan",new Boolean(onloan));
                        }
                %>
                <c:choose>
                     <c:when test="${onloan}">

                         <td>       <p> This book is currently on loan to member <%= be.getMember().getMemberId() %>
                         <p> Due date of book is <%= be.getDuedate() %> </td>
                              </c:when>
                             <c:otherwise>
                             <td>  <p>This book is available for loan</p> </td>
                             </c:otherwise>
                 </c:choose>
                             <td>
                    <form action="AddReservation" method="POST">
                             <input type="hidden" name="memberId" value="${memberId}">
                             <input type="hidden" name="bookId" value=<%= be.getBookId()%>>
                             <p><input type="submit" value="Click to reserve this book" ></p>
                         </form>
                             </td>
         </tr>
          <%--
          %>
                <p> Book ISBN = <%= be.getTitle().getISBN() %>
                <p> Book Title = <%= be.getTitle().getTitle() %>
                <p> Book Author =  <%= be.getTitle().getAuthor() %>
                <p> Book Publisher = <%= be.getTitle().getPublisher() %>
                <p> Copy Number = <%= be.getCopy() %>
                <%  if(be.getMember() == null)
                    {
                        onloan = false;
                        pageContext.setAttribute("onloan",new Boolean(onloan));
                    }
                    else
                        {
                        onloan = true;
                        pageContext.setAttribute("onloan",new Boolean(onloan));
                        }
                %>

                                 
                 <c:choose>
                     <c:when test="${onloan}">

                         <p> This book is currently on loan to member <%= be.getMember().getMemberId() %>
                         <p> Due date of book is <%= be.getDuedate() %>
                              </c:when>
                             <c:otherwise>
                     <p>This book is available for loan</p>
                             </c:otherwise>
                 </c:choose>

                    <form action="AddReservation" method="POST">
                             <input type="hidden" name="memberId" value="${memberId}">
                             <input type="hidden" name="bookId" value=<%= be.getBookId()%>>
                             <p><input type="submit" value="Click to reserve this book" ></p>
                         </form>
                 --%>
                    
                             <%--
                             <form action="AddReservation" method="POST">
                             <input type="hidden" name="memberId" value="${memberId}">
                             <input type="hidden" name="bookId" value=<%= be.getBookId()%>>
                             <p><input type="submit" value="Click to reserve this book"/></p>
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
