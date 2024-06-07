<%-- 
    Document   : detail
    Created on : Jun 3, 2024, 11:52:19 PM
    Author     : sonng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>feedback detail</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        subject: ${requestScope.feedback.subject} <br/>
        at: ${requestScope.feedback.time} <br/>
        message: <br/>
        ${requestScope.feedback.message}
    </body>
</html>
