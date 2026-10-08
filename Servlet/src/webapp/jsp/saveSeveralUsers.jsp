<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <h1>Добавить пользователей</h1>
        <form action="${pageContext.request.contextPath}/save" method="post">
            <c:forEach begin="1" end="${count}" var="i">
                <fieldset>
                    <legend>Пользователь ${i}</legend>
                    <input name="name_${i}" placeholder="Имя" required maxlength="20">
                    <input name="surname_${i}" placeholder="Фамилия" required maxlength="20">
                    <input name="age_${i}" type="number" placeholder="Возраст" required>
                    <input name="city_${i}" placeholder="Город" maxlength="100">
                    <input name="email_${i}" placeholder="Email" maxlength="100">
                    <input name="phone_${i}" placeholder="Телефон" maxlength="20">
                </fieldset>
            </c:forEach>
            <input type="submit" value="Сохранить">
        </form>
    </body>
</html>