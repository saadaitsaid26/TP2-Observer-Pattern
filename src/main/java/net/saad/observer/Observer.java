package net.saad.observer;

/**
 * The observer contract. Implementations are told whenever the
 * {@link Observable} they subscribed to changes. Having only one method makes
 * it a {@code @FunctionalInterface}, so any observer can also be a lambda.
 */
@FunctionalInterface
public interface Observer {

    void update(Observable observable);
}
