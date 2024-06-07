<%-- 
    Document   : list
    Created on : May 27, 2024, 10:52:19 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "fn" 
           uri = "http://java.sun.com/jsp/jstl/functions" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>products</title>
        <link href="../text?name=product.css" rel="stylesheet" type="text/css"/>
        <script src="../text?name=pagger.js" type="text/javascript"></script>
        <link href="../text?name=pagger.css" rel="stylesheet" type="text/css"/>
        <script>
            function buyProduct(id,name)
            {
                alert("one more "+name +" is added to your shopping cart. check your cart!");
                var form = document.getElementById("frmBuy"+id);
                form.submit();
            }
        </script>
    </head>
    <body>
        <jsp:include page="../common/greeting.jsp"></jsp:include>
        <c:forEach items="${requestScope.categories}" var="c">
            <a 
                <c:if test="${(c.key.id eq requestScope.categoryid) 
                              or (c.key.id eq 0 and requestScope.categoryid eq null)}">
                      class="redText"
                </c:if>
                href="list?category=${c.key.id}">${c.name}</a>
        </c:forEach>
        <br/>
        <br/>
        <div class="pagger"> </div>
        <br/>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Price</td>
                <td>Image</td>
                <td></td>
            </tr>
            <c:forEach items="${requestScope.products}" var="p">
                <tr>
                    <td>${p.key.id}</td>
                    <td>${p.name}</td>
                    <td>${p.price}</td>
                    <td><img src="../image?name=${p.image}" /></td>
                    <td>
                        <input type="button" value="buy" onclick="buyProduct(${p.key.id},'${p.name}');"/>
                        <form action="../order/cart" id="frmBuy${p.key.id}" method="POST">
                        <input type="hidden" name="id" value="${p.key.id}"/>
                        <input type="hidden" name="quantity" value="1"/>
                        <input type="hidden" name="param" value="?category=${requestScope.categoryid}&page=${requestScope.pageindex}"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <div class="pagger"/></div>
    <script>
                pagger(${requestScope.pageindex}
                ,${requestScope.totalpage}
                , 2
                        , 'list'
                        , 'category=${requestScope.categoryid}'
                        );
    </script>
</body>
</html>
