<%-- 
    Document   : detail
    Created on : Jun 5, 2024, 9:16:52 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>view product</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <c:if test="${requestScope.order ne null}">
            id: ${order.key.id}<br/>
            created date: ${order.orderdate} <br/>
            status:${order.status==0?"new":order.status==1?"approved":"rejected"}<br/>
            customer: ${order.customer.displayname} <br/>
            <c:if test="${order.sale ne null}">
            sale: ${order.sale.displayname} <br/>
            processed date: ${order.processdate} <br/>
            </c:if>
            <table border="1px">
                <tr>
                    <td>product</td>
                    <td>quantity</td>
                    <td>unit price</td>
                <tr>
                    <c:forEach items="${requestScope.order.lines}" var="line">
                    <tr>
                        <td>${line.key.product.name}</td>
                        <td>
                            ${line.quantity}
                        </td>
                        <td>${line.price}</td>
                    <tr>
                    </c:forEach>
                <tr>
                    <td colspan="2">total:</td>
                    <td>${requestScope.order.total}</td>
                <tr>        
            </table>
        </c:if>
        <c:if test="${requestScope.order eq null}">
            order does not exist!
        </c:if>
    </body>
</html>
