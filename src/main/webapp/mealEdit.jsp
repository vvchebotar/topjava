<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить блюдо</title>
</head>
<body>

<h3>Изменить блюдо</h3>
<form method="POST" action=doEditMeal?id=${meal.id}&version=${meal.version}>
    <table border="1">
        <tr>
            <td>Дата/Время</td>
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date"/>
            <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd.MM.yyyy HH:mm"/>
            <td><input type="datetime-local" name="dateTime" value="${newParsedDate}"/></td>
        </tr>
        <tr>
            <td>Описание</td>
            <td><input type="text" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="text" name="calories" value="${meal.calories}"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Сохранить"/>
                <a href="meals">Отмена</a>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <a style="color: red;"
                   href="doDeleteMeal?id=${meal.id}">Delete</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>