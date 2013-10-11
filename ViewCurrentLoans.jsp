<%--
    Document   : ViewCurrentLoans
    Created on : Mar 26, 2013, 12:41:19 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, ejb.BookEntity" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <title>View Current Loans Page</title>
    </head>
     <body>
        <h1 align="center">Here are your current loans</h1>

        <%
        Long username=(Long)session.getAttribute("username");
            if(username == null)
                {
                response.sendRedirect("invalidLogin");
                } 
        else
            {
            ArrayList loansDetail = (ArrayList)request.getAttribute("loans");           
            BookEntity be = new BookEntity(); %>
            <table border="1">
              <th>Book ID</th>
              <th>Copy Number</th>
              <th>Loan Date</th>
              <th>Due Date</th>
              <th>Renew Loan</th>
              <%
            for(int i=0; i< loansDetail.size(); i++)
                {
                    be = (BookEntity)loansDetail.get(i);
          %>
     
          <tr>
              <td align="center"><p> <%=be.getBookId() %> </td>
              <td align="center"><p> <%=be.getCopy() %> </td>
              <td><p> <%= be.getLoandate() %></td>
              <td><p> <%= be.getDuedate() %></td>
              <td>
              <form action="RenewLoanResult" method="POST">
                        <input type="hidden" name="bookIdforRenewLoan" value=<%= be.getBookId()%>>
                     <p><input type="submit" value="Click to renew loan for this book"/></p>
                    </form>
                     </td>
          </tr>
     
<%--
                <p> Book Id = <%= be.getBookId() %>
                <p> Copy Number = <%= be.getCopy() %>
                <p> Loan date =  <%= be.getLoandate() %>
                <p> Due date = <%= be.getDuedate() %>
                <form action="RenewLoanResult" method="POST">
                        <input type="hidden" name="bookIdforRenewLoan" value=<%= be.getBookId()%>>
                     <p><input type="submit" value="Click to renew loan for this book"/></p>
                    </form>
--%>
        <%
                }
        %>
            </table>
        <%
            }
        %>
        <h3> <p>Back to <a href="MainPage"</a> MainPage </p> </h3>
       

    </body>
</html>
