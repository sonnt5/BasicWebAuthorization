<%-- 
    Document   : edit
    Created on : Jun 1, 2024, 10:32:04 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>edit resource ${requestScope.resource.name}</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <form action="edit" method="post">
            <input type="hidden" name="id" value="${requestScope.resource.key.id}"/>
            resource name:
            <input type="text" name="name" value="${requestScope.resource.name}" required/>
            <br/>
            <a href="file?id=${requestScope.resource.key.id}"> ${requestScope.resource.file_url} </a>
            <br/>
            <table border="1px">
                <tr>
                    <td></td>
                    <td>Can View</td>
                    <td>Can Edit</td>
                </tr>
                <c:forEach items="${requestScope.users}" var="u">
                    <c:if test="${sessionScope.user.key.id ne u.key.id and requestScope.resource.creator.key.id ne u.key.id }">
                        <tr>
                            <td>${u.displayname}</td>
                            <td><input type="checkbox"
                                       <c:forEach items="${requestScope.resource.users}" var="ur">
                                           <c:if test="${ur.key.user.key.id eq u.key.id and ur.canView}">
                                               checked="checked" 
                                           </c:if>
                                       </c:forEach>
                                       name="uid_view" value="${u.key.id}"/></td>
                            <td><input type="checkbox" name="uid_edit"
                                       <c:forEach items="${requestScope.resource.users}" var="ur">
                                           <c:if test="${ur.key.user.key.id eq u.key.id and ur.canEdit}">
                                               checked="checked" 
                                           </c:if>
                                       </c:forEach>
                                       value="${u.key.id}"/></td>
                        </tr>  
                    </c:if>
                </c:forEach>
            </table>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
