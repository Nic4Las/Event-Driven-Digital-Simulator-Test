package Interfaces;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import Components.Signal;

public interface Gate extends PropertyChangeListener{
    public String getId();
    public void evaluate();
    public ArrayList<Signal> getInputSignals();
    public ArrayList<Signal> getOutputSignals();

}
