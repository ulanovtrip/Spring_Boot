<!doctype html>
<html lang="ru">

<#--импорт модуля спринга, т.е. отдельной библиотеки-->
<#import "spring.ftl" as spring/>

<head>
    <title>Регистрация</title>
    <#--стиль для ошибок-->
    <style>
        .error {
            color: red;
        }
    </style>
</head>

<body>

<#--ссылки переключения языка-->
<p><a href="?lang=ru">РУ</a></p>
<p><a href="?lang=en">EN</a></p>

<#--связываем форму .ftlh с конкретным объектом в java-->
<@spring.bind "signUpForm"/>

<form action="/signUp" method="post">

    <#--если есть ошибка где имя и фамилия одинаковые-->
    <#if namesError??>
        <p style="color: red"><@spring.message 'error.names.bad'/></p>
    </#if>

    <label for="firstName"><@spring.message 'signUp.label.for.firstName'/>
        <@spring.formInput "signUpForm.firstName"/>
        <@spring.showErrors "<br>" "error"/>
    </label>

    <label for="lastName"><@spring.message 'signUp.label.for.lastName'/>
        <@spring.formInput "signUpForm.lastName"/>
        <@spring.showErrors "<br>" "error"/>
    </label>

    <label for="email"><@spring.message 'signUp.label.for.email'/>
        <@spring.formInput "signUpForm.email"/>
        <@spring.showErrors "<br>" "error"/>
    </label>

    <label for="password"><@spring.message 'signUp.label.for.password'/>
        <@spring.formInput "signUpForm.password"/>
        <@spring.showErrors "<br>" "error"/>
    </label>

    <#--скрытый токен-->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <input type="submit" value="Регистрация">
    <#--    <input type="submit" value=<@spring.message 'signUp.label.for.registrationBtn'/>-->

</form>
</body>
</html>
