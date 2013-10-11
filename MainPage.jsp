<%-- 
    Document   : MainPage
    Created on : Mar 26, 2013, 12:40:13 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
     <body>
        <%
        Long username=(Long)session.getAttribute("username");
            if(username == null) {
            response.sendRedirect("invalidLogin");
            }
        %>
        <h1 align="center">Welcome to the library system!</h1>
        <h2>
            <p>Click <a href="ViewCurrentLoans"> here</a> to View Current Loans </p>
            <p>Click <a href="SearchBooks"> here</a> to Search Books </p>
            <p>Click <a href="ViewReservations"> here</a> to View Reservations </p>
            <p>Click <a href="Logout"> here</a> to logout and exit the system</p>
        </h2>
    </body>
</html>
