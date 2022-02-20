package Interfaces;

import java.beans.PropertyChangeListener;

public interface Gate extends PropertyChangeListener{
    public String getId();
    public Boolean getValue();
    public void evaluate();
}
