<%-- 
    Document   : AddReservation
    Created on : Mar 28, 2013, 1:00:48 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Reservation Page</title>
    </head>
     <body>
        <%
        Long username=(Long)session.getAttribute("username");
            if(username == null) {
            response.sendRedirect("invalidLogin");
            }
        %>
        <h1>Reservation added!</h1>

        <h2 <p>Back to <a href="MainPage"</a> MainPage </p> </h2>
    </body>
</html>
