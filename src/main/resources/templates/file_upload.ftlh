<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>

</head>

<#--скрипт для загрузки файлов-->
<script>
    function sendFile() {
        // с помощью jquery руками собираем форму
        let formData = new FormData();
        // сам файл берём из инпута, $('#file') - получить доступ по id
        let file = ($('#file'))[0]['files'];
        // передаём csrf токен
        formData.append('${_csrf.parameterName}', '${_csrf.token}');
        // передаём один файл
        formData.append("file", file[0]);

        // ajax запрос для отправки на сервер
        // этот запрос вызовет public FileUrlDto saveFile из FilesController
        $.ajax({
            type: "POST",
            url: "/files", // отправка на этот адрес
            data: formData, // данные
            processData: false,
            contentType: false
        }).done(function () {
            // по окончанию запроса будет алерт
            alert("Все хорошо");
        })
    }
</script>

<body>

<div>
    <#--здесь описан отдельный input для загрузки файла-->
    <input type="file" id="file" name="file" placeholder="Путь к файлу"/>
    <#--кнопка, по нажатию вызывается функция-->
    <button onclick="sendFile()">Загрузить файл</button>
</div>

</body>
</html>