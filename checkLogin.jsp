<%--
    Document   : checkLogin
    Created on : Mar 25, 2013, 8:38:21 PM
    Author     : yh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check login Page</title>
    </head>
     <body>
        <h1><%
                 Long username=(Long)session.getAttribute("username");

                 try {
                     if(username==null)
                        {
                         response.sendRedirect("invalidLogin");
                        }

                     if(username!=null)
                        {
                         response.sendRedirect("MainPage");
                        }
                 } catch (Exception e) { %>
                 <h2> <%= (String)e.getMessage() %></h2>
            <%   } %>
        %></h1>
    </body>
</html>
