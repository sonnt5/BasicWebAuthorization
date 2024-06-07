<%-- 
    Document   : create
    Created on : May 30, 2024, 3:38:04 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>create resource</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <form action="create" method="post" enctype="multipart/form-data">
            resource name:<input type="text" id="name" name="name" required><br>
            file:<input type="file" id="file" name="file" required><br>
            
            <br/>
            <table border="1px">
                <tr>
                    <td></td>
                    <td>Can View</td>
                    <td>Can Edit</td>
                </tr>
                <c:forEach items="${requestScope.users}" var="u">
                    <c:if test="${sessionScope.user.key.id ne u.key.id}">
                        <tr>
                            <td>${u.displayname}(${u.username})</td>
                            <td><input type="checkbox" name="uid_view" value="${u.key.id}"/></td>
                            <td><input type="checkbox" name="uid_edit" value="${u.key.id}"/></td>
                        </tr>  
                    </c:if>
                </c:forEach>
            </table>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
