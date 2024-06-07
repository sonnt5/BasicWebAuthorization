<%-- 
    Document   : detail
    Created on : May 31, 2024, 5:31:21 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>resource info</title>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
            resource info: <br/>
            id: ${requestScope.resource.key.id} <br/>
        name: ${requestScope.resource.name} <br/>
        created by: ${requestScope.resource.creator.displayname} <br/>
        created time: ${requestScope.resource.create_time} <br/>
        <c:if test="${requestScope.resource.updater ne null}"> 
            updated by: ${requestScope.resource.updater.displayname} <br/>
            updated time: ${requestScope.resource.update_time} <br/>
        </c:if>
        <a href="file?id=${requestScope.resource.key.id}"> ${requestScope.resource.file_url} </a>   
    </body>
</html>
