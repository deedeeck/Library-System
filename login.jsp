<%--
    Document   : login
    Created on : Mar 25, 2013, 8:37:50 PM
    Author     : Yh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
     <body>
        <h1 align="center">
            Login Page
        </h1>
        <h2>
             <form action="checkLogin" method="POST">
            <table>
                <tr>
                    <td align="right">Username:</td>
                    <td align="left"><input type="text" name="username" length="30"/></td>
                </tr>
                <tr>
                    <td align="right">Password:</td>
                    <td align="left"><input type="password" name="password" length="30"/></td>
                </tr>
            </table>
            <p><input type="submit" value="Login"/></p>
        </form>
        </h2>
    </body>
</html>
