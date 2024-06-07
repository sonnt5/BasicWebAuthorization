<%-- 
    Document   : list
    Created on : Jun 2, 2024, 12:17:35 AM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>shared resources with ${sessionScope.user.displayname}</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Can View</td>
                <td>Can Edit</td>
            </tr>
            <c:forEach items="${requestScope.resources}" var="r">
                <tr>
                    <td>${r.key.id}</td>
                    <td>${r.name}</td>
                    <td>
                        <c:forEach items="${r.users}" var="ur">
                            <c:if test="${ur.canView}">
                                <a href="detail?id=${r.key.id}">View</a>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${r.users}" var="ur">
                            <c:if test="${ur.canEdit}">
                                <a href="edit?id=${r.key.id}">Edit</a>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>   
            </c:forEach>
        </table>
    </body>
</html>
