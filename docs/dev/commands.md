---
layout: default
title: Команды
description: Язык машин
---

{% include dev/navigation.md page="commands" %}

# Формат

Для коммуникации сервера и роботов используется текстовый формат передачи данных.

Байты, записываемые сервером и роботами в сокеты, должны обозначать **только символы в кодировке ASCII**.

Формат общения похож на интерфейс командной строки:

- Токены (слова) отделяются натуральным количеством пробелов;
- Символ новой строки `\n` отделяет команды друг от друга;
- Команда начинается с названия инструмента, после этого идут определённые инструментом аргументы, если они есть.

# Общие команды

## Команды роботов

### Авторизация

```sh
authorize $token
```

#### Токен

`char*` `$token` - строка, обозначающая токен авторизации (его необходимо узнать у разработчиков сервера).

#### Ответ сервера

Сервер может принять токен и ответить:

```sh
OK
```

При таком ответе следует перейти к главному циклу.

Сервер может отказать в авторизации и ответить:

```sh
Unauthorized
```

Тогда единственный выход - повторять попытку авторизации снова и снова.
Но в нормальной работе такого ответа встретиться не должно.

#### Пример

```sh
authorize eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoicm9ib3QiLCJpZCI6MSwiaWF0IjoxNTE2MjM5MDIyfQ.rNOD9JICEmckMGhzTsdkyt64DZHPlsAWz5EcJpt1pi4
```