<%-- 
    Document   : update
    Created on : Jun 3, 2024, 12:03:19 AM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>update product</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <form action="update" method="post">
            <input type="hidden" name="id" value="${param.id}"/>
        product name:<input type="text" value="${requestScope.product.name}" name="name" required><br>
            product price:<input type="text" value="${requestScope.product.price}" name="price" required><br>
            image file: ${requestScope.product.image}
            <br>
            active: <input type="checkbox" 
                           <c:if test="${requestScope.product.active}">
                               checked="checked"
                           </c:if>
                           name="active" value="active" /> <br/>
            category: <select name="categoryid">
                <c:forEach items="${requestScope.categories}" var="c">
                    <option 
                        <c:if test="${requestScope.product.category.key.id eq c.key.id}">
                            selected="selected"
                        </c:if>
                        value="${c.key.id}">${c.name}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="submit" name="create product"/>
        </form>    
    </body>
</html>
