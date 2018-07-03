<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals table</title>
</head>
<body>
<h3>Meals table</h3>
<table border="1">
    <tr style="color: black">
        <th><b>Дата/Время</b></th>
        <th><b>Описание</b></th>
        <th><b>Калории</b></th>
        <th></th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <tr style="${meal.exceed ? 'color: red' : 'color: green' }">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date"/>
            <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd.MM.yyyy HH:mm"/>
            <td>${newParsedDate}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td style="color: black"><a href="mealEdit?id=${meal.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>