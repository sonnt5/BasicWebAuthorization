<%-- 
    Document   : cart
    Created on : Jun 4, 2024, 6:56:10 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/custom-tags.tld" prefix="custom" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>shopping card</title>

        <script>
            function buyProduct(id)
            {
                document.getElementById("quantity" + id).value = "1";
                document.getElementById("form" + id).submit();
            }
            function removeProduct(id)
            {
                document.getElementById("quantity" + id).value = "-1";
                document.getElementById("form" + id).submit();
            }

        </script>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <c:if test="${sessionScope.order eq null or sessionScope.order.lines.size()eq 0}">
            you did not buy anything!
        </c:if>
        <c:if test="${sessionScope.order ne null and sessionScope.order.lines.size()gt 0}">
            order date: <%= new Date()%>  <br/>
            <table border="1px">
                <tr>
                    <td>product</td>
                    <td>quantity</td>
                    <td>unit price</td>
                <tr>
                    <c:forEach items="${sessionScope.order.lines}" var="line">
                    <tr>
                        <td>${line.key.product.name}</td>
                        <td>
                            <form id="form${line.key.product.key.id}" action="cart" method="POST">
                                ${line.quantity}
                                <input type="hidden" value="" name="quantity" id="quantity${line.key.product.key.id}"/>
                                <input type="hidden" name="id" value="${line.key.product.key.id}"/>
                                <input type="button" value="+1" onclick="buyProduct(${line.key.product.key.id})">
                                <input type="button" value="remove" onclick="removeProduct(${line.key.product.key.id})"/>
                            </form>
                        </td>
                        <td>${line.price}</td>
                    <tr>
                    </c:forEach>
                <tr>
                    <td colspan="2">total:</td>
                    <td>${sessionScope.order.total}</td>
                <tr>        
            </table>
            <custom:AuthorizationControl featureid="/order/create">
                <input type="button" value="checkout" onclick="window.location.href = 'create'" />    
            </custom:AuthorizationControl> 
        </c:if>

    </body>
</html>
