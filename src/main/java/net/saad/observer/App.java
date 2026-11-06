package net.saad.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Client entry point for the Observer pattern demo. An interactive menu lets
 * the user subscribe, unsubscribe, change the state, and list subscribers.
 * <p>
 * Observers are discovered at runtime: a number (e.g. "1") is turned into a
 * class name ({@code net.saad.observer.impl.ObserverImpl<n>}) and loaded via
 * reflection, like in the Strategy demo of TP1. Observer 3 has no class to
 * load: it is a lambda built on the {@code @FunctionalInterface} Observer.
 * Each created observer is cached in a map, so unsubscribing later removes
 * <em>the same instance</em> that was subscribed (a new
 * {@code new ObserverImpl1()} would be a different object).
 */
public class App {

    public static void main(String[] args) {
        ObservableImpl observable = new ObservableImpl();
        Map<Integer, Observer> subscribed = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Subscribe an observer   ==> 1");
            System.out.println("Unsubscribe an observer ==> 2");
            System.out.println("Change the state        ==> 3");
            System.out.println("Show subscribed         ==> 4");
            System.out.println("Close the app           ==> -1");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    int number = askObserverNumber(scanner);
                    if (number < 1 || number > 3) {
                        System.out.println("No observer " + number + ".");
                    } else if (subscribed.containsKey(number)) {
                        System.out.println("Observer " + number + " is already subscribed.");
                    } else {
                        Observer observer = createObserver(number);
                        observable.subscribe(observer);
                        subscribed.put(number, observer);
                        System.out.println("Observer " + number + " subscribed.");
                    }
                }
                case 2 -> {
                    int number = askObserverNumber(scanner);
                    Observer observer = subscribed.remove(number);
                    if (observer == null) {
                        System.out.println("Observer " + number + " is not subscribed.");
                    } else {
                        observable.unsubscribe(observer);
                        System.out.println("Observer " + number + " unsubscribed.");
                    }
                }
                case 3 -> {
                    System.out.print("New state: ");
                    int state = scanner.nextInt();
                    observable.setState(state);   // notifies every subscribed observer
                }
                case 4 -> System.out.println("Subscribed observers: " + subscribed.keySet());
                case -1 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static int askObserverNumber(Scanner scanner) {
        System.out.println("  Observer 1 ==> prints the new state");
        System.out.println("  Observer 2 ==> prints the average of all states");
        System.out.println("  Observer 3 ==> lambda, prints a calculation");
        System.out.print("Which observer: ");
        return scanner.nextInt();
    }

    private static Observer createObserver(int number) {
        if (number == 3) {
            return observable -> {
                if (observable instanceof ObservableImpl impl) {
                    System.out.println("================= Observer Impl 3 ====================");
                    System.out.println("Some calculation: " + (impl.getState() + Math.random() * 100));
                    System.out.println("=====================================================");
                }
            };
        }
        try {
            String className = "net.saad.observer.impl.ObserverImpl" + number;
            return (Observer) Class.forName(className).getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create observer " + number, e);
        }
    }
}
