package net.saad.observer.impl;

import net.saad.observer.Observable;
import net.saad.observer.ObservableImpl;
import net.saad.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete observer 2, discovered reflectively by {@link net.saad.observer.App}:
 * remembers every state it receives and prints the running average.
 */
public class ObserverImpl2 implements Observer {

    private final List<Integer> history = new ArrayList<>();

    @Override
    public void update(Observable observable) {
        if (observable instanceof ObservableImpl obs) {
            history.add(obs.getState());
            double average = history.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.println("================= Observer Impl 2 ====================");
            System.out.println("The average of the " + history.size() + " states is: " + average);
            System.out.println("======================================================");
        }
    }
}
