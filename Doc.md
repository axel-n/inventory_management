# Документация на коленке
при старте проекта импортируются демо данные в h2 базу (ничего настраивать не нужно)
[postman коллекция](https://www.getpostman.com/collections/c53a6ea89b5ff710d90c)
[онлайн документация](https://documenter.getpostman.com/view/6588996/SVfNv9At?version=latest)

## авторизация
1. можно [войти](https://documenter.getpostman.com/view/6588996/SVfNv9At?version=latest#e05f5e8f-de45-4970-a57a-faa4b39f7835) под 2 учетками 
 - admin (может читать/обновлять/создавать/удалять данные). логин admin@gmail.com, пароль 123
 - guest (может читать данные). логин guest1@gmail.com, пароль 123
2. после авторизации, можно что-то другое вызывать

## запрос данных
- запрашивать нужно использую тег Authorization в заголовке + значение: Bearer ${токен}
- примеры описаны в онлайн документации
