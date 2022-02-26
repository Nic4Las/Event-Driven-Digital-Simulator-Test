package Components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import Interfaces.Gate;


public class Port implements Gate {
    
    private String id;
    private Signal a;
    private PORT_TYPE type;

    public Port(Signal a, PORT_TYPE type, String id) {
        this.a = a;
        this.id = id;
        this.type = type;

        if(this.isOutput()) this.a.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public boolean isInput() {
        return this.type.equals(PORT_TYPE.INPUT);
    }

    public boolean isOutput() {
        return !isInput();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Boolean getValue() {
        return a.getValue();
    }

    public PORT_TYPE getType() {
        return type;
    }

    @Override
    public void evaluate() {
        // if(this.isInput()) this.a.setValue(getValue()); //No need
    }

    @Override
    public ArrayList<Signal> getInputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        if(this.isOutput()) list.add(this.a);
        return list;
    }

    @Override
    public ArrayList<Signal> getOutputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        if(this.isInput()) list.add(this.a);
        return list;
    }

    public enum PORT_TYPE {
        INPUT,
        OUTPUT
    }
    
}
