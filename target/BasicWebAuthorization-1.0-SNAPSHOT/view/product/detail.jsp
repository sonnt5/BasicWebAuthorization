<%-- 
    Document   : detail.jsp
    Created on : Jun 3, 2024, 12:07:17 PM
    Author     : sonng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>product detail</title>
    </head>
    <body>
        product info: <br/>
        id:${requestScope.product.key.id} <br/>
        name:${requestScope.product.name} <br/>
        price:${requestScope.product.price} <br/>
        image: <img src="../image?name=${requestScope.product.image}" /> <br/>
        active: ${requestScope.product.active} <br/>
        category: ${requestScope.product.category.name} <br/>
        created by: ${requestScope.product.creator.displayname} <br/>
        created time: ${requestScope.product.create_time} <br/>
        updated by: ${requestScope.product.updater.displayname} <br/>
        updated time: ${requestScope.product.update_time} <br/>
        
    </body>
</html>
