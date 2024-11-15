<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Apartment List</title>
</head>
<body>
    <h1>Apartment List</h1>

    <c:if test="${not empty message}">
        <div style="color: green;">
            ${message}
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div style="color: red;">
            ${error}
        </div>
    </c:if>

    <!-- НУЖНО ЕЩЁ ДОРАБОТАТЬ, ДОБАВИТЬ НОРМАЛЬНОЕ ОТОБРАЖЕНИЕ АПАРТАМЕНТОВ -->

    <a href="index.jsp">Back to Home</a>
</body>
</html>
