<%-- 
    Document   : SearchBooks
    Created on : Mar 27, 2013, 12:02:10 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <title>Search Books Page</title>
    <h1 align="center"> Search Page
    </h1>
    </head>
     <body>
        <%
        Long username=(Long)session.getAttribute("username");
            if(username == null) {
            response.sendRedirect("invalidLogin");
            }
        %>
        <h2>
             <form action="SearchResult" method="POST">
            <table>
                <tr>
                    <td align="right">Enter Author or Title name</td>
                    <td align="left"><input type="text" name="title" length="30"/></td>
                </tr>
                
            </table>
            <p><input type="submit" value="Submit"/></p>
             </form>
             
            <p>Back to <a href="MainPage"</a> MainPage </p> 
        </h2>
    </body>
</html>
