<%-- 
    Document   : create
    Created on : Jun 3, 2024, 12:03:15 AM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>create new product</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <form action="create" method="post" enctype="multipart/form-data">
            product name:<input type="text" name="name" required><br>
            product price:<input type="text" name="price" required><br>
            image file:<input type="file" id="file" name="file" accept="image/*" required>
            <br/>
            ( only supported: png,img,gif,jpg,bmp,webp,tiff,svg)
            <br>
            active: <input type="checkbox" name="active" value="active" /> <br/>
            category: <select name="categoryid">
                <c:forEach items="${requestScope.categories}" var="c">
                    <option value="${c.key.id}">${c.name}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="submit" name="create product"/>
        </form>    
    </body>
</html>
