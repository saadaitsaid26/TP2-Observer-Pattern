package net.saad.observer.impl;

import net.saad.observer.Observable;
import net.saad.observer.ObservableImpl;
import net.saad.observer.Observer;

/**
 * Concrete observer 1, discovered reflectively by {@link net.saad.observer.App}:
 * prints the new state.
 */
public class ObserverImpl1 implements Observer {

    @Override
    public void update(Observable observable) {
        if (observable instanceof ObservableImpl obs) {
            System.out.println("================= Observer Impl 1 ====================");
            System.out.println("New state: " + obs.getState());
            System.out.println("=====================================================");
        }
    }
}
