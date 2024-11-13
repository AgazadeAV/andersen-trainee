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

    <table border="1">
        <thead>
            <tr>
                <th>Apartment ID</th>
                <th>Price</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="apartment" items="${apartments}">
                <tr>
                    <td>${apartment.id}</td>
                    <td>${apartment.price}</td>
                    <td>${apartment.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="addApartment">Add New Apartment</a>
</body>
</html>
