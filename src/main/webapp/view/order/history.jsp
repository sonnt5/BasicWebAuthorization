<%-- 
    Document   : history
    Created on : Jun 5, 2024, 12:03:16 AM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>placed orders</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <table>
            <tr>
                <td>Id</td>
                <td>Date</td>
                <td>Status</td>
                <td></td>
            </tr>
        <c:forEach items="${requestScope.orders}" var="o">
            <tr>
                <td>${o.key.id}</td>
                <td>${o.orderdate}</td>
                <td>${o.status==0?"new":o.status==1?"approved":"rejected"}</td>
                <td><a href="detail?id=${o.key.id}">view</a></td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>
