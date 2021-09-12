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
<script>
    function sendFile() {
        let formData = new FormData();
        let file = ($('#file'))[0]['files'];
        formData.append('${_csrf.parameterName}', '${_csrf.token}');
        formData.append("file", file[0]);

        $.ajax({
            type: "POST",
            url: "/files",
            data: formData,
            processData: false,
            contentType: false
        }).done(function () {
            alert("Все хорошо");
        })
    }
</script>
<body>
<div>
    <input type="file" id="file" name="file" placeholder="Путь к файлу"/>
    <button onclick="sendFile()">Загрузить файл</button>
</div>

</body>
</html>