---
layout: default
title: Колёсный робот
description: Поехали!
mermaid: true
---

{% include dev/navigation.md page="wheel" %}

# Состояния

<div class="mermaid">
stateDiagram-v2
    state "Цикл" as cycle
    state "Авторизация" as auth
    
    [*] --> auth
    auth --> cycle: Успешная авторизация

    state cycle {
        state "Ожидание на складе" as warehouseStandBy
        state "Получение заказа" as gettingOrder
        state "Движение до манипулятора" as movingToManipulator
        state "Отдача заказа" as givingOrder
        state "Движение до склада" as movingToWarehouse

        [*] --> warehouseStandBy
        warehouseStandBy --> gettingOrder: Команда сервера
        gettingOrder --> movingToManipulator: Команда сервера
        movingToManipulator --> givingOrder: Команда сервера
        givingOrder --> movingToWarehouse: Команда сервера
        movingToWarehouse --> warehouseStandBy: Команда сервера
    }

    note left of cycle: В идеальных условиях не прерывается

    cycle --> [*]: Ручная остановка робота

</div>

# Команды

Команды следуют правилам, описанным [здесь](/RobotizedLogisticsSystem/dev/commands).

## Команды робота

### Перекрёсток

```sh
crossroad
```

Этой командой робот сообщает серверу о достижении перекрёстка.

После отдачи этой команды необходимо **остановиться и не двигаться**, пока от сервера не получена одна из [команд движения](#двигаться-вперёд).

## Команды сервера

### Двигаться вперёд

```sh
move forward
```

Роботу следует начать двигаться вперёд до перекрёстка.

### Двигаться налево

```sh
move left
```

Роботу следует повернуть налево и начать двигаться вперёд до перекрёстка.

### Двигаться направо

```sh
move right
```

Роботу следует повернуть направо и начать двигаться вперёд до перекрёстка.

### Двигаться назад

```sh
move back
```

Роботу следует **развернуться** и начать двигаться вперёд до перекрёстка.
