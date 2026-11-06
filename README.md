# Observer Pattern — Java Demo

A small interactive Java console demo of the **Observer** design pattern (GoF
behavioral): a subject object holds a state, and when that state changes it
automatically notifies every observer that subscribed to it.

## How it works

| Pattern role        | Class                                        | Description                                     |
|---------------------|----------------------------------------------|-------------------------------------------------|
| Observer (contract) | `net.saad.observer.Observer`                 | `@FunctionalInterface`: one `update(Observable)` method, so it can also be a lambda |
| Concrete observers  | `impl.ObserverImpl1`, `impl.ObserverImpl2`   | Print the new state / print the running average |
| Observable contract | `net.saad.observer.Observable`               | `subscribe`, `unsubscribe`, `notifyObservers`   |
| Subject             | `net.saad.observer.ObservableImpl`           | Holds the int state, notifies on every `setState` |
| Client              | `net.saad.observer.App`                      | Interactive menu, reflective discovery + cache  |

One object changes, all objects depending on it are told about the change,
without it needing to know what they do with it — like a YouTube channel
uploading a video: every subscriber gets notified and reacts in its own way.

### Pull model

`update()` receives the observable itself, not the value: each observer
**pulls** what it needs with `getState()`.

```java
public void update(Observable observable) {
    if (observable instanceof ObservableImpl obs) {
        System.out.println("New state: " + obs.getState());
    }
}
```

The `instanceof` pattern variable (`obs`) makes sure the cast is safe before
calling `getState()`.

### Discovery and caching in the client

Observers 1 and 2 are created **by name with reflection**, like the Strategy
demo of TP1:

```java
Observer observer = (Observer) Class.forName("net.saad.observer.impl.ObserverImpl" + number)
        .getConstructor()
        .newInstance();
```

- The name must include the package: `net.saad.observer.impl.ObserverImpl1`.
- Observer 3 is a lambda (Observer is a `@FunctionalInterface`), so it has no
  class to load and is built directly.

Each created observer is kept in a `Map<Integer, Observer>`. This matters
because `unsubscribe()` uses `List.remove(o)`, which only removes the **same
object instance** that was subscribed — a new `new ObserverImpl1()` would be a
different object and would not be removed.

## Project layout

```
src/main/java/net/saad/observer/
├── App.java               # client: interactive menu + reflective lookup
├── Observable.java        # observable contract
├── Observer.java          # observer contract (@FunctionalInterface)
├── ObservableImpl.java    # subject: state + subscriber list
└── impl/
    ├── ObserverImpl1.java # prints the new state
    └── ObserverImpl2.java # keeps a history and prints the running average
```

## Requirements

- Java 21+
- Maven 3.9+

## Build & run

```bash
mvn clean compile
java -cp target/classes net.saad.observer.App
```

## Menu

```
Subscribe an observer   ==> 1
Unsubscribe an observer ==> 2
Change the state        ==> 3
Show subscribed         ==> 4
Close the app           ==> -1
```

## Sample session

```
Your choice: 1
  Observer 2 ==> prints the average of all states
Which observer: 2
Observer 2 subscribed.

Your choice: 3
New state: 80
================= Observer Impl 1 ====================
New state: 80
=====================================================
================= Observer Impl 2 ====================
The average of the 1 states is: 80.0
======================================================

Your choice: 3
New state: 90
================= Observer Impl 2 ====================
The average of the 2 states is: 85.0
======================================================

Your choice: 2
Which observer: 1
Observer 1 unsubscribed.

Your choice: 3
New state: 100
================= Observer Impl 2 ====================
The average of the 3 states is: 90.0
======================================================

Your choice: 4
Subscribed observers: [2]
```

Enter `-1` to quit.

## Extending

Add `ObserverImpl3.java` to `impl/` implementing `Observer` — the client's
reflective lookup will find it, and the lambda observer (currently number 3)
can move: lambdas need no class at all.
