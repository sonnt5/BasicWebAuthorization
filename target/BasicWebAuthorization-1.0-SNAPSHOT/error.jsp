<%-- 
    Document   : error
    Created on : May 29, 2024, 12:36:47 AM
    Author     : sonng
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error Page</title>
    <link href="${pageContext.request.contextPath}/text?name=basic.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    Something went wrong! <br/>
    We encountered an error while processing your request.
    <br/>
    <br/>
    <br/>
    <br/>
    <div class="redText">
        Hi red team: here is may useful for you: <br/>
        ${pageContext.exception}
    </div>
</body>
</html>
