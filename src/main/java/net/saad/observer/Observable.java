package net.saad.observer;

/**
 * The observable (subject) contract: keeps the list of subscribers and
 * notifies them when its state changes.
 */
public interface Observable {

    void subscribe(Observer observer);

    void unsubscribe(Observer observer);

    void notifyObservers();
}
