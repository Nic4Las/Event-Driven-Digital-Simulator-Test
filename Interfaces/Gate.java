package Interfaces;

import java.beans.PropertyChangeListener;

public interface Gate extends PropertyChangeListener {
    public String getId();
    public boolean getValue();
    public void evaluate();
}
