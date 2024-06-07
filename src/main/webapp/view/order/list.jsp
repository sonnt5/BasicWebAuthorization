<%-- 
    Document   : list
    Created on : Jun 5, 2024, 9:37:26 PM
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
                    <th>Id</th>
                    <th>Date</th>
                    <th>customer</th>
                    <th>sale</th>
                    <th>processed date</th>
                    <th>lines</th>
                    <th></th>
                </tr>
            <c:forEach items="${requestScope.orders}" var="o">
                <tr>
                    <td>${o.key.id}</td>
                    <td>${o.orderdate}</td>
                    <td>${o.customer.displayname}</td>
                    <td>${(o.sale ne null)?o.sale.displayname:""}</td>
                    <td>${o.processdate}</td>
                    <td>
                        <table border="1px">
                            <tr>
                                <td>product</td>
                                <td>quantity</td>
                                <td>unit price</td>
                            <tr>
                                <c:forEach items="${o.lines}" var="line">
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
                                <td>${o.total}</td>
                            <tr>        
                        </table>

                    </td>
                    <td>
                        <c:if test="${o.status eq 0}">
                            <a href="review?id=${o.key.id}&status=1">approve</a>
                            <a href="review?id=${o.key.id}&status=2">reject</a>
                        </c:if> 
                        <c:if test="${o.status eq 1}">
                            approved
                        </c:if>
                        <c:if test="${o.status eq 2}">
                            rejected
                        </c:if>    
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
