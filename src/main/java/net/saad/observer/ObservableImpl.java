package net.saad.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The subject of the Observer pattern: holds an {@code int} state and notifies
 * every subscribed observer whenever the state changes.
 */
public class ObservableImpl implements Observable {

    private final List<Observer> observers = new ArrayList<>();
    private int state;

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setState(int state) {
        this.state = state;
        notifyObservers();
    }

    public int getState() {
        return state;
    }
}
