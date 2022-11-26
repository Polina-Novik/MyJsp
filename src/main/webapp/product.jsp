<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="MyStyle12.css"/>
</head>
<body>
<form action="/products" method="post">
    <input type="text" name="id"/>
    <c:if test="${errors['id']!=null}"><div style="color:red"><c:out value="${errors['id']}"/></div> </c:if>
    <input type="text" name="name"/>
    <input type="text" name="price"/>
    <input type="text" name="information"/>
    <c:if test="${errors['price']!=null}"><div style="color:red"><c:out value="${errors['price']}"/></div> </c:if>
    <input type="submit" value="Send"/>
</form>
</body>
</html>
