<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reserve Apartment</title>
</head>
<body>
    <h1>Reserve Apartment</h1>

    <form method="post" action="reserveApartment">
        <label for="apartmentId">Apartment ID:</label>
        <input type="text" id="apartmentId" name="apartmentId" required />
        <br><br>

        <label for="guestName">Guest Name:</label>
        <input type="text" id="guestName" name="guestName" required />
        <br><br>

        <input type="submit" value="Reserve Apartment" />
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
