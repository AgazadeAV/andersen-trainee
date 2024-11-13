<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Release Apartment</title>
</head>
<body>
    <h1>Release Apartment</h1>

    <form method="post" action="releaseApartment">
        <label for="apartmentId">Apartment ID:</label>
        <input type="text" id="apartmentId" name="apartmentId" required />
        <br><br>
        <input type="submit" value="Release Apartment" />
    </form>

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

    <a href="apartments">Back to Apartments</a>
</body>
</html>
