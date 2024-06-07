<%-- 
    Document   : list
    Created on : Jun 3, 2024, 11:52:16 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
            <table border="1px">
                <tr>
                    <td>id</td>
                    <td>subject</td>
                    <td>time</td>
                    <td></td>
                </tr>
            <c:forEach items="${requestScope.feedbacks}" var="f">
                <tr>
                    <td>${f.key.id}</td>
                    <td>${f.subject}</td>
                    <td>${f.time}</td>
                    <td><a href="detail?id=${f.key.id}">view</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
