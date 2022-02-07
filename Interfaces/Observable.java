package Interfaces;

import java.beans.PropertyChangeListener;


public interface Observable {
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}