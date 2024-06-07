<%-- 
    Document   : manage
    Created on : Jun 3, 2024, 9:37:53 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>manage products</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Price</td>
                <td>Image</td>
                <td></td>
            </tr>
            <c:forEach items="${requestScope.products}" var="p">
            <tr>
                <td>${p.key.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td><img src="../image?name=${p.image}" /></td>
                <td>
                    <a href="detail?id=${p.key.id}">view</a>
                    <a href="update?id=${p.key.id}">edit</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
