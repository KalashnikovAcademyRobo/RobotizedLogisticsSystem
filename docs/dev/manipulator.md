---
layout: default
title: Манипулятор
description: Людьми манипулировать плохо. Вещами - за радость
mermaid: true
---

{% include dev/navigation.md page="manipulator" %}

# Состояния

<div class="mermaid">
stateDiagram-v2
    state "Цикл" as cycle
    state "Авторизация" as auth
    
    [*] --> auth
    auth --> cycle: Успешная авторизация

    state cycle {
        state gettingOrderFork &lt;&lt;fork&gt;&gt;
        state givingOrderJoin &lt;&lt;join&gt;&gt;

        state "Ожидание" as standBy
        state "Взятие заказа" as gettingOrder
        state "Ожидание колёсного робота" as waitingForWheelRobot
        state "Отдача заказа" as givingOrder

        [*] --> standBy
        standBy --> gettingOrderFork: Команда сервера
        gettingOrderFork --> gettingOrder
        gettingOrderFork --> waitingForWheelRobot
        gettingOrder --> givingOrderJoin: Самостоятельное определение
        waitingForWheelRobot --> givingOrderJoin: Команда сервера
        givingOrderJoin --> givingOrder
        givingOrder --> standBy: Самостоятельное определение
    }

    note left of cycle: В идеальных условиях не прерывается

    cycle --> [*]: Ручная остановка робота

</div>

# Команды

Команды следуют правилам, описанным [здесь](/RobotizedLogisticsSystem/dev/commands).

## Команды робота

### Отдача заказа

```sh
finished
```

Сообщает серверу о том, что робот отдал заказ.
После этой команды сервер имеет право отправить [команду взятия заказа](#взятие-заказа).

## Команды сервера

### Взятие заказа

```sh
pick
```

Сообщает роботу о необходимости взять заказ.
После этой команды сервер имеет право оправить [команду отдачи заказа](#отдача-заказа).

### Отдача заказа

```sh
put
```

Сообщает роботу о необходимости отдать заказ.
По окончании исполнения данной команды роботу следует [сообщить об этом серверу](#отдача-заказа).
