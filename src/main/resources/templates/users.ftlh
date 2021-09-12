<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <title>Document</title>
    ${_csrf.headerName}
    <script>
        function addUser(firstName, lastName, email, password) {
            // создаем тело запроса
            let body = {
                "firstName": firstName,
                "lastName": lastName,
                "email": email,
                "password": password
            };

            $.ajax({
                type: "POST",
                url: "http://localhost/api/users",
                data: JSON.stringify(body),
                headers: {'X-CSRF-TOKEN' : '${_csrf.token}'},
                success: function (response) {

                    let html = '<tr>' +
                        '           <td>' + response['firstName'] + '</td>' +
                        '           <td>' + response['lastName'] + '</td>' +
                        '           <td>' + response['email'] + '</td>';

                    $('#usersTable > tbody:last-child').append(html);
                },
                dataType: "json",
                contentType: "application/json"
            });
        }
    </script>
</head>
<body>
<#--<h1>${_csrf.parameterName}</h1>-->
<#--<br>-->
<#--<h1>${_csrf.token}</h1>-->
<h1>Всем привет!</h1>
<table id="usersTable">
    <thead>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <#list accounts as account>
        <form id="${account.id}" method="post" action="/users/${account.id}/ban">
        </form>
        <tr>
            <td>${account.firstName}</td>
            <td>${account.lastName}</td>
            <td>${account.email}</td>
            <td><input form="${account.id}" type="submit" value="Заблокировать"></td>
            <td><input form="${account.id}" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"></td>
        </tr>
    </#list>
    </tbody>
    <div>
        <input id="firstName" type="text" name="firstName" placeholder="First Name">
        <input id="lastName" type="text" name="lastName" placeholder="Last Name">
        <input id="email" type="email" name="email" placeholder="Email">
        <input id="password" type="password" name="password" placeholder="Password">
        <button onclick="addUser(
            $('#firstName').val(),
            $('#lastName').val(),
            $('#email').val(),
            $('#password').val(),
        )">Send
        </button>
    </div>
</table>
</body>
</html>