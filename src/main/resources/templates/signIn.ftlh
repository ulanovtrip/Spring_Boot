<!doctype html>
<html lang="ru">
<head>
    <title>Вход</title>
</head>
<body>

<#--специальный токен для защиты от csrf атаки, каждая из страниц будет подписываться этим токеном-->
<#--это конструкция выводит на страницу токен-->
<h1>${_csrf.parameterName}</h1>
<br>
<h1>${_csrf.token}</h1>

<form action="/signIn" method="post">

    <label for="email">Введите email:
        <input id="email" name="email" placeholder="Email">
    </label>

    <#--отправка токена на сервер скрытым полем-->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <label for="password">Введите пароль:
        <input id="password" name="password" type="password" placeholder="Password">
        <label><input type="checkbox" name="remember-me">Запомни меня</label>
    </label>

    <input type="submit" value="Вход">

</form>

</body>
</html>
