<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Apartment</title>
</head>
<body>
    <h1>Add Apartment</h1>

    <form method="post" action="addApartment">
        <label for="price">Price:</label>
        <input type="text" id="price" name="price" required />
        <br><br>
        <input type="submit" value="Add Apartment" />
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
