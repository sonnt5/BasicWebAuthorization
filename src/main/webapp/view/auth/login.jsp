<%-- 
    Document   : login
    Created on : May 28, 2024, 12:50:01 AM
    Author     : sonng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="text?name=basic.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form action="login" method="POST">
           username: <input type="text" name="username"/><br/>
           password: <input type="password" name="password"/> <br/>
           <input type="hidden" name="visiturl" value="${param.visiturl}"/>
           <input type="submit" value="Login"/>
           <input type="checkbox" name="remember" value="remember"/> remember for 1 week!
        </form>
        <div class="redText">
            ${requestScope.error}
        </div>
    </body>
</html>
