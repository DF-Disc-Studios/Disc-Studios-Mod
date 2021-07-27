package io.github.discstudiosclient.util;

import java.util.List;

public interface IManager<T> {
    /**
     * Initializes all objects.
     */
    void initialize();

    /**
     * Registers an object and inserts it
     * into a list.
     *
     * @param object Object to register.
     */
    void register(T object);

    /**
     * Returns list of all registered objects.
     *
     * @return List of registered objects.
     */
    List<T> getRegistered();

    /**
     * Removes an object from the manager's list.
     *
     * @param object Object to be removed.
     * @return True if was removed, false if not.
     * @see List#remove(Object)
     */
    default boolean unregister(T object) {
        return this.getRegistered().remove(object);
    }

}
