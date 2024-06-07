<%-- 
    Document   : greeting
    Created on : May 28, 2024, 12:14:41 AM
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
        <table border="1px">
            <tr>
                <td>
                    <c:if test="${sessionScope.user ne null}">
                        Hello: ${sessionScope.user.username}
                        <br/>
                        <a href="${pageContext.request.contextPath}/logout">logout</a>
                        <br/>
                        <a href="${pageContext.request.contextPath}/home/feature">homepage</a>
                    </c:if>
                    <c:if test="${sessionScope.user eq null}">
                        <a href="${pageContext.request.contextPath}/login">login</a> 
                    </c:if>
                    <br/>
                    <a href="${pageContext.request.contextPath}/product/list">list of products</a>
                    <br/>
                    <a href="${pageContext.request.contextPath}/order/cart">view shopping cart</a>
                    <br/>
                    <a href="${pageContext.request.contextPath}/feedback/send">send a feedback</a>
                </td>
            </tr>
        </table>
    </body>
</html>
