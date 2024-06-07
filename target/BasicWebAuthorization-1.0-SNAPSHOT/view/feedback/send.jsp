<%-- 
    Document   : send
    Created on : Jun 3, 2024, 9:50:20 PM
    Author     : sonng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>end feedback</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <form action="send" method="post">
            subject:<input type="text" id="subject" name="subject" required/><br/>
            message:
            <textarea id="message" name="message" required></textarea><br/>
            <input type="submit" value="send"/>
        </form>
    </body>
</html>
