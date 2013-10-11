<%-- 
    Document   : RenewLoanResult
    Created on : Mar 28, 2013, 8:05:29 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Renew Loan Page</title>
    </head>
     <body>
        <h1>Loan Renewal result:</h1>

        <%
        Long username=(Long)session.getAttribute("username");
        if(username == null)
           {
           response.sendRedirect("invalidLogin");
           }
        
        boolean loanresult = false;
        String Loanstatus = (String)request.getAttribute("loanstatus");
        if (Loanstatus.equals("renewdone"))
            {
            loanresult = true;
            pageContext.setAttribute("loanresultt",new Boolean(loanresult));
            }
        else if (Loanstatus.equals("renewfailed"))
            {
            loanresult = false;
            pageContext.setAttribute("loanresultt",new Boolean(loanresult));
            }
        %>
    </body>
     <c:choose>
                     <c:when test="${loanresultt}">
                         <p> Book sucessfully renewed! </p>
                              </c:when>
                             <c:otherwise>
                     <p>Book renewal failed. You have renewed this book before</p>
                             </c:otherwise>
                 </c:choose>
    <h2 <p>Back to <a href="MainPage"</a> MainPage </p> </h2>
</html>