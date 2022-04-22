---
layout: default
title: Омниколёсный робот
description: Колёса на максималках
mermaid: true
---

{% include dev/navigation.md page="omniwheel" %}

# Состояния

<div class="mermaid">
stateDiagram-v2
    state "Цикл" as cycle
    state "Авторизация" as auth
    
    [*] --> auth
    auth --> cycle: Успешная авторизация

    state cycle {
        state "Ожидание на пункте выдачи" as dropPointStandBy
        state "Движение до манипулятора" as movingToManipulator
        state "Получение заказа" as gettingOrder
        state "Движение до пункта выдачи" as movingToDropPoint
        state "Отдача заказа" as givingOrder

        [*] --> dropPointStandBy
        dropPointStandBy --> movingToManipulator: Команда сервера
        movingToManipulator --> gettingOrder: Команда сервера
        gettingOrder --> movingToDropPoint: Команда сервера
        movingToDropPoint --> givingOrder: Команда сервера
        givingOrder --> dropPointStandBy: Команда сервера
    }

    note left of cycle: В идеальных условиях не прерывается

    cycle --> [*]: Ручная остановка робота

</div>

# Команды

Команды следуют правилам, описанным [здесь](/RobotizedLogisticsSystem/dev/commands/).

## Команды сервера

### Движение

```sh
turn $angle
```

Сообщает роботу о том, что ему следует двигаться в направлении указанного [угла поворота](#угол-поворота).

#### Угол поворота

`float` `$angle` - дробное число, обозначающее угол поворота **в градусах**, в направлении которого следует двигаться роботу.

Значение следует логике тригонометрической окружности:

- Если `$angle` положителен, он обозначает поворот против часовой стрелки (налево);
- Если `$angle` отрицателен, он обозначает поворот по часовой стрелке (направо).

#### Примеры

```sh
turn 30.0
```

```sh
turn -179.5555
```

### Остановка

```sh
stop
```

Сообщает роботу о том, что ему следует остановиться и ждать [команды движения](#движение).
