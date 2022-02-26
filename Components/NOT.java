package Components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import Interfaces.Gate;

public class NOT implements Gate{
    
    private String id;
    private Signal a;
    private Signal y;

    public NOT(Signal a, Signal y, String id){
        this.a = a;
        this.y = y;
        this.id = id;

        this.a.addListener(this);

        // this.y.setValueSilent(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        evaluate();
        //System.out.println("Updated: " + this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Boolean getValue() {
        return !a.getValue();
    }

    @Override
    public void evaluate() {
        this.y.setValue(getValue());
    }

    @Override
    public ArrayList<Signal> getInputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        list.add(a);
        return list;
    }

    @Override
    public ArrayList<Signal> getOutputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        list.add(y);
        return list;
    }
    
}
