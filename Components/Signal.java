package Components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import EventScheduler.EventScheduler;
import Interfaces.Gate;
import Interfaces.Observable;

public class Signal implements Observable {

    private Boolean value = false;
    private Boolean dirty = true;
    private String name;
    private EventScheduler scheduler;
    private ArrayList<Gate> listeners = new ArrayList<Gate>();

    public Signal(String name, EventScheduler scheduler) {
        this.name = name;
        this.scheduler = scheduler;
    }

    public Signal(String name, EventScheduler scheduler, Boolean isInput) {
        this.name = name;
        this.scheduler = scheduler;
        this.dirty = true;
        this.setValue(this.value);

    }

    @Override
    public void addListener(Gate listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Gate listener) {
        listeners.remove(listener);
    }

    public Boolean getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Gate> getListeners() {
        return this.listeners;
    }

    public void setValue(Boolean value) {

        PropertyChangeEvent event = new PropertyChangeEvent(this, "value", this.value, value);
        this.value=value;
        scheduler.addEvent(event);
        // scheduler.runStep();
    }

    public void setValueSilent(Boolean value) {
        this.value = value;
        // scheduler.runStep();
    }

    public void toggleValue() {
        Boolean newVal = !this.getValue();
        this.setValue(newVal);
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean value) {
        this.dirty = value;
    }

    public void markClean() {
        this.dirty = false;
    }

    public void forceUpdate() {
        this.dirty = true;
        this.setValue(this.value);
    }

}
