<%-- 
    Document   : feature
    Created on : May 29, 2024, 9:30:48 AM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>homepage of ${sessionScope.user.displayname}</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <br/>
        List of features for you:
        <table>
        <c:forEach items="${requestScope.features}" var="f">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}${f.url}">${f.name}</a>
                </td>
            </tr>
        </c:forEach>
        </table>    
    </body>
</html>
