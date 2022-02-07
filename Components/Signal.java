package Components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import Interfaces.Observable;

public class Signal implements Observable {

    private boolean value = false;
    private String name;
    private PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);

    public Signal(String name){
        this.name = name;
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        listenerManager.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        listenerManager.removePropertyChangeListener(listener);
    }

    public boolean getValue(){
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    public void setValue(boolean value){
        PropertyChangeEvent event = new PropertyChangeEvent(this, "value", this.value, value);
        this.value = value;
        listenerManager.firePropertyChange(event);
    }
    
}
