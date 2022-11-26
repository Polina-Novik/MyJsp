<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--library of tags--%>
<%--xml for xml,sql for sql but no one use function lines--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
 <link rel="stylesheet" href="MyStyle12.css"/>
</head>
<body>
<h1>Hello, <c:out value="${user}" default="anonimous"/></h1>
<c:forEach var="product" items="${products}">
   <div> <c:out value="${product.name}, ${product.price} $, ${product.information}" /> <a href="/products?method=delete&id=${product.id}">Delete</a>
       <a href="/products?method=change&id=${product.id}">Change</a></div>
<%--    default="not found"--%>
<%--    div for new Lines--%>

</c:forEach>
<div><a href="/products?method=create">Add new product</a></div>
<%--items name in servlet--%>
<%--like for (Product product:products)--%>
</body>
</html>
